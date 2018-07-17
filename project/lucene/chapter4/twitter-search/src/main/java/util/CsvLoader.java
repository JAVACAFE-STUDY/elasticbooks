package util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import vo.TweetPost;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvLoader {

    public List<TweetPost> readEnglishReview(){
        //CSV 파일 Path 를 설정한다.
        String fileName = "src/main/resources/tweet_en.csv";
        return getReview(fileName);
    }

    public List<TweetPost> readKoreanReview(){
        String fileName = "src/main/resources/tweet_ko.csv";
        return getReview(fileName);
    }

    public static List<TweetPost> getReview(String fileName){

        List<TweetPost> reviewList = null;
        try(Reader reader = Files.newBufferedReader(Paths.get(fileName))){
            CsvToBean<TweetPost> csvToBean = new CsvToBeanBuilder<TweetPost>(reader)
                    .withType(TweetPost.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            reviewList = csvToBean.parse();
        }catch (Exception e){
            e.printStackTrace();
        }
        return reviewList;
    }

}
