package helpers;

import com.google.common.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FileHelper {

    private static final Logger LOGGER = LogManager.getLogger(FileHelper.class);

    public static String loadResourceData(String resourceFile) {
        String resourceContent = "";
        URL url = Resources.getResource(resourceFile);
        try {
            resourceContent = Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Unable to load resource {}: {}", resourceFile, e.getMessage());
        }
        return resourceContent;
    }
}
