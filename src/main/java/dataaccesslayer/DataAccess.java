package dataaccesslayer;

import models.MediaItem;
import models.MediaNotes;

import java.util.List;

// return data from defined sources
public interface DataAccess {
    List<MediaItem> GetItems();  //return MediaItems regardless of Database or FileAccess
    void AddItem(String title, String from, String to, String agenda);

    void deleteItem(int i);
    void updateItem(String title, String from, String to, String agenda, Integer id);

    List<MediaNotes> GetNotes();
}
