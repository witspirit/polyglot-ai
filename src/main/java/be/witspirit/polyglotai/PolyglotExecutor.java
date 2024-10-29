package be.witspirit.polyglotai;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PolyglotExecutor {

    private static final Context context = Context.create();

    public boolean isJavascript(String codeCandidate) {
        try {
            log.info("Trying to check whether '{}' is Javascript", codeCandidate);
            context.eval("js", codeCandidate);
            return true;
        } catch (PolyglotException e) {
            log.info("Failed to execute JS: {}", e.getMessage());
            return false;
        }
    }

}
