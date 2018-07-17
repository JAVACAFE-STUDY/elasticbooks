package example;

import java.util.List;

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

public class NoriKoreanAnalyzerExample {
    public static void main(String[] args) throws Exception {
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readKoreanReview();
        reviewList.forEach(System.out::println);

        //색인을 한다.
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(new KoreanAnalyzer());
        IndexService.indexingTweetData(reviewList, index, config);
        Query q = new QueryParser("text", new KoreanAnalyzer()).parse("먼지");

        SearchService.searchTweetData(index, q);
    }
}
