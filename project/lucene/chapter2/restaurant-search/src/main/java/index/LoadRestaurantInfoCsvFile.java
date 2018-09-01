package index;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import util.PropertyLoader;
import vo.RestaurantInfoVo;

public class LoadRestaurantInfoCsvFile {
    private String restaurantInfoCsvFilePath;

    public LoadRestaurantInfoCsvFile() {
        restaurantInfoCsvFilePath = PropertyLoader.getInstance().getPropertyValue("RESTAURANT_INFO_CSV_FILE_PATH");
    }

    public List<RestaurantInfoVo> loadRestaurantInfo() {
        List<RestaurantInfoVo> restaurantInfos = null;

        // 파일에서 스트림 형태로 음식점 정보를 한줄 씩 읽는다
        try(Reader reader = Files.newBufferedReader(Paths.get(restaurantInfoCsvFilePath))) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                .withType(RestaurantInfoVo.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
            restaurantInfos = csvToBean.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurantInfos;
    }
}
