package io.javacafe.chap11;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class Example_007 {
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

        DeleteRequest request = new DeleteRequest(INDEX_NAME, TYPE_NAME, _id);
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);

    }
}
