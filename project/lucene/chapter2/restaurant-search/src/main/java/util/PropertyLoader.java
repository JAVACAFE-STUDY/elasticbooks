package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyLoader {
    private static final String PROPERTY_FILE_LOCATION="src/main/resources/restaurantInfoSearch.properties";
    private Map<String, String> propMap;

    private static final PropertyLoader instance = new PropertyLoader();
    private PropertyLoader() {
        loadProperties();
    }

    public String getPropertyValue(String key) {
        return propMap.get(key);
    }

    private void loadProperties() {
        // 프로퍼티를 저장할 Perperties 객체를 생성한다.
        Properties prop = new Properties();
        // 프로퍼티 파일이 저장되어 있는 경로를 가져온다.
        Path path = Paths.get(PROPERTY_FILE_LOCATION);
        try {
            // FileInputStream으로 설정 파일에서 데이터를 읽어 온다
            prop.load(new FileInputStream(path.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        propMap = new HashMap<>();
        // 프로퍼티 검색이 용이하도록 map에 추가한다
        prop.forEach((key, value) -> propMap.put((String)key, (String)value));
    }

    public static PropertyLoader getInstance() {
        return instance;
    }
}
