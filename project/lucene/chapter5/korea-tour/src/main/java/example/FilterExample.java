package example;

import helper.CsvLoader;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

/**
 * Created by hwang on 2018. 6. 6..
 */
public class FilterExample {
    public static void main(String args[]) throws Exception{
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();

        //Directory를 사용한다.
        Directory index = new RAMDirectory();

        //색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexTourInfo(index, tourInfoList);

        //분석 결과를 확인한다.
        SearchService searchService = new SearchService();
        int maxHitCount = 10;

        System.out.println("\n==========QUERY");
        getTermQuery(index, maxHitCount);
        System.out.println("\n==========MUST QUERY");
        getMustQuery(index, maxHitCount);
        System.out.println("\n==========FILTER QUERY");
        getFilterQuery(index, maxHitCount);
    }


    public static void getTermQuery(Directory index, int maxHitCount) throws Exception{
        TermQuery termQuery = new TermQuery(new Term("description","바다"));
        printResult(index, maxHitCount, termQuery);
    }

    public static void getMustQuery(Directory index, int maxHitCount) throws Exception{
        Query baseQuery = new QueryParser("description", new KoreanAnalyzer()).parse("바다");

        Query filterQuery = DoublePoint.newRangeQuery("hour", 1d, 2d);

        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(new BooleanClause(baseQuery, BooleanClause.Occur.MUST))
                .add(new BooleanClause(filterQuery, BooleanClause.Occur.MUST))
                .build();

        printResult(index, maxHitCount, booleanQuery);
    }

    public static void getFilterQuery(Directory index, int maxHitCount) throws Exception{
        Query baseQuery = new QueryParser("description", new KoreanAnalyzer()).parse("바다");

        Query filterQuery = DoublePoint.newRangeQuery("hour", 1d, 2d);

        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(new BooleanClause(baseQuery, BooleanClause.Occur.MUST))
                .add(new BooleanClause(filterQuery, BooleanClause.Occur.FILTER))
                .build();

        printResult(index, maxHitCount, booleanQuery);
    }

    private static void printResult(Directory index, int maxHitCount, Query query) throws Exception{
        int hitsPerPage = maxHitCount;

        try(IndexReader reader = DirectoryReader.open(index)){
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(query, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;
            System.out.println(hits.length + " 개의 결과를 찾았습니다.");
            for(int i=0;i<hits.length;++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                System.out.println((i + 1) + ". ");
                System.out.println("- Score : " + hits[i].score);
                System.out.println("[길 이름] " + "\t" + d.get("pathName"));
                System.out.println("[코스 이름] " + "\t" + d.get("courseName"));
                System.out.println("[소요 시간] " + "\t" + d.get("hour"));
                System.out.println("[코스 설명] " + "\t" + d.get("description"));
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
