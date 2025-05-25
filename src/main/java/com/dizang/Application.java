package com.dizang;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Bean
    public CommandLineRunner predefinedQuestions(
            ChatClient.Builder chatClientBuilder,
            ToolCallbackProvider tools,
            ConfigurableApplicationContext context) {
        return args -> {
            // 构建ChatClient并注入MCP工具
            ChatClient chatClient = chatClientBuilder
                    .defaultToolCallbacks(tools)
                    .build();

            // 定义用户输入
//            String userInput = "帮我创建一个私有仓库，命名为test-mcp";
            String userInput = "有哪些工具可用";
//            String userInput = "求广州三日两夜的行程";
            // 打印问题
            System.out.println("\n>>> QUESTION: " + userInput);
            // 调用LLM并打印响应
            System.out.println("\n>>> ASSISTANT: " +
                    chatClient.prompt(userInput).call().content());

            // 关闭应用上下文
            context.close();
        };
    }
}
