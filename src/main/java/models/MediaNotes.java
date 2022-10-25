package models;

import java.util.ArrayList;
import java.util.List;

public class MediaNotes {
    private List<String> Notes = new ArrayList<>();

    public List<String> getNotes() {
        return Notes;
    }

    public void setNotes(List<String> notes) {
        Notes = notes;
    }
}
