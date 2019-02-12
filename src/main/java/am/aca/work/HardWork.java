package am.aca.work;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HardWork {
    public static void test() {
                MigrationEventHandler eventHandler = new MigrationEventHandler();

        Thread thread = new Thread(() -> HardWork.migration(eventHandler));
        thread.start();

//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        MigrationEventHandler eventHandler = new MigrationEventHandler();
//        executorService.submit(() -> migration(eventHandler));
    }

    private static void migration(MigrationEventHandler eventHandler) {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(500);
                eventHandler.notify("Message " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MigrationEventHandler {
    void notify(String event) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());
//      stompClient.setTaskScheduler(taskScheduler);
        String url = "ws://localhost:8080/ws";
        StompSessionHandler sessionHandler = new MyStompSessionHandler(event);
        stompClient.connect(url, sessionHandler);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");


    }
}

class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private String event;

    MyStompSessionHandler(String event) {
        this.event = event;
    }
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/ws", this);
        session.send("/message", event);
        System.out.println("in afterconnect");
    }
}