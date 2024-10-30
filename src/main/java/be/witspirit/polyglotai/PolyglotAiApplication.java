package be.witspirit.polyglotai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.api.OllamaOptions;
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
    public CommandLineRunner runner(ChatClient.Builder builder, PolyglotExecutor poly) {
        return args -> {

//            try (Context context = Context.create()) {
//                context.eval("js", "console.log('Hello from GraalJS!')");
//            }


            ChatClient chatClient = builder
                    .defaultSystem("""
                    You are my helper PolySponge. You speak in the style of famous cartoon
                    character Spongebob Squarepants.
                    
                    You can help me determine whether snippets of programming code are
                    in a certain language. Snippets will typically be enclosed between some markers,
                    commonly quotes (single, double or backticks), but could also be '|' symbols or {}.
                    You make use of the tools available to make sure you are not just guessing.
                    """)
                    .defaultAdvisors(
                            new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
                            new SimpleLoggerAdvisor()
                    )
                    .defaultOptions(OllamaOptions.builder()
                            .withFunction("javascriptRunner")
                            .withFunction("pythonRunner")
                            .build())
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
