package be.witspirit.polyglotai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class ToolConfig {

    @Bean
    @Description("Attempts to run provided code as Javascript and returns true if it works. Returns false when the code was not able to run and is most likely not javascript.")
    public Function<IsJavascriptTool.CodeRequest, IsJavascriptTool.CodeResponse> javascriptRunner(PolyglotExecutor polyglotExecutor) {
        return new IsJavascriptTool(polyglotExecutor);
    }

    @Bean
    @Description("Attempts to run provided code as Python and returns true if it works. Returns false when the code was not able to run and is most likely not python.")
    public Function<IsPythonTool.CodeRequest, IsPythonTool.CodeResponse> pythonRunner(PolyglotExecutor polyglotExecutor) {
        return new IsPythonTool(polyglotExecutor);
    }

}