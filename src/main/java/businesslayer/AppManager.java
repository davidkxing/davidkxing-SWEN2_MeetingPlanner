package businesslayer;

import lombok.Getter;
import models.MediaItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppManager {
    List<MediaItem> GetItems();
    List<MediaItem> Search(String itemName, boolean caseSensitive);
    void SetItem(String title, String from, String to, String agenda);

    void deleteItem(int i);

    void setWindow();

    void updateItem(String title, String from, String to, String agenda, Integer id);

    void generatePDF(String title, LocalDateTime from, LocalDateTime to, String agenda, Integer id) throws IOException;
}
