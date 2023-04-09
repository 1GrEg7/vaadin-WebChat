package letscode.vaadin.chat;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route(value = "Mems",  layout = NovigationColumn.class)
@PageTitle("Mems")
public class Mems extends VerticalLayout {
    private Mems(){
        ADDpictures();

    }
    StreamResource streamResource = new StreamResource("Mem1",
            () ->getClass().getResourceAsStream("C:/Developing_Java_Android/Images/Mem1.png")
    );
    Image mem1 = new Image(streamResource,"mem1");

    private void ADDpictures(){
        add(mem1);
    }

}
