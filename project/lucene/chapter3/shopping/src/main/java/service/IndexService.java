package service;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import vo.CustomerReview;

import java.util.List;

public class IndexService {
    public void indexCustomerReview(Directory index, List<CustomerReview> reviewList){
        //분석기를 설정을 한다.
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());

		// IndexWriter 생성
        try(IndexWriter w = new IndexWriter(index, config)) {
            reviewList.stream().forEach( review-> {

				// Document 생성
                Document doc = new Document();
                doc.add(new StringField("reviewId", review.getReviewId(), Field.Store.YES));
                doc.add(new TextField("clothingId", review.getClothingId(), Field.Store.YES));
                doc.add(new TextField("title", review.getTitle(), Field.Store.YES));
                doc.add(new TextField("reviewText", review.getReviewText(), Field.Store.YES));
                doc.add(new StringField("age", review.getAge(), Field.Store.YES));

                try{
					// 인덱스에 Document 추가
                    w.addDocument(doc);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });

            w.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void deleteCustomerReview(Directory index, String word) {
    	StandardAnalyzer analyzer = new StandardAnalyzer();
    	IndexWriterConfig config = new IndexWriterConfig(analyzer);
    	
    	try(IndexWriter w = new IndexWriter(index, config)) {
			// 삭제하기 전 색인된 도큐먼트의 수를 확인한다.
    		System.out.println("before delete. numDocs=" + w.numDocs());

			// 삭제할 대상 도큐먼트를 고르는 쿼리.
    		Query q = new QueryParser("reviewText", analyzer).parse(word);

			// 쿼리의 대상이 되는 도큐먼트를 삭제한다.
    		w.deleteDocuments(q);

			// 삭제 후 도큐먼트 수를 확인한다.
    		System.out.println("after delete. numDocs="+ w.numDocs());

			// 삭제 처리에 대한 flush를 수행한다.
    		w.flush();

			// flush를 실행한 후 도큐먼트 수를 확인한다.
    		System.out.println("after delete and flush. numDocs="+ w.numDocs());
    		w.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void updateCustomerReview(Directory index, String reviewId, Document document) {
    	StandardAnalyzer analyzer = new StandardAnalyzer();
    	IndexWriterConfig config = new IndexWriterConfig(analyzer);
    	
    	try(IndexWriter w = new IndexWriter(index, config)) {
			// 수정하기 전 색인된 도큐먼트 수를 확인한다.
			System.out.println("before update. numDocs=" + w.numDocs());

			// 수정할 도큐먼트를 설정한다.
    		Term updateDocumentReview = new Term("reviewId", reviewId);

			// 수정할 도큐먼트를 매개변수로 받은 도큐먼트로 교체한다.
    		w.updateDocument(updateDocumentReview, document);

			// 수정 후 도큐먼트 수를 확인한다.
    		System.out.println("after update. numDocs="+ w.numDocs());

			// 수정 작업에 대한 flush를 수행한다.
    		w.flush();

			// flush 후 도큐먼트 수를 확인한다.
    		System.out.println("after update and flush. numDocs="+ w.numDocs());
    		w.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
