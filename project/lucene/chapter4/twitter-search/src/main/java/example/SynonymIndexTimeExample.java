package example;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.CharsRef;

import analyzer.SynonymAnalyzer;
import service.IndexService;
import service.SearchService;
import util.CsvLoader;
import vo.TweetPost;

public class SynonymIndexTimeExample {
    public static void main(String args[]) throws Exception{
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readEnglishReview();
        reviewList.forEach(System.out::println);

        //색인을 한다.
        Directory index = new RAMDirectory();

        // 동의어를 처리하기 위해 SynonymMap 설정
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        builder.add(new CharsRef("good"), new CharsRef("nice"), true);
        builder.add(new CharsRef("great"), new CharsRef("nice"), true);

        Analyzer analyzer = new SynonymAnalyzer(builder.build());

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexService.indexingTweetData(reviewList, index, config);


        Query q = new QueryParser("text", new StandardAnalyzer()).parse("nice");

        SearchService.searchTweetData(index, q);
    }
}
