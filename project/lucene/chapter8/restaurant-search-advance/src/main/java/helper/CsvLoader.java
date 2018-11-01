package helper;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import vo.RestaurantInfoVo;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by hwang on 2018. 4. 8..
 */
public class CsvLoader {
    public List<RestaurantInfoVo> readRestaurantnfo(){
        // CSV 파일 경로를 설정한다.
        String fileName = "src/main/resources/restaurant_Info_explain_20160906.csv";
        List<RestaurantInfoVo> restaurantInfoList = null;

        try(Reader reader = Files.newBufferedReader(Paths.get(fileName))){
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(RestaurantInfoVo.class)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            restaurantInfoList = csvToBean.parse();
        }catch (Exception e){
            e.printStackTrace();
        }
        return restaurantInfoList;
    }
}
