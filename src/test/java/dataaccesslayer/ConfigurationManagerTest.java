package dataaccesslayer;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationManagerTest {
    ConfigurationManager configurationManager;

    @Test
    public void testGetConfigPropertyValue() throws IOException {
        assertNotNull(ConfigurationManager.GetConfigPropertyValue("name"));
        assertNotNull(ConfigurationManager.GetConfigPropertyValue("pw"));
        assertNotNull(ConfigurationManager.GetConfigPropertyValue("connectionString"));
    }
}