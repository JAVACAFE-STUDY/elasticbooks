package example;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import util.CsvLoader;
import vo.CustomerReview;

import java.util.List;

/**
 * example.RamDirectoryExample 에 대한 예제입니다
 */
public class RamDirectoryExample {
    public static void main(String args[]) {
        //쇼핑몰 후기 데이터를 가져온다
        RamDirectoryExample example = new RamDirectoryExample();
        List<CustomerReview> reviewList = example.collectData();

        //RAMDirectory 사용한다.
        Directory ramDirectory = new RAMDirectory();

        //색인을 한다
        IndexService indexService = new IndexService();
        indexService.indexCustomerReview(ramDirectory, reviewList);

        //검색을 한다
        example.searchResult(ramDirectory);

    }

    /**
     * 쇼핑몰 후기 데이터를 CSV에서 데이터를 가져온다
     * @return
     */
    public List<CustomerReview> collectData(){
        //CSV 파일로 부터 데이터를 읽어온다.
        List<CustomerReview> reviewList = CsvLoader.readReview();
        return reviewList;
    }

    /**
     * 분석 결과를 확인한다
     * @param directory
     * @param
     */
    public void searchResult(Directory directory){

        //분석 결과를 확인한다.
        //필드 이름이 reviewText이며 질의어가 shirt인 경우
        SearchService searchService = new SearchService();
        String userQuery = "shirt";
        String fieldName = "reviewText";
        int maxHitCount = 10;
        searchService.serach(directory, fieldName, userQuery, maxHitCount);
    }
}
