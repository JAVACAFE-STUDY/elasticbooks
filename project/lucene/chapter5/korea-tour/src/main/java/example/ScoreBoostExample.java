package example;

import helper.CsvLoader;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

public class ScoreBoostExample {
    public static void main(String args[]) throws Exception{
        // CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();

        // Directory를 사용한다.
        Directory index = new RAMDirectory();

        // 색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexTourInfo(index, tourInfoList);

        // 분석 결과를 확인한다.
        SearchService searchService = new SearchService();
        int maxHitCount = 10;

        System.out.println("Boosting을 적용하지 않은 예제=====================");
        BooleanQuery query = getTermQuery(index, maxHitCount);
        getQueryResult(index, maxHitCount, query);

        System.out.println("Boosting을 적용한 예제=====================");
        BooleanQuery query2 = getBoostQuery(index, maxHitCount);
        getQueryResult(index, maxHitCount, query2);

    }

    public static BooleanQuery getTermQuery(Directory index, int maxHitCount) throws Exception{
        Query termQuery = new TermQuery(new Term("title", "남해"));
        Query termQuery2 = new TermQuery(new Term("description", "바다"));

        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(new BooleanClause(termQuery, BooleanClause.Occur.SHOULD))
                .add(new BooleanClause(termQuery2, BooleanClause.Occur.SHOULD))
                .build();
        return booleanQuery;
    }


    public static BooleanQuery getBoostQuery(Directory index, int maxHitCount) throws Exception{
        Query termQuery = new TermQuery(new Term("title", "남해"));

        Query termQuery2 = new TermQuery(new Term("description", "바다"));
        Query boostedTermQuery = new BoostQuery(termQuery2, 2f);

        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(new BooleanClause(termQuery, BooleanClause.Occur.SHOULD))
                .add(new BooleanClause(boostedTermQuery, BooleanClause.Occur.SHOULD))
                .build();

        return booleanQuery;
    }

    public static void getQueryResult(Directory index, int maxHitCount, Query query) throws Exception{
        int hitsPerPage = maxHitCount;

        try(IndexReader reader = DirectoryReader.open(index)){
            IndexSearcher searcher = new IndexSearcher(reader);

            TopDocs docs = searcher.search(query, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            System.out.println(hits.length + " 개의 결과를 찾았습니다.");
            for(int i=0;i<hits.length;++i) {
                int docId = hits[i].doc;
                System.out.println("-----------------");
                System.out.println(searcher.explain(query, docId));
                Document d = searcher.doc(docId);
                System.out.println((i + 1) + ". ");
                System.out.println("[길 이름] " + "\t" + d.get("pathName"));
                System.out.println("[지역] " + "\t" + d.get("area"));
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
