package cn.zeroeden.haoke.dubbo.api.controller;

import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

/**
 * @author Zero
 * @Description 描述此类
 */
@Controller
@RequestMapping("graphql")
public class GraphQLController {
    @Autowired
    private GraphQL graphQL;

    @GetMapping
    @ResponseBody
    public Map<String, Object> graphql(@RequestParam("query") String query) throws
            IOException {
        System.out.println(query);
        return this.graphQL.execute(query).toSpecification();
    }
}
