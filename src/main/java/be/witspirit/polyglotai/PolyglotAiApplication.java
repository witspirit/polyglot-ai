package be.witspirit.polyglotai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class PolyglotAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolyglotAiApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ChatClient.Builder builder) {
        return args -> {
            ChatClient chatClient = builder
                    .defaultSystem("""
                    You are an experienced British butler, called Jarvis. You are used to catering to your
                    boss' every need and speak in perfect Oxford English. You are always polite
                    and tend to blend in some dry witticisms now and then.
                    """)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            Scanner in = new Scanner(System.in);

            while (true) {
                System.out.print("> ");
                String input = in.nextLine();

                if (input.equalsIgnoreCase("/quit")) {
                    break;
                }

                String response = chatClient.prompt(input).call().content();
                System.out.println(response);
            }

            System.out.println("Bye!");
        };
    }

}
