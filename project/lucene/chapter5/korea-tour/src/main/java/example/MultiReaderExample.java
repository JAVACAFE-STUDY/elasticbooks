package example;

import helper.CsvLoader;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import vo.TourInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by hwang on 2018. 6. 4..
 */
public class MultiReaderExample {

    Directory index1;
    Directory index2;

    public static void main(String args[]) throws Exception{

        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();
        List<TourInfo> tour1 = tourInfoList.subList(0, 9);
        List<TourInfo> tour2 = tourInfoList.subList(10, 19);

        MultiReaderExample multiReaderExample = new MultiReaderExample();

        //다중 색인을 생성한다
        multiReaderExample.createIndex(tour1, tour2);

        //다중 색인 검색을 한다
        multiReaderExample.search();

    }

    /**
     * 다중 색인을 생성한다
     * @param tour1
     * @param tour2
     * @throws Exception
     */
    private void createIndex(List<TourInfo> tour1, List<TourInfo> tour2) throws Exception{
        // Directory를 생성한다.
        index1 = new RAMDirectory();
        index2 = new RAMDirectory();

        // 색인 생성을 위한 Writer를 구성한다
        IndexWriterConfig config1 = new IndexWriterConfig(new KoreanAnalyzer());
        IndexWriterConfig config2 = new IndexWriterConfig(new KoreanAnalyzer());
        IndexWriter w1 = new IndexWriter(index1, config1);
        IndexWriter w2 = new IndexWriter(index2, config2);

        IndexService indexService = new IndexService();
        // 도큐먼트를 추가한다.
        tour1.stream().forEach(tour->{
            indexService.addDocument(tour, w1);
        });

        tour2.stream().forEach(tour->{
            indexService.addDocument(tour, w2);
        });

        w1.close();
        w2.close();
    }

    /**
     * 다중색인을 검색한다
     * @throws IOException
     * @throws ParseException
     */
    private void search() throws IOException, ParseException{
        IndexReader reader1 = DirectoryReader.open(index1);
        IndexReader reader2 = DirectoryReader.open(index2);

        MultiReader mr = new MultiReader(reader1, reader2);

        IndexSearcher searcher = new IndexSearcher(mr);
        Query q = new QueryParser("description", new KoreanAnalyzer()).parse("도보");

        int hitsPerPage = 10;
        TopDocs docs = searcher.search(q, hitsPerPage);
        printResult(docs, searcher);
    }

    /**
     * 검색 결과를 출력한다
    * @param docs
     * @param searcher
     * @throws IOException
     */
    private static void printResult(TopDocs docs, IndexSearcher searcher) throws IOException{
        ScoreDoc[] hits = docs.scoreDocs;

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
    }


}
