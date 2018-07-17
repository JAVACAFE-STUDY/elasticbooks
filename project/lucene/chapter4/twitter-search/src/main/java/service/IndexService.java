package service;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import vo.TweetPost;

public class IndexService {
    public static void indexingTweetData(List<TweetPost> reviewList, Directory index, IndexWriterConfig config) {
        try(IndexWriter w = new IndexWriter(index, config)) {
            for (TweetPost review : reviewList) {
                Document doc = new Document();
                doc.add(new TextField("createdAt", review.getCreatedAt(), Field.Store.YES));
                doc.add(new StringField("id", review.getId(), Field.Store.YES));
                doc.add(new StringField("lang", review.getLang(), Field.Store.YES));
                doc.add(new TextField("text", review.getText(), Field.Store.YES));

                try {
                    w.addDocument(doc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
