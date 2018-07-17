package example;

import analyzer.CustomKoreanAnalyzer;
import helper.CsvLoader;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import vo.TourInfo;

import java.util.List;

/**
 * 색인 시 순서 정렬에 대한 예제
 */
public class IndexOrderSortExample {

    public static void main(String args[]) throws Exception{
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();

        //Directory를 사용한다.
        Directory index = new RAMDirectory();

        //색인을 하고 색인 시간을 기록한다
        IndexService indexService = new IndexService();

        IndexOrderSortExample indexOrderSortExample = new IndexOrderSortExample();
        indexOrderSortExample.indexTourInfoWithTime(index, tourInfoList);

        //검색을 한다
        int maxHitCount = 10;
        TermQuery termQuery = new TermQuery(new Term("description","섬진강"));

        //Sort 순으로 정렬한다
        indexOrderSortExample.getQueryResult(index, maxHitCount, termQuery);
    }

    //색인을 하고 색인 시간을 기록한다
    private void indexTourInfoWithTime(Directory index, List<TourInfo> tourInfoList) throws Exception {
        //분석기를 설정을 한다.
        CustomKoreanAnalyzer analyzer = new CustomKoreanAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try(IndexWriter w = new IndexWriter(index, config)) {
            tourInfoList.stream().forEach( tourInfo -> {
                addDocument(tourInfo, w);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 도큐먼트를 추가한다
     * @param tourInfo
     * @param writer
     */
    private void addDocument(TourInfo tourInfo, IndexWriter writer) {
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
        doc.add(new StoredField("hour", hour));
        doc.add(new TextField("description", tourInfo.getDescription(), Field.Store.YES));

        //현재시간을 색인한다
        doc.add(new StoredField("date", System.currentTimeMillis()));

        try{
            writer.addDocument(doc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 결과를 가져온다
     * @param index
     * @param maxHitCount
     * @param query
     * @throws Exception
     */
    private void getQueryResult(Directory index, int maxHitCount, Query query) throws Exception{
        int hitsPerPage = maxHitCount;

        try(IndexReader reader = DirectoryReader.open(index)){
            IndexSearcher searcher = new IndexSearcher(reader);
            //관련도 기준으로 정렬한다
            TopDocs docs = searcher.search(query, hitsPerPage, Sort.INDEXORDER, true, false);
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
                System.out.println("[색인 시간] " + "\t" + d.get("date"));
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
