package service;

import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;
import vo.TourInfo;

import java.util.List;

/**
 * Created by hwang on 2018. 4. 8..
 */
public class IndexService {

    public void indexTourInfo(Directory index, List<TourInfo> tourInfoList) throws Exception {
        // 분석기를 설정을 한다.
        KoreanAnalyzer analyzer = new KoreanAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try(IndexWriter w = new IndexWriter(index, config)) {
            tourInfoList.stream().forEach( tourInfo -> {
                addDocument(tourInfo, w);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addDocument(TourInfo tourInfo, IndexWriter writer) {
        Document doc = new Document();
        doc.add(new TextField("pathName", tourInfo.getPathName(), Field.Store.YES));
        doc.add(new TextField("courseName", tourInfo.getCourseName(), Field.Store.YES));
        doc.add(new TextField("area", tourInfo.getArea(), Field.Store.YES));
        doc.add(new StringField("level", tourInfo.getLevel(), Field.Store.YES));
        doc.add(new StoredField("distance", tourInfo.getDistance()));

        double hour = 0;
        if(tourInfo.getHour() != null && (! tourInfo.getHour().equals(""))){
            hour = Double.valueOf(tourInfo.getHour());
        }

        doc.add(new DoublePoint("hour", hour));

        // 값을 저장하려면 동일한 이름의 StoredField를 추가해야 한다.
        doc.add(new StoredField("hour", hour));

        // 값을 저장하려면 같은 이름의 StoredField를 추가해야 한다.
        doc.add(new DoubleDocValuesField("hour", hour));

        doc.add(new TextField("description", tourInfo.getDescription(), Field.Store.YES));

        // 정렬하려면 다음 값을 설정해야 한다.
        doc.add(new SortedDocValuesField("courseName", new BytesRef(tourInfo.getDescription())));

        try{
            writer.addDocument(doc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
