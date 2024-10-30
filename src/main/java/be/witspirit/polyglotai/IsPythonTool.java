package be.witspirit.polyglotai;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class IsPythonTool implements Function<IsPythonTool.CodeRequest, IsPythonTool.CodeResponse> {
    private final PolyglotExecutor polyglotExecutor;

    public record CodeRequest(String pythonCandidate) {}
    public record CodeResponse(boolean isPython) {}

    @Override
    public CodeResponse apply(CodeRequest request) {
        return new CodeResponse(polyglotExecutor.isPython(request.pythonCandidate()));
    }
}
