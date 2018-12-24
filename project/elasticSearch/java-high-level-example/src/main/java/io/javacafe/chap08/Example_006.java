package io.javacafe.chap11;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Map;

public class Example_006 {
    /**
     * 인덱스 오픈 및 종료
     * */
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        //인덱스 명
        String INDEX_NAME = "tweet";
        //타입 명
        String TYPE_NAME="_doc";
        //문서 키값
        String _id = "1";

        GetRequest getRequest = new GetRequest( INDEX_NAME, TYPE_NAME, _id);
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);

    }
}
