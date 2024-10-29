package be.witspirit.polyglotai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class ToolConfig {

    @Bean
    @Description("Takes potential javascript code and returns true if the code runs as proper Javascript. Returns false when the code was not able to run and is most likely not javascript.")
    public Function<IsJavascriptTool.CodeRequest, IsJavascriptTool.CodeResponse> isJavascriptTool(PolyglotExecutor polyglotExecutor) {
        return new IsJavascriptTool(polyglotExecutor);
    }

}