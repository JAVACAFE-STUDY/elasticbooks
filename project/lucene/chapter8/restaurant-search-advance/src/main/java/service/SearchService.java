package service;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

/**
 * Created by hwang on 2018. 4. 8..
 */
public class SearchService {


    /**
     *     // 음식점 명
     @CsvBindByPosition(position = 0)
     private String restaurantName;
     // 카테고리 1
     @CsvBindByPosition(position = 1)
     private String category1;
     // 카테고리 2
     @CsvBindByPosition(position = 2)
     private String category2;
     // 카테고리 3
     @CsvBindByPosition(position = 3)
     private String category3;
     // 지역명
     @CsvBindByPosition(position = 4)
     private String region;
     // 시군구명
     @CsvBindByPosition(position = 5)
     private String city;
     // 개요
     @CsvBindByPosition(position = 6)
     private String description;
     * @param index
     * @param maxHitCount
     * @param query
     * @throws Exception
     */
    public void getQueryResult(Directory index, int maxHitCount, Query query) throws Exception{
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
                System.out.println("[음식점명] " + "\t" + d.get("restaurantName"));
                System.out.println("[카테고리 1] " + "\t" + d.get("category1"));
                System.out.println("[카테고리 2] " + "\t" + d.get("category2"));
                System.out.println("[카테고리 3] " + "\t" + d.get("category3"));
                System.out.println("[지역명] " + "\t" + d.get("region"));
                System.out.println("[시군구명] " + "\t" + d.get("city"));
                System.out.println("[개요] " + "\t" + d.get("description"));
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }





}
