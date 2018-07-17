package service;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

public class SearchService {
    public void serach(Directory index, String field, String userQuery, int maxHitCount){
        TermQuery termQuery = new TermQuery(new Term(field,userQuery));

        int hitsPerPage = maxHitCount;

        try(IndexReader reader = DirectoryReader.open(index)){
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(termQuery, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            System.out.println(hits.length + " 개의 결과를 찾았습니다.");
            for(int i=0;i<hits.length;++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                System.out.println((i + 1) + ". ");
                System.out.println("[제목] " + "\t" + d.get("title"));
                System.out.println("[리뷰아이디] " + "\t" + d.get("reviewId"));
                System.out.println("[나이] " + "\t" + d.get("age"));
                System.out.println("[내용] " + "\t" + d.get("reviewText"));
                if(StringUtils.isNotEmpty(d.get("systemYear"))){
                    System.out.println("[시스템연도] " +"\t" + d.get("systemYear"));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
