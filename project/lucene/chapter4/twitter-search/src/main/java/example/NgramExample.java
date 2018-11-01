package example;

import analyzer.NgramAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import util.CsvLoader;
import vo.TweetPost;

import java.util.List;

public class NgramExample {
    public static void main(String[] args) throws Exception {
        // CSV 파일로부터 데이터를 읽는다.
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readKoreanReview();

        // 색인을 위해 디렉터리를 생성한다.
        Directory index = new RAMDirectory();

        // N-Gram 분석기를 설정한다.
        IndexWriterConfig config = new IndexWriterConfig(new NgramAnalyzer());

        // 트위터 날씨 반응 트윗 데이터를 색인한다.
        IndexService.indexingTweetData(reviewList, index, config);

        // 먼지로 쿼리를 생성한다.
        Query q = new QueryParser("text", new NgramAnalyzer()).parse("미세먼지");

        // 쿼리로 검색한 결과를 확인한다.
        SearchService.searchTweetData(index, q);


    }


}
