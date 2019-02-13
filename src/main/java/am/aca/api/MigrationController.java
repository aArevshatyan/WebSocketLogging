package am.aca.api;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SessionScope
@RestController
@RequestMapping("/migrate")
public class MigrationController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MigrationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<?> migrate() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        executorService.submit(() -> migration(sessionId));
        return ResponseEntity.ok().build();
    }

    private void migration(String sessionId) {
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(20);
                messagingTemplate.convertAndSend("/message/" + sessionId, i);
            }
            messagingTemplate.convertAndSend("/message/" + sessionId, "done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}