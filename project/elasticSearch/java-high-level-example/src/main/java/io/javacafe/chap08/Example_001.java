package io.javacafe.chap08;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public class Example_001 {
    /**
     * 인덱스 생성 및 매핑생성
     * */
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        String INDEX_NAME = "movie_auto_java";
        String TYPE_NAME = "_doc";

        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);

        //매핑생성
        XContentBuilder indexBuilder = jsonBuilder().startObject().startObject(TYPE_NAME)
                .startObject("properties")
                .startObject("movieCd").field("type","keyword").field("store","true").field("index_options","docs").endObject()
                .startObject("movieNm").field("type","text").field("store","true").field("index_options","docs").endObject()
                .startObject("movieNmEn").field("type","text").field("store","true").field("index_options","docs").endObject()
                .endObject().endObject().endObject();

        request.mapping(TYPE_NAME,indexBuilder);

        String ALIAS_NAME = "moive_auto_alias";
        request.alias(new Alias(ALIAS_NAME));


        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();



    }
}
