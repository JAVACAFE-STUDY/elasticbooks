package example;

import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import service.SearchService;
import util.CsvLoader;
import vo.CustomerReview;

public class FieldExample {
	public static void main(String args[]) {
		List<CustomerReview> reviewList = CsvLoader.readReview().subList(1,50);
		reviewList.forEach(System.out::println);

		Directory index = new RAMDirectory();

		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());

		try(IndexWriter w = new IndexWriter(index, config)) {
			reviewList.stream().forEach( review-> {
				Document doc = new Document();
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
