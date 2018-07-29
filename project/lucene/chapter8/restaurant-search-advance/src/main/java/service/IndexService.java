package service;

import analyzer.CustomKoreanAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import vo.RestaurantInfoVo;

import java.util.List;

/**
 * Created by hwang on 2018. 4. 8..
 */
public class IndexService {

    public void indexRestaurantInfo(Directory index, List<RestaurantInfoVo> restaurantInfoList) throws Exception {
        //분석기를 설정을 한다.
        CustomKoreanAnalyzer analyzer = new CustomKoreanAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try(IndexWriter w = new IndexWriter(index, config)) {
            restaurantInfoList.stream().forEach( restaurantInfo -> {
                addDocument(restaurantInfo, w);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addDocument(RestaurantInfoVo restaurantInfo, IndexWriter writer) {
        Document doc = new Document();
        if(restaurantInfo.getRestaurantName() != null) {
            doc.add(new TextField("restaurantName", restaurantInfo.getRestaurantName(), Field.Store.YES));
        }
        if(restaurantInfo.getCategory1() != null){
            doc.add(new TextField("category1", restaurantInfo.getCategory1(), Field.Store.YES));
        }
        if(restaurantInfo.getCategory2() != null){
            doc.add(new TextField("category2", restaurantInfo.getCategory2(), Field.Store.YES));
        }
        if(restaurantInfo.getCategory3() != null){
            doc.add(new TextField("category3", restaurantInfo.getCategory3(), Field.Store.YES));
        }
        if(restaurantInfo.getRegion() != null){
            doc.add(new TextField("region", restaurantInfo.getRegion(), Field.Store.YES));
        }
        if(restaurantInfo.getCity() != null){
            doc.add(new TextField("city", restaurantInfo.getCity(), Field.Store.YES));
        }
        if(restaurantInfo.getDescription() != null){
            doc.add(new TextField("description", restaurantInfo.getDescription(), Field.Store.YES));
        }

        try{
            writer.addDocument(doc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
