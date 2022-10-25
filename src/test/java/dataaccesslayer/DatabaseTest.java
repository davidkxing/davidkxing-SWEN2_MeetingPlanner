package dataaccesslayer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    Connection connection;
    String ConnectionString;
    String name;
    String pw;

    @BeforeEach
    public void getConfigProperties() throws IOException {
        ConnectionString = ConfigurationManager.GetConfigPropertyValue("connectionString");
        name = ConfigurationManager.GetConfigPropertyValue("name");
        pw = ConfigurationManager.GetConfigPropertyValue("pw");
    }


    @Test
    public void testConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(ConnectionString, name, pw);
        assertNotNull(connection);
    }

}