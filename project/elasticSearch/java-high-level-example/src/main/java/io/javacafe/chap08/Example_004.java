package io.javacafe.chap11;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Example_004 {
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

        IndexRequest request = new IndexRequest(INDEX_NAME,TYPE_NAME,_id);
        //인덱스 데이터 추가
        request.source(jsonBuilder()
                .startObject()
                .field("tweetId", 97777096016089500l)
                .field("createdAt", "2018-03-25T04:55:37.000Z")
                .field("tweetLang", "ko")
                .field("userFollowersCount", 1000)
                .field("text", "배고픈데 뭐해먹지")
                .endObject()
        );

        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        } catch(ElasticsearchException e) {
            e.printStackTrace();
            if (e.status() == RestStatus.CONFLICT) {

            }
        }

    }
}
