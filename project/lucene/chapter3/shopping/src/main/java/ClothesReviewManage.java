import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import util.CsvLoader;
import vo.CustomerReview;

import java.util.List;

public class ClothesReviewManage {
    public static void main(String args[]) {
        //CSV 파일로 부터 데이터를 읽어온다.
        List<CustomerReview> reviewList = CsvLoader.readReview();

        //Directory를 사용한다.
        Directory index = new RAMDirectory();

        IndexService indexService = new IndexService();
        indexService.indexCustomerReview(index, reviewList);

        //분석 결과를 확인한다.
        SearchService searchService = new SearchService();
        String userQuery = "shirt";
        String fieldName = "reviewText";
        int maxHitCount = 10;
        searchService.serach(index, fieldName, userQuery, maxHitCount);
    }
}
