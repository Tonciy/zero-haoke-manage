package cn.zeroeden.haoke.dubbo.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zero
 * @Description 描述此类
 */
@Controller
@RequestMapping("graphql")
@CrossOrigin
public class GraphQLController {
    @Autowired
    private GraphQL graphQL;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @GetMapping
    @ResponseBody
    public Map<String, Object> graphql(@RequestParam("query") String query,
                                       @RequestParam(value = "variables", required = false) String variablesJson,
                                       @RequestParam(value = "operationName", required = false) String operationName) throws IOException {
        try {
            Map variables = MAPPER.readValue(variablesJson,
                    MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class,
                            Object.class));
            return this.executeGraphqlQuery(query, operationName, variables);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> error = new HashMap<>();
        error.put("status", 500);
        error.put("msg", "查询出错");
        return error;
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> postQuery(@RequestBody Map<String, Object> map) {
        try {
            String query = (String) map.get("query");
            if (null == query) {
                query = "";
            }
            String operationName = (String) map.get("operationName");
            Map variables = (Map) map.get("variables");
            if (variables == null) {
                variables = Collections.EMPTY_MAP;
            }
            return this.executeGraphqlQuery(query, operationName, variables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> error = new HashMap<>();
        error.put("status", 500);
        error.put("msg", "查询出错");
        return error;
    }

    private Map<String, Object> executeGraphqlQuery(String query, String
            operationName, Map<String, Object> variables) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .operationName(operationName)
                .variables(variables)
                .build();
        return this.graphQL.execute(executionInput).toSpecification();
    }
}
