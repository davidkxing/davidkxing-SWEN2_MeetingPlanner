package businesslayer;

import dataaccesslayer.MediaItemDAO;
import logger.ILoggerWrapper;
import logger.LoggerFactory;
import models.MediaItem;
import views.MainController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

public class AppManagerImpl implements AppManager{

    //access DAL
    MediaItemDAO mediaItemDAO = new MediaItemDAO();
    public static final String TARGET_PDF = "MeetingPlanner.pdf";
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public AppManagerImpl() throws IOException {
    }

    @Override
    public List<MediaItem> GetItems() {
        return mediaItemDAO.GetItems();
    }

    @Override
    public List<MediaItem> Search(String itemName, boolean caseSensitive) {
        List<MediaItem> items = GetItems();


        //check for caseSensitive
        if (caseSensitive) {
            return items
                    .stream()
                    .filter(x -> x.getTitle().contains(itemName))
                    .collect(Collectors.toList());
        }

        return items
                .stream()
                .filter(x -> x.getTitle().toLowerCase().contains(itemName.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void SetItem(String title, String from, String to, String agenda) {

        mediaItemDAO.SetItem(
                title,
                from,
                to,
                agenda
        );
    }

    @Override
    public void deleteItem(int i) {
        mediaItemDAO.deleteItem(i);
    }

    @Override
    public void setWindow() {

    }


    @Override
    public void updateItem(String title, String from, String to, String agenda, Integer id) {
        mediaItemDAO.updateItem(
                title,
                from,
                to,
                agenda,
                id
        );
    }

    @Override
    public void generatePDF(String title, LocalDateTime from, LocalDateTime to, String agenda, Integer id) throws IOException {
        String[] split;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String temp = from.format(formatter);

        PdfWriter writer = new PdfWriter(TARGET_PDF);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph meeting = new Paragraph("Title: " + title)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(30)
                .setBold();
        document.add(meeting);

        split = temp.split(" ");
        Paragraph From = new Paragraph("\nFrom: " + split[0] + " " + split[1])
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(20)
                .setBold();
        document.add(From);

        temp = to.format(formatter);
        split = temp.split(" ");
        Paragraph To = new Paragraph("To: " + split[0] + " " + split[1])
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(20)
                .setBold();
        document.add(To);

        Paragraph agendas = new Paragraph("\nAgenda: \n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(20)
                .setBold();
        document.add(agendas);
        document.add(new Paragraph(agenda).setFontSize(20));
        document.close();
        logger.debug("PDF created successfully");
    }


}
