package models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class MediaItemTest {
    MediaItem mediaItem;

    @BeforeEach
    void createMediaItem() {
        String from = "2022-10-20 11:30:30", to = "2022-10-20 12:30:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter), toDateTime = LocalDateTime.parse(to, formatter);
        mediaItem = new MediaItem(1, "Meeting 1", fromDateTime, toDateTime, "Unit Test");
    }

    @Test
    public void testGetID() {
        assertEquals(1, mediaItem.getID());
    }

    @Test
    public void getTitle() {
        assertEquals("Meeting 1", mediaItem.getTitle());
    }

    @Test
    public void setTitle() {
        mediaItem.setTitle("Meeting 2");
        assertEquals("Meeting 2", mediaItem.getTitle());
    }

    @Test
    public void getFrom() {
        assertEquals("2022-10-20T11:30:30", mediaItem.getFrom().toString());
    }

    @Test
    public void setFrom() {
        String from = "2033-10-20 11:30:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
        mediaItem.setFrom(fromDateTime);
        assertEquals("2033-10-20T11:30:30", mediaItem.getFrom().toString());
    }

    @Test
    public void getTo() {
        assertEquals("2022-10-20T12:30:30", mediaItem.getTo().toString());
    }

    @Test
    public void setTo() {
        String to = "2044-10-20 11:30:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);
        mediaItem.setTo(toDateTime);
        assertEquals("2044-10-20T11:30:30", mediaItem.getTo().toString());
    }

    @Test
    public void getAgenda() {
        assertEquals("Unit Test", mediaItem.getAgenda());
    }

    @Test
    public void setAgenda() {
        mediaItem.setAgenda("Changing Agenda");
        assertEquals("Changing Agenda", mediaItem.getAgenda());
    }

}