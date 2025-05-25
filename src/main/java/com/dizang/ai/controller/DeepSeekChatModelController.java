package com.dizang.ai.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@RequestMapping("/openai")
@ResponseBody
@Controller
public class DeepSeekChatModelController {

    private final ChatModel deepSeekChatModel;

    public DeepSeekChatModelController(ChatModel chatModel) {
        this.deepSeekChatModel = chatModel;
    }

    @GetMapping("/simple/chat/{prompt}")
    public String simpleChat (@PathVariable(value = "prompt") String prompt) {

        return deepSeekChatModel.call(new Prompt(prompt)).getResult().getOutput().getText();
    }
    /**
     * Stream 流式调用。可以使大模型的输出信息实现打字机效果。
     *
     * @return Flux<String> types.
     */
    @GetMapping("/stream/chat/{prompt}")
    public Flux<String> streamChat (@PathVariable(value = "prompt") String prompt,HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        Flux<ChatResponse> stream = deepSeekChatModel.stream(new Prompt(prompt));
        return stream.map(resp -> resp.getResult().getOutput().getText());
    }
}
