package letscode.vaadin.chat;

import com.github.rjeschke.txtmark.Processor;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import java.io.InputStream;
import java.util.Objects;

@Route(value = "Chat", layout = NovigationColumn.class)
@PageTitle("Layouts")
public class MainView extends VerticalLayout {
    private Registration registration;

    private Grid<Storage.ChatMessage> grid;
    private VerticalLayout chat;
    private VerticalLayout login;
    public String user = "";


    public MainView() {
        buildChat();
    }
    NovigationColumn novigationColumn = new NovigationColumn();
    private void buildLogin() {
        int numberOFClicks =0;
        login = new VerticalLayout() {{
            TextField name = new TextField("Name");
            name.setMinLength(3);
            PasswordField password = new PasswordField("Password");
            password.setPlaceholder("Please, enter the password");
            name.setPlaceholder("Please, introduce yourself");
            add(
                    name,
                    password,
                    new Button("Login") {{
                        addClickListener(click -> {
                            if (!name.isInvalid() && Objects.equals(password.getValue(), "12345")){
                                login.setVisible(false);
                                chat.setVisible(true);
                                //user = name.getValue();
                               // Storage.storage.addRecordJoined(user);
                                //NovigationColumn.header.setVisible(true);
                                //NovigationColumn.SetVISIBLE();

                            }

                        });
                        addClickShortcut(Key.ENTER);
                    }}
            );
        }};
        add(login);
    }


    /*private static File getUploadFolder() {
        File folder = new File("uploaded-files");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }*/

    private void buildChat() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
        });
        chat = new VerticalLayout();
        add(chat);
        chat.setVisible(true);

        grid = new Grid<>();
        grid.setItems(Storage.storage.getMessages());
        grid.addColumn(new ComponentRenderer<>(message -> new Html(renderRow(message))))
                .setAutoWidth(true);

        TextField field = new TextField();
        chat.add(
                grid,
                new HorizontalLayout() {{
                    add(
                            field,
                            new Button("➡") {{
                                addClickListener(click -> {
                                    if (!Objects.equals(field.getValue(), "")){
                                        Storage.storage.addRecord(user, field.getValue());
                                        field.clear();
                                    }
                                });
                                addClickShortcut(Key.ENTER);
                            }}
                    );
                }}
        );
    }
    public void onMessage(Storage.ChatEvent event) {
        if (getUI().isPresent()) {
            UI ui = getUI().get();
            ui.getSession().lock();
            ui.beforeClientResponse(grid, ctx -> grid.scrollToEnd());
            ui.access(() -> grid.getDataProvider().refreshAll());          // обеспечивает связь интерфейса и данных
            ui.getSession().unlock();
        }
    }
    private String renderRow(Storage.ChatMessage message) {
        if (message.getName().isEmpty()) {
            return Processor.process(String.format("_User **%s** is just joined the chat!_", message.getMessage()));
        } else {
            return Processor.process(String.format("**%s**: %s", message.getName(), message.getMessage()));
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        registration = Storage.storage.attachListener(this::onMessage);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
    }
        }
