package io.javacafe.chap11;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public class Example_013 {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        String INDEX_NAME = "tweet";
        String FIELD_NAME = "userName";
        String QUERY_TEXT = "nobaksan";


        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery(FIELD_NAME, QUERY_TEXT));
        searchRequest.source(searchSourceBuilder);

        searchRequest.scroll (TimeValue.timeValueMinutes (1L));

        SearchResponse searchResponse = client.search(searchRequest);
        String scrollId = searchResponse.getScrollId();

        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId(scrollId);
        //scroll ID가 여러개일 경우는 setScrollIds를 사용하여 조회 가능
//        request.setScrollIds(scrollIds);
        ClearScrollResponse response = client.clearScroll(request, RequestOptions.DEFAULT);

        //해당 scrollId가 정상적으로 release 되었는지 확인 가능
        boolean success = response.isSucceeded();
        int released = response.getNumFreed();

    }
}
