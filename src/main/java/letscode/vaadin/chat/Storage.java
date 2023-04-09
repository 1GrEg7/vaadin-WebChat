package letscode.vaadin.chat;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Storage {
    @Getter
    private Queue<ChatMessage> messages = new ConcurrentLinkedQueue<>(); // очередь для многопоточного использования
    private ComponentEventBus eventBus = new ComponentEventBus(new Div());
    public static Storage storage = new Storage();

    @Getter
    @AllArgsConstructor
    public static class ChatMessage {
        private String name;
        private String message;
    }

    public static class ChatEvent extends ComponentEvent<Div> { // штука для обновления чата
        public ChatEvent() {
            super(new Div(), false);
        }
    }

    public void addRecord(String user, String message) {  // Запись пользователя и его сообщения
        messages.add(new ChatMessage(user, message));
        eventBus.fireEvent(new ChatEvent()); // передает сообщение серверу(ту штуку) для обновления дыннх(появляются сообщения)
    }

    public void addRecordJoined(String user) {   // Метод для присоеденения к чату
        messages.add(new ChatMessage("", user));
        eventBus.fireEvent(new ChatEvent());
    }

    public Registration attachListener(ComponentEventListener<ChatEvent> messageListener) {
        return eventBus.addListener(ChatEvent.class, messageListener);
    }

    public int size() {
        return messages.size();
    }
}
