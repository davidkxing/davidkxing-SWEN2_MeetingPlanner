/*package dataaccesslayer;

import models.MediaItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccess implements DataAccess{
    private String filePath;

    //constructor
    public FileAccess() {
        // get info from config.properties
        filePath = "...";
    }
    @Override
    public List<MediaItem> GetItems() {
        // read media items from file system
        MediaItem[] mediaItems = {
                new MediaItem("Item 1"),
                new MediaItem("Item 2"),
                new MediaItem("Item 3"),
                new MediaItem("Item 4")
        };
        return new ArrayList<MediaItem>(Arrays.asList(mediaItems));
    }
}*/
