package index;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import analyzer.CustomKoreanAnalyzer;
import util.PropertyLoader;
import vo.RestaurantInfoVo;

public class IndexRestaurantInfo {
    // 1. 색인할 경로
    private String indexPath;

    // 2. 클래스 생성 시 색인 경로를 설정한다.
    public IndexRestaurantInfo() {
        indexPath = PropertyLoader.getInstance().getPropertyValue("INDEX_DIR_PATH");
    }

    // 3. 음식점 정보를 색인하는 메소드
    public void indexRestaurantInfo(List<RestaurantInfoVo> restaurantInfoVos) throws Exception {

        // 1. 색인 작업을 파일 시스템에 한다.
        Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
        // 2. 한국어 분석을 위한 analyzer를 선언한다.
        CustomKoreanAnalyzer analyzer = new CustomKoreanAnalyzer();

        // 3. 인덱스 생성을 위한 Writer 설정 정보를 구성한다. 한글로 되어 있는 음식점 정보를 분석하기 위해 한글 분석기를 설정한다.
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 4. 색인 directory와 설정 정보로 writer를 생성한다.
        IndexWriter writer = new IndexWriter(indexDirectory, config);

        // 5. List<RestaurantInfoVo>에서 하나씩 색인한다.
        restaurantInfoVos.stream().forEach(i -> addDocument(i, writer));
        // 6. 색인작업이 끝난 후 writer를 종료한다.
        writer.close();
    }

    // 4. 음식점 정보를 하나씩 색인에 쓰는 메소드
    private void addDocument(RestaurantInfoVo restaurantInfoVo, IndexWriter writer) {
        // 1. Document 생성
        Document doc = new Document();

        try{
            // 2. 음식점 명 field 추가
            doc.add(new TextField("restaurantName", restaurantInfoVo.getRestaurantName(), Field.Store.YES));
            // 3. category1 field 추가
            doc.add(new StringField("category1", restaurantInfoVo.getCategory1(), Field.Store.YES));
            // 4. category2 field 추가
            doc.add(new StringField("category2", restaurantInfoVo.getCategory2(), Field.Store.YES));
            // 5. category3 field 추가
            doc.add(new StringField("category3", restaurantInfoVo.getCategory3(), Field.Store.YES));
            // 6. region field 추가
            doc.add(new StringField("region", restaurantInfoVo.getRegion(), Field.Store.YES));
            // 7. city field 추가
            doc.add(new StringField("city", restaurantInfoVo.getCity(), Field.Store.YES));
            // 8. description field 추가
            doc.add(new TextField("description", restaurantInfoVo.getDescription(), Field.Store.YES));

            // 9. 색인에 추가
            writer.addDocument(doc);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
