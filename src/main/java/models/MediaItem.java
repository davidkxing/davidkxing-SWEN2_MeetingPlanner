package models; //Daten container -> properties, constructor


import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

public class MediaItem {
    private int ID;
    private String Title;
    private MediaNotes Notes;
    public LocalDateTime From;
    public LocalDateTime To;
    public String Agenda;

    public int getID() { return ID; }
    public void setID(int id) { ID = id; }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }

    public LocalDateTime getFrom() {
        return From;
    }
    public void setFrom(LocalDateTime from) {
        From = from;
    }

    public LocalDateTime getTo() {
        return To;
    }
    public void setTo(LocalDateTime to) {
        To = to;
    }

    public String getAgenda() { return Agenda;}
    public void setAgenda(String agenda) { Agenda = agenda;}

    public MediaNotes getNotes() { return Notes;}
    public void setNotes(MediaNotes notes) { Notes = notes;}

    public MediaItem(Integer id, String title, LocalDateTime from, LocalDateTime to, String agenda) {
        ID = id;
        Title = title;
        From = from;
        To = to;
        Agenda = agenda;
    }
}
