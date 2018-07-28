package example;

import analyzer.CustomKoreanAnalyzer;
import helper.CsvLoader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import vo.TourInfo;

import java.util.List;

/**
 * 하이라이터 예제
 */
public class HighlighterExample {
    public static void main(String[] args) throws Exception {
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();

        //Directory를 사용한다.
        Directory dir = new RAMDirectory();

        //색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexTourInfo(dir, tourInfoList);

        IndexReader reader = DirectoryReader.open(dir);

        IndexSearcher searcher = new IndexSearcher(reader);

        //한국어 분석기를 설정한다
        Analyzer analyzer = new CustomKoreanAnalyzer();

        //설명글이 "벚꽃"인 문서를 조회하는 쿼리 파서를 만든다
        QueryParser qp = new QueryParser("description", analyzer);

        //쿼리파서를 이용해 쿼리를 만든다
        Query query = qp.parse("벚꽃");

        //결과를 조회한다
        TopDocs hits = searcher.search(query, 10);

        /** 하이라이터 시작 ****/

        //검색된 결과에서 강조 표시를 하기위한 포맷터를 가져온다
        Formatter formatter = new SimpleHTMLFormatter();

        //결과에서 발견된 고유한 쿼리 용어의 수에 따라 텍스트 조각에 점수를 매긴다
        QueryScorer scorer = new QueryScorer(query);

        //formatter, scorer를 사용하여 하이라이터 객체를 만든다
        Highlighter highlighter = new Highlighter(formatter, scorer);

        //텍스트를 같은 크기의 텍스트로 나누지만 범위를 분할하지 않는다.
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, 10);

        //하이라이터에 텍스트 조각을 설정한다
        highlighter.setTextFragmenter(fragmenter);

        //결과를 출력한다
        for (int i = 0; i < hits.scoreDocs.length; i++)
        {
            int docid = hits.scoreDocs[i].doc;
            Document doc = searcher.doc(docid);
            String title = doc.get("pathName");

            System.out.println("길 이름 : " + title);

            //도큐먼트에서 데이터 가져온다
            String text = doc.get("description");

            //token stream을 생성한다.
            TokenStream stream = analyzer.tokenStream("description", text);

            //하이라이트 된 텍스트 조각을 가져온다.
            String[] frags = highlighter.getBestFragments(stream, text, 10);
            for (String frag : frags)
            {
                System.out.println(frag);
            }
            System.out.println("=======================");

        }
        dir.close();
    }

}
