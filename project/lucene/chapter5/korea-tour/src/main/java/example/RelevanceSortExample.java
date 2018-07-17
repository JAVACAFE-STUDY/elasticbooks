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
import vo.TourInfo;

import java.util.List;

/**
 * 유사도 정렬에 대한 예제
 */
public class RelevanceSortExample {

    public static void main(String args[]) throws Exception{
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();

        //Directory를 사용한다.
        Directory index = new RAMDirectory();

        //색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexTourInfo(index, tourInfoList);

        //검색을 한다
        int maxHitCount = 10;
        TermQuery termQuery = new TermQuery(new Term("description","계곡"));

        //Sort 순으로 정렬한다
        getQueryResult(index, maxHitCount, termQuery);
    }


    /**
     * 결과를 가져온다
     * @param index
     * @param maxHitCount
     * @param query
     * @throws Exception
     */
    public static void getQueryResult(Directory index, int maxHitCount, Query query) throws Exception{
        int hitsPerPage = maxHitCount;

        try(IndexReader reader = DirectoryReader.open(index)){
            IndexSearcher searcher = new IndexSearcher(reader);
            //관련도 기준으로 정렬한다
            TopDocs docs = searcher.search(query, hitsPerPage, Sort.RELEVANCE, true, false);
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
