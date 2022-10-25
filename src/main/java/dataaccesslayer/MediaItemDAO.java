package dataaccesslayer;

import models.MediaItem;
import models.MediaNotes;

import java.io.IOException;
import java.util.List;

public class MediaItemDAO {


    private DataAccess dataAccess;

    //constructor select database connection or file
    public MediaItemDAO() throws IOException {
        dataAccess = new Database();
    }

    public List<MediaItem> GetItems() {
        return dataAccess.GetItems();
    }

    public void SetItem(String title, String from, String to, String agenda) {

        dataAccess.AddItem(
                title,
                from,
                to,
                agenda
        );
    }

    public void deleteItem(int i) {
        dataAccess.deleteItem(i);
    }

    public void updateItem(String title, String from, String to, String agenda, Integer id) {
        dataAccess.updateItem(
                title,
                from,
                to,
                agenda,
                id
        );
    }

    public List<MediaNotes> GetNotes() {
        return dataAccess.GetNotes();
    }
}
