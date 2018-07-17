package example;

import analyzer.CustomKoreanAnalyzer;
import helper.CsvLoader;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
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
public class FieldSortExample {
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
        int maxHitCount = 10;

        System.out.println("소요시간 오름차순 정렬==================");
        getSortByHourQuery(index, maxHitCount);

        System.out.println("소요시간 내림차순 정렬==================");
        getSortByReverseHourQuery(index, maxHitCount);
    }


    private static void getSortByHourQuery(Directory index, int maxHitCount) throws Exception{
        SearchService searchService = new SearchService();

        // 원하는 정렬 필드를 이용해 Sort 생성
        Query query = new QueryParser("description", new CustomKoreanAnalyzer()).parse("벚꽃");
        Sort sort = new Sort(new SortField("hour", SortField.Type.DOUBLE));

        //결과를 출력한다
        printResult(index, maxHitCount, query, sort);
    }

    private static void getSortByReverseHourQuery(Directory index, int maxHitCount) throws Exception{
        SearchService searchService = new SearchService();

        // 원하는 정렬 필드를 이용해 Sort 생성
        Query query = new QueryParser("description", new CustomKoreanAnalyzer()).parse("벚꽃");

        //내림차순 정렬
        Sort sort = new Sort(new SortField("hour", SortField.Type.DOUBLE, true));

        //결과를 출력한다
        printResult(index, maxHitCount, query, sort);
    }

    private static void printResult(Directory index, int maxHitCount, Query query, Sort sort) throws Exception{
        int hitsPerPage = maxHitCount;

        try(IndexReader reader = DirectoryReader.open(index)){
            IndexSearcher searcher = new IndexSearcher(reader);
            //쿼리, hitsPerPage, sort를 파라미터로 지정한다
            TopDocs docs = searcher.search(query, hitsPerPage, sort);
            ScoreDoc[] hits = docs.scoreDocs;
            System.out.println(hits.length + " 개의 결과를 찾았습니다.");
            for(int i=0;i<hits.length;++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                System.out.println((i + 1) + ". ");
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
