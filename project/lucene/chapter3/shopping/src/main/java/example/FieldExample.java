package example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.SearchService;
import util.CsvLoader;
import vo.CustomerReview;

import java.util.Date;
import java.util.List;

public class FieldExample {
	public static void main(String args[]) {
		List<CustomerReview> reviewList = CsvLoader.readReview().subList(1,50);

		// 디렉터리를 생성한다.
		Directory index = new RAMDirectory();

		// 사용할 분석기를 설정한다.
		Analyzer analyzer = new StandardAnalyzer();

		// 색인 생성에 필요한 IndexWriter를 구성한다.
		IndexWriterConfig config = new IndexWriterConfig(analyzer);

		try(IndexWriter w = new IndexWriter(index, config)) {
			reviewList.stream().forEach( review-> {
				// 도큐먼트를 생성한다.
				Document doc = new Document();

				// 필드를 추가한다.
				doc.add(new StringField("reviewId", review.getReviewId(), Field.Store.YES));
				doc.add(new TextField("clothingId", review.getClothingId(), Field.Store.YES));
				doc.add(new TextField("title", review.getTitle(), Field.Store.YES));
				doc.add(new TextField("reviewText", review.getReviewText(), Field.Store.YES));
				doc.add(new StoredField("age", Integer.parseInt(review.getAge())));
				Date modifiedDate = new Date();
				doc.add(new StringField("systemYear",
					DateTools.dateToString(modifiedDate, DateTools.Resolution.DAY)
					,Field.Store.YES));

				try{
					// 도큐먼트를 색인에 추가한다.
					w.addDocument(doc);
				}catch (Exception e){
					e.printStackTrace();
				}
			});
		}catch (Exception e){
			e.printStackTrace();
		}

		SearchService searchService = new SearchService();
		searchService.serach(index, "reviewText","love", 10);
	}
}
