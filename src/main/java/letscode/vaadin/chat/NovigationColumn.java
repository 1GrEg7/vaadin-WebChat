package letscode.vaadin.chat;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

@Push
public class NovigationColumn extends AppLayout{
    public NovigationColumn(){
        createHeader();
        createDrawer();
    }

     H1 logo = new H1("Novigation");
     public  HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

    private void createHeader() {
        logo.addClassNames("text-l", "m-m");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);
    }
     VerticalLayout verticalLayout = new VerticalLayout();
    private void createDrawer() {
        RouterLink listView = new RouterLink("Chat", MainView.class );
        listView.setHighlightCondition(HighlightConditions.sameLocation());

        //RouterLink Memes = new RouterLink("Memes", Mems.class);
        RouterLink Files = new RouterLink("Files", SpaceForFiles.class);
        verticalLayout.add(listView,Files);

        addToDrawer(verticalLayout);
    }

    public void SetINISIBLE(){
         header.setVisible(false);
         verticalLayout.setVisible(false);

    }
    public void SetVISIBLE(){
        header.setVisible(true);
        verticalLayout.setVisible(true);
    }


}

