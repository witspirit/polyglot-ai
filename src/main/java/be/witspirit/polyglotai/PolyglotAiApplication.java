package be.witspirit.polyglotai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PolyglotAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolyglotAiApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ChatClient.Builder builder) {
        return args -> {
            ChatClient chatClient = builder.build();
            String response = chatClient.prompt("As a wizard from Lord of the Rings, tell me a joke related to a beach").call().content();
            System.out.println(response);
        };
    }

}
