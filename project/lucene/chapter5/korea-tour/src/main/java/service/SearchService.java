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
