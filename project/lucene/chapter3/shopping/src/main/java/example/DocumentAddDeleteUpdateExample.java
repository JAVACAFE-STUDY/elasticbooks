package example;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import service.IndexService;
import service.SearchService;
import util.CsvLoader;
import vo.CustomerReview;

public class DocumentAddDeleteUpdateExample {
	public static void main(String args[]) {
		//쇼핑몰 후기 데이터를 가져온다
		DocumentAddDeleteUpdateExample example = new DocumentAddDeleteUpdateExample();
        List<CustomerReview> reviewList = example.collectData().subList(0, 100);

        //RAMDirectory 사용한다.
        Directory ramDirectory = new RAMDirectory();

        //색인을 한다
        IndexService indexService = new IndexService();
        indexService.indexCustomerReview(ramDirectory, reviewList);

        // 검색을 한다
        example.searchResult(ramDirectory);
        
        // love가 들어가 있는 구매자 리뷰를 삭제 한다.
        indexService.deleteCustomerReview(ramDirectory, "love");
        
        // 다시 검색 한다.
        example.searchResult(ramDirectory);
        
        // 변경할 문서를 생성한다.
        Document doc = new Document();
        doc.add(new StringField("reviewId", "66", Field.Store.YES));
        doc.add(new TextField("clothingId", "862", Field.Store.YES));
        doc.add(new TextField("title", "Super cute and unique top_modified", Field.Store.YES));
        doc.add(new TextField("reviewText", "Just received this in the mail, tried it on and am smitten. i'm usually a l, but sometimes i'm a xl (if no stretch), in retailer tops. i bought this one in l and i'm sure glad i did. very flowy, stretchy and comfortable. i also bought the meda lace top from one september and they are very similar expect this is more of a t-shirt and the other is more of a blouse. i almost think i could've gotten a m in this because there is a lot of extra fabric at the chest which is usually never the issue for me", Field.Store.YES));
        doc.add(new TextField("rating", "4", Field.Store.YES));
        
        // 변경할 문서를 생성한다.
        indexService.updateCustomerReview(ramDirectory, "66", doc);
        
        // 다시 검색 한다.
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
