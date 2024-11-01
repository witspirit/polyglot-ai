package be.witspirit.polyglotai;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SandboxPolicy;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@Slf4j
public class PolyglotExecutor {

    private final ByteArrayOutputStream programOutput;
    private final ByteArrayOutputStream programError;

    private final Context jsContext;
    private final Context pyContext;

    public PolyglotExecutor() {
        programOutput = new ByteArrayOutputStream();
        programError = new ByteArrayOutputStream();

        jsContext = Context.newBuilder("js")
                .sandbox(SandboxPolicy.CONSTRAINED)
                .out(programOutput)
                .err(programError)
                // .option("sandbox.MaxStatements", "2") // Requires enterprise runtime
                .build();

        pyContext = Context.newBuilder("python")
                .sandbox(SandboxPolicy.TRUSTED) // Constrained is not supported for Python
                .allowHostAccess(HostAccess.CONSTRAINED)
                .out(programOutput)
                .err(programError)
                // .option("sandbox.MaxStatements", "2") // Requires Enterprise runtime
                .build();
    }

    public boolean isJavascript(String codeCandidate) {
        try {
            log.info("Trying to check whether '{}' is Javascript", codeCandidate);
            if (codeCandidate == null || codeCandidate.isEmpty()) {
                return false;
            }
            jsContext.eval("js", codeCandidate);
            logOutputs();
            return true;
        } catch (PolyglotException e) {
            log.info("Failed to execute JS: {}", e.getMessage());
            return false;
        }
    }

    public boolean isPython(String codeCandidate) {
        try {
            log.info("Trying to check whether '{}' is Python", codeCandidate);
            if (codeCandidate == null || codeCandidate.isEmpty()) {
                return false;
            }
            pyContext.eval("python", codeCandidate);
            logOutputs();
            return true;
        } catch (PolyglotException e) {
            log.info("Failed to execute Python: {}", e.getMessage());
            return false;
        }
    }

    private void logOutputs() {
        String output = programOutput.toString();
        if (output != null && !output.isEmpty()) {
            log.info("PROGRAM OUTPUT: {}", output);
        }
        programOutput.reset();

        String errors = programOutput.toString();
        if (errors != null && !errors.isEmpty()) {
            log.info("PROGRAM ERRORS: {}", errors);
        }
        programError.reset();
    }

}
