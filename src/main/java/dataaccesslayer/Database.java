package dataaccesslayer;

import logger.ILoggerWrapper;
import logger.LoggerFactory;
import models.MediaItem;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database implements DataAccess{

    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    private final String SQL_DELETE_BY_ID = "DELETE FROM \"Meetings\" WHERE \"MeetingID\" = ?;";
    private final String SQL_GET_ALL_ITEMS = "SELECT * FROM \"Meetings\";";
    private final String SQL_INSERT_NEW_ITEM = "INSERT INTO \"Meetings\" (\"Title\", \"From\", \"To\", \"Agenda\") VALUES (?, ?, ?, ?);";
    private final String SQL_UPDATE_LIST = "UPDATE \"Meetings\" SET \"Title\" = ?, \"From\" = ?, \"To\" = ?, \"Agenda\" = ? WHERE \"MeetingID\" = ?;";

    private final String SQL_INSERT_NEW_NOTE = "INSERT INTO \"Notes\" (\"MeetingID\", \"Notes\") VALUES (?, ?);";
    private final String SQL_UPDATE_NOTE = "UPDATE \"Notes\" SET \"Notes\" = ? where \"MeetingID\" = ?;";
    private final String SQL_GET_NOTE_BY_FK = "SELECT * FROM \"Notes\" WHERE \"MeetingID\" = ?;";
    private final String SQL_DELETE_BY_ID_NOTE = "DELETE FROM \"Notes\" WHERE \"NotesID\" = ?;";

    private String ConnectionString, name, pw;

    List<MediaItem> mediaItems = new ArrayList<MediaItem>();

    //constructor
    public Database() throws IOException {
        //get info from config.properties file
        ConnectionString = ConfigurationManager.GetConfigPropertyValue("connectionString");
        name = ConfigurationManager.GetConfigPropertyValue("name");
        pw = ConfigurationManager.GetConfigPropertyValue("pw");
        // establish DB connection
    }

    private Connection CreateOpenConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(ConnectionString, name, pw);
            return connection;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MediaItem> GetItems() {
        mediaItems.removeAll(mediaItems);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //SQL Statement SELECT * FROM items....
        try (   Connection connection = CreateOpenConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_ITEMS)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    mediaItems.add(new MediaItem(
                            resultSet.getInt("MeetingID"),
                            resultSet.getString("Title"),
                            LocalDateTime.parse(resultSet.getString("From"), formatter),
                            LocalDateTime.parse(resultSet.getString("To"), formatter),
                            resultSet.getString("Agenda")
                    ));
                }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return mediaItems;
    }

    @Override
    public void AddItem(String title, String from, String to, String agenda) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime localDateTime;
        Timestamp timestamp;
        ArrayList<Object> parameters = new ArrayList<Object>();
        parameters.add(title);
        parameters.add(from);
        parameters.add(to);
        parameters.add(agenda);
        //mediaItems.add(new MediaItem(meeting));

        try (   Connection connection = CreateOpenConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_ITEM)) {
            // dynamically add parameter
            for (int i = 0; i < parameters.size(); i++) {
                if (i == 1 || i == 2) {
                    localDateTime = LocalDateTime.from(formatter.parse(parameters.get(i).toString()));
                    timestamp = Timestamp.valueOf(localDateTime);
                    preparedStatement.setTimestamp(i + 1, timestamp);
                } else {
                    preparedStatement.setString(i + 1, parameters.get(i).toString());
                }
            }
            preparedStatement.executeUpdate();
            logger.debug("SQL query successfully executed!");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(int i) {
        if (!mediaItems.isEmpty()) {

            try (   Connection connection = CreateOpenConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {

                preparedStatement.setInt(1, i);
                preparedStatement.executeUpdate();
                logger.debug("SQL query successfully executed!");
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateItem(String title, String from, String to, String agenda, Integer id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime localDateTime;
        Timestamp timestamp;
        ArrayList<Object> parameters = new ArrayList<Object>();
        parameters.add(title);
        parameters.add(from);
        parameters.add(to);
        parameters.add(agenda);
        parameters.add(id);
        //mediaItems.add(new MediaItem(meeting));

        try (   Connection connection = CreateOpenConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_LIST)) {
            // dynamically add parameter
            for (int i = 0; i < parameters.size(); i++) {
                if (i == 1 || i == 2) {
                    localDateTime = LocalDateTime.from(formatter.parse(parameters.get(i).toString()));
                    timestamp = Timestamp.valueOf(localDateTime);
                    preparedStatement.setTimestamp(i + 1, timestamp);
                } else {
                    preparedStatement.setString(i + 1, parameters.get(i).toString());
                }
            }
            preparedStatement.setInt(5, Integer.parseInt(parameters.get(4).toString()));
            preparedStatement.executeUpdate();
            logger.debug("SQL query successfully executed!");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
