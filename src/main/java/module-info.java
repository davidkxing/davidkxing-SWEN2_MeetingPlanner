module views{
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires kernel;
    requires io;
    requires layout;

    opens views to javafx.fxml;
    exports views;
}