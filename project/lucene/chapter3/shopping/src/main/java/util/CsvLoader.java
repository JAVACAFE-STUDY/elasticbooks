package util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import vo.CustomerReview;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvLoader {

    public static List<CustomerReview> readReview(){
        //CSV 파일 Path 를 설정한다.
        String fileName = "src/main/resources/WomensClothingSample.csv";
        List<CustomerReview> reviewList = null;
        try(Reader reader = Files.newBufferedReader(Paths.get(fileName))){
            CsvToBean<CustomerReview> csvToBean = new CsvToBeanBuilder<CustomerReview>(reader)
                    .withType(CustomerReview.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            reviewList = csvToBean.parse();
        }catch (Exception e){
            e.printStackTrace();
        }
        return reviewList;

    }
}
