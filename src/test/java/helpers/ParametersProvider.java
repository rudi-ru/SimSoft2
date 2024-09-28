package helpers;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// Извлекаем данные из local_env.properties
public class ParametersProvider {
    private static ParametersProvider instance;
    private List<Properties> propertiesList = new ArrayList<>();

    private ParametersProvider() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/test/resources/local_env.properties"));
        propertiesList.add(properties);
    }

    public static synchronized ParametersProvider getInstance() throws IOException {
        if (instance == null) {
            instance = new ParametersProvider();
        }
        return instance;
    }

    public static String getProperty(final String key) throws IOException {
        for (Properties properties : getInstance().propertiesList) {
            String result = properties.getProperty(key, null);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}

