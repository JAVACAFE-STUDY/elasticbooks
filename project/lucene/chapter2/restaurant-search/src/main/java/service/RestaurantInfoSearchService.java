package service;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import util.PropertyLoader;

public class RestaurantInfoSearchService {
    // 검색한 색인 경로
    private String indexPath;

    // 클래스 생성 시 색인의 경로를 설정한다
    public RestaurantInfoSearchService() {
        indexPath = PropertyLoader.getInstance().getPropertyValue("INDEX_DIR_PATH");
    }

    // 음식점 정보를 검색하는 메소드
    public void search(String searchValue) throws IOException {
        // 색인을 읽을 IndexReader를 설정한다.
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
        // 읽은 색인을 검색하는 IndexSearcher를 생성한다.
        IndexSearcher searcher = new IndexSearcher(reader);

        // 음식점 명과 개요에서 특정 단어로 검색하기 위해 쿼리를 생성한다.
        TermQuery termQuery = new TermQuery(new Term("restaurantName", searchValue));
        TermQuery termQuery2 = new TermQuery(new Term("description", searchValue));

        // 검색어가 음식점 명이나 개요에 있을 경우 검색이 되도록 BooleanQuery 생성 및 설정
        BooleanQuery query = new BooleanQuery
                .Builder()
                .add(new BooleanClause(termQuery, BooleanClause.Occur.SHOULD))
                .add(new BooleanClause(termQuery2, BooleanClause.Occur.SHOULD))
                .build();

        // 검색 결과는 10건만 가져온다.
        int hitsPerPage = 10;

        // IndexSearch에 쿼리와 가져올 결과를 설정하고 검색한다.
        TopDocs docs = searcher.search(query, hitsPerPage);
        // 검색 결과는 ScoreDoc 배열로 가져올 수 있다.
        ScoreDoc[] hits = docs.scoreDocs;

        // 검색된 결과를 출력한다.
        System.out.println("총 " + hits.length + "개가 일치합니다");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " +"음식점명:"+ d.get("restaurantName")
                    + "\t카테고리1:" + d.get("category1")  + "\t카테고리2:" + d.get("category2") + "\t카테고리:" + d.get("category3")
                    + "\t지역:" + d.get("region")+ "\t도시:" + d.get("city") + "\t설명:" + d.get("description"));
        }

        // 검색이 완료됐으면 IndexReader를 닫는다(close)
        reader.close();

    }

}
