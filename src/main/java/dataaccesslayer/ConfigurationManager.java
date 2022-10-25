package dataaccesslayer;

import logger.ILoggerWrapper;
import logger.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {


    public static String GetConfigPropertyValue(String propertyName) throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
            return prop.getProperty(propertyName);
        }
        throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");

    }
}
