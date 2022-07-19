package cn.zeroeden.webSocket;

import cn.zeroeden.dao.MessageDAO;
import cn.zeroeden.pojo.Message;
import cn.zeroeden.pojo.UserData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zero
 * @Description 描述此类
 */
@Component
@RocketMQMessageListener(topic = "haoke-im-send-message-topic", messageModel = MessageModel.BROADCASTING,
                        consumerGroup = "haoke-im-consumer",
                        selectorExpression = "SEND_MSG"
)
public class MessageHandler extends TextWebSocketHandler implements RocketMQListener<String> {
    @Autowired
    private MessageDAO messageDAO;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Map<Long, WebSocketSession> SESSIONS = new HashMap<>();

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws
            Exception {
        Long uid = (Long) session.getAttributes().get("uid");
// 将当前用户的session放置到map中，后面会使用相应的session通信
        SESSIONS.put(uid, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long uid = (Long) session.getAttributes().get("uid");
        // 从 session 中移除此用户信息
        SESSIONS.remove(uid);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage
            textMessage) throws Exception {
        Long uid = (Long) session.getAttributes().get("uid");
        JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());
        Long toId = jsonNode.get("toId").asLong();
        String msg = jsonNode.get("msg").asText();
        Message message = Message.builder()
                .from(UserData.USER_MAP.get(uid))
                .to(UserData.USER_MAP.get(toId))
                .msg(msg)
                .build();
        // 将消息保存到MongoDB
        message = this.messageDAO.saveMessage(message);
        String msgJson = MAPPER.writeValueAsString(message);
        // 判断to用户是否在线
        WebSocketSession toSession = SESSIONS.get(toId);
        if (toSession != null && toSession.isOpen()) {
            //TODO 具体格式需要和前端对接

            toSession.sendMessage(new
                    TextMessage(msgJson));
            // 更新消息状态为已读
            this.messageDAO.updateMessageState(message.getId(), 2);
        }else{
            // 当前id的会话可能未开 或者不存在此结点中
            rocketMQTemplate.convertAndSend("haoke-im-send-message-topic:SEND_MSG",msgJson);
        }
    }

    @Override
    public void onMessage(String msg) {
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);
            Long toId = jsonNode.get("to").get("id").longValue();
            // 判断to用户是否在线
            WebSocketSession toSession = SESSIONS.get(toId);
            if (toSession != null && toSession.isOpen()) {
                toSession.sendMessage(new TextMessage(msg));
// 更新消息状态为已读
                this.messageDAO.updateMessageState(new
                        ObjectId(jsonNode.get("id").asText()), 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
