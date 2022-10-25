package views;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import businesslayer.AppManager;
import businesslayer.AppManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import logger.ILoggerWrapper;
import logger.LoggerFactory;
import models.MediaItem;
import models.MediaNotes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MainController implements Initializable {
    public static final String TARGET_PDF = "MeetingPlanner.pdf";
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public TextField searchMeetingField;
    public ListView<MediaItem> meetingListView;
    public TextField titleField;
    public DatePicker fromDateField;
    public TextField fromTimeField;
    public DatePicker toDateField;
    public TextField toTimeField;
    public TextArea agendaField;
    public ListView<MediaNotes> notesListView;
    public TextField notesField;
    public Integer hiddenID;
    public String[] split;

    private ObservableList<MediaItem> mediaItems;
    private ObservableList<MediaNotes> mediaNotes;
    private MediaItem currentItem;
    private MediaNotes currentNote;
    private AppManager manager;

    //region Actions
    public void searchAction(ActionEvent actionEvent) {
        mediaItems.clear(); //list is clear

        List<MediaItem> items = manager.Search(searchMeetingField.textProperty().getValue(), false);
        mediaItems.addAll(items);
    }

    public void clearAction(ActionEvent actionEvent) {
        mediaItems.clear();
        clearForm();
        refreshList();
    }


    public void deleteAction(ActionEvent actionEvent) {
        int i = meetingListView.getSelectionModel().getSelectedItem().getID();
        manager.deleteItem(i);
        clearForm();
        mediaItems.clear();
        refreshList();
    }

    public void addAction(ActionEvent actionEvent) {
        mediaItems.clear();

        if (missingField()) {
            logger.warn("Some fields are empty!");
        } else {
            logger.debug("Inputs have been submitted!");
            manager.SetItem(
                    titleField.getText(),
                    getFrom(),
                    getTo(),
                    agendaField.getText()
            );
        }
        clearForm();
        refreshList();
    }

    public void updateAction(ActionEvent actionEvent) {
        int i = meetingListView.getSelectionModel().getSelectedIndex();
        if (i >= 0 && i < mediaItems.size()) {
            LocalDate localDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            hiddenID = mediaItems.get(i).getID();
            titleField.setText(mediaItems.get(i).getTitle());
            agendaField.setText(mediaItems.get(i).getAgenda());
            String temp = mediaItems.get(i).getFrom().format(formatter);
            split = temp.split(" ");
            localDate = LocalDate.parse(split[0], formatter2);
            fromTimeField.setText(split[1]);
            fromDateField.setValue(localDate);

            temp = mediaItems.get(i).getTo().format(formatter);
            split = temp.split(" ");
            localDate = LocalDate.parse(split[0], formatter2);
            toTimeField.setText(split[1]);
            toDateField.setValue(localDate);
        }
        mediaItems.clear();
        refreshList();
    }



    public void notesAction(ActionEvent actionEvent) {
    }

    public void noteUpdateAction(ActionEvent actionEvent) {
    }

    public void noteDeleteAction(ActionEvent actionEvent) {
    }

    public void saveAction(ActionEvent actionEvent) {
        mediaItems.clear();

        if (missingField()) {
            logger.warn("Some fields are empty!");
        } else {
            logger.debug("Inputs have been submitted!");
            manager.updateItem(
                    titleField.getText(),
                    getFrom(),
                    getTo(),
                    agendaField.getText(),
                    hiddenID
            );
        }
        clearForm();
        refreshList();
    }

    public void reportAction(ActionEvent actionEvent) throws IOException {
        int i = meetingListView.getSelectionModel().getSelectedIndex();
        manager.generatePDF(
                mediaItems.get(i).getTitle(),
                mediaItems.get(i).getFrom(),
                mediaItems.get(i).getTo(),
                mediaItems.get(i).getAgenda(),
                mediaItems.get(i).getID()
        );
    }

    private boolean missingField() {
        if (
                titleField.getText().isEmpty()
                || fromTimeField.getText().isEmpty()
                || fromDateField.getValue() == null
                || toTimeField.getText().isEmpty()
                || toDateField.getValue() == null
                || agendaField.getText().isEmpty()
        ) {
            return true;
        }
        return false;
    }

    private void clearForm() {
        searchMeetingField.textProperty().setValue("");
        titleField.textProperty().setValue("");
        fromDateField.getEditor().clear();
        fromTimeField.textProperty().setValue("");
        toDateField.getEditor().clear();
        toTimeField.textProperty().setValue("");
        agendaField.textProperty().setValue("");
        notesField.textProperty().set("");

    }
    private String getFrom() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateTime = fromDateField.getValue().format(formatter) + " " + fromTimeField.getText();
        return dateTime;
    }

    private String getTo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateTime = toDateField.getValue().format(formatter) + " " + toTimeField.getText();
        return dateTime;
    }

    private void refreshList() {
        List<MediaItem> items = manager.GetItems();
        mediaItems.addAll(items);
    }
    //endregion

    //region Init
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initListView();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


    private void initListView() throws IOException {
        manager = AppManagerFactory.GetManager();   //get the latest manager
        mediaItems = FXCollections.observableArrayList();   //initialize observable List
        mediaItems.addAll(manager.GetItems());    //add all items to Mediaitems from manager singleton
        meetingListView.setItems(mediaItems); //add media items from observable to listview

        //format cells to show only the name
        meetingListView.setCellFactory((param -> new ListCell<MediaItem>() {
            @Override
            protected void updateItem(MediaItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || (item == null) || item.getTitle() == null) {
                    setText(null);
                } else {
                    setText(item.getTitle());
                }
            }
        }));

        //set current selected item
        meetingListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if ((newValue != null) && (oldValue != newValue)){
                currentItem = newValue;
            }
        }));
    }

    /*private void initNotesView() throws IOException {
        manager = AppManagerFactory.GetManager();   //get the latest manager
        mediaNotes = FXCollections.observableArrayList();   //initialize observable List
        mediaNotes.addAll(manager.GetNotes());    //add all items to Mediaitems from manager singleton
        notesListView.setItems(mediaNotes); //add media items from observable to listview

        //format cells to show only the name
        meetingListView.setCellFactory((param -> new ListCell<MediaNotes>() {
            @Override
            protected void updateItem(MediaNotes item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || (item == null) || item.getNotes()) == null) {
                    setText(null);
                } else {
                    setText(item.getNotes());
                }
            }
        }));

        //set current selected item
        notesListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if ((newValue != null) && (oldValue != newValue)){
                currentNote = newValue;
            }
        }));
    }*/




    //endregion
}