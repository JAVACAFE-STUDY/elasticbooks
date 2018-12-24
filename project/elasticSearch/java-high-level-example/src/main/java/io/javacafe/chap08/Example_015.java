package io.javacafe.chap11;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Example_015 {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        String INDEX_NAME = "tweet";
        String TYPE_NAME = "_doc";
        String FIELD_NAME = "userName";
        String QUERY_TEXT = "nobaksan";
        String QUERY_TEXT2 = "dokzon";

        MultiSearchRequest request = new MultiSearchRequest();

        SearchRequest firstSearchRequest = new SearchRequest(INDEX_NAME);
        firstSearchRequest.types(TYPE_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(FIELD_NAME, QUERY_TEXT));
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);

        SearchRequest secondSearchRequest = new SearchRequest(INDEX_NAME);
        secondSearchRequest.types(TYPE_NAME);
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(FIELD_NAME, QUERY_TEXT2));
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);

        MultiSearchResponse multiSearchResponse = client.msearch(request, RequestOptions.DEFAULT);

        List<SearchResponse> searchResponses = new ArrayList<>();
        for(MultiSearchResponse.Item multiSearchResponseItem : multiSearchResponse.getResponses()){
            searchResponses.add(multiSearchResponseItem.getResponse());
        }

    }
}
