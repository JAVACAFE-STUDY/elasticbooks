package io.javacafe.chap11;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public class Example_012 {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        String INDEX_NAME = "tweet";
        String FIELD_NAME = "userName";
        String QUERY_TEXT = "nobaksan";
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));

        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery(FIELD_NAME, QUERY_TEXT));
        searchRequest.source(searchSourceBuilder);

        searchRequest.scroll (scroll);

        SearchResponse searchResponse = client.search(searchRequest);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();

        }

    }
}
