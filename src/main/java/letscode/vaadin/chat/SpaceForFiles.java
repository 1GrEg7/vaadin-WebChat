package letscode.vaadin.chat;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.io.File;

@Route(value = "Files",  layout = NovigationColumn.class)
@PageTitle("Files")

public class SpaceForFiles extends VerticalLayout {
    File uploadFolder = getUploadFolder();

    public  UploadArea uploadArea = new UploadArea(uploadFolder);

    public SpaceForFiles() {
        DownloadLinksArea linksArea = new DownloadLinksArea(uploadFolder);
        uploadArea.getUploadField().addSucceededListener(e -> {
            uploadArea.hideErrorField();
            linksArea.refreshFileLinks();
        });
        add(uploadArea, linksArea);
    }

    private static File getUploadFolder() {                          // создает папку для файлов
        File folder = new File("uploaded-files");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}
