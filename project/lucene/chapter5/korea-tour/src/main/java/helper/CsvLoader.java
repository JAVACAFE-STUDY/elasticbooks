package helper;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import vo.TourInfo;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by hwang on 2018. 4. 8..
 */
public class CsvLoader {
    public List<TourInfo>  readTourInfo(){
        // CSV 파일 경로를 설정한다.
        String fileName = "src/main/resources/한국관광공사_걷는길_인코딩_정제본.csv";
        List<TourInfo> tourList = null;

        try(Reader reader = Files.newBufferedReader(Paths.get(fileName))){
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(TourInfo.class)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            tourList = csvToBean.parse();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tourList;
    }
}
