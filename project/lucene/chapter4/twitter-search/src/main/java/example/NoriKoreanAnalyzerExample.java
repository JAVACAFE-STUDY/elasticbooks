package example;

import org.apache.lucene.analysis.ko.KoreanAnalyzer;
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

public class NoriKoreanAnalyzerExample {
    public static void main(String[] args) throws Exception {
        // CSV 파일로부터 데이터를 읽어온다.
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readKoreanReview();

        // 색인을 위해 디렉터리를 생성
        Directory index = new RAMDirectory();
        // 노리 한글 형태소 분석기를 설정한다.
        IndexWriterConfig config = new IndexWriterConfig(new KoreanAnalyzer());
        // 트위터 날씨 반응 데이터를 색인한다.
        IndexService.indexingTweetData(reviewList, index, config);

        // 먼지로 쿼리를 생성한다.
        Query q = new QueryParser("text", new KoreanAnalyzer()).parse("먼지");
        // 쿼리로 검색하여 결과를 확인한다.
        SearchService.searchTweetData(index, q);

    }
}
