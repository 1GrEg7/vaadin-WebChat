package letscode.vaadin.chat;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Objects;

@Route("")
@PageTitle("Login")

public class loginView  extends VerticalLayout {

    public loginView(){

        buildLogin();
    }

    public String user;
    private void buildLogin() {
        VerticalLayout login;
        MainView mainView = new MainView();
        //novigationColumn.BuidNovigationTool();
        //novigationColumn.header.setVisible(false);
        int numberOFClicks =0;
        login = new VerticalLayout() {{
            TextField name = new TextField("Name");
            name.setMinLength(3);
            PasswordField password = new PasswordField("Password");
            password.setPlaceholder("Please, enter the password");
            name.setPlaceholder("Please, introduce yourself");
            Button button = new Button("Login");
            button.addClickShortcut(Key.ENTER);
            button.addClickListener(click ->{
                        if (!name.isInvalid() && Objects.equals(password.getValue(), "12345")){
                            user = name.getValue();
                            Storage.storage.addRecordJoined(user);
                            button.getUI().ifPresent(ui -> ui.navigate(MainView.class));
                        }});

            add(
                    name,
                    password,
                    button
            );
        }};
        add(login);
    }


}
