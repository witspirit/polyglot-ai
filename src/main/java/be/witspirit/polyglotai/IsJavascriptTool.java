package be.witspirit.polyglotai;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class IsJavascriptTool implements Function<IsJavascriptTool.CodeRequest, IsJavascriptTool.CodeResponse> {
    private final PolyglotExecutor polyglotExecutor;

    public record CodeRequest(String javascriptCandidate) {}
    public record CodeResponse(boolean isJavascript) {}

    @Override
    public CodeResponse apply(CodeRequest request) {
        return new CodeResponse(polyglotExecutor.isJavascript(request.javascriptCandidate()));
    }
}
