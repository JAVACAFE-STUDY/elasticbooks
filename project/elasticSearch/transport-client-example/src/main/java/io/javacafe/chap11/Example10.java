package io.javacafe.chap11;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Example10 {

    /**
     * BulkProcessor 이용하여 문서 추가하기
     * */


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        //Index명
        String INDEX_NAME="tweet";

        //타입명
        String TYPE_NAME = "info";

        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
                    public void beforeBulk(long executionId,
                                           BulkRequest request) {  }

                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          BulkResponse response) { }

                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          Throwable failure) {  }
                })
                .setBulkActions(1000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setConcurrentRequests(1)
                .build();

//벌크 데이터 추가
        bulkProcessor.add(new IndexRequest(INDEX_NAME, TYPE_NAME, "1")
                .source(jsonBuilder()
                        .startObject()
                        .field("tweetId", "97777096016089500")
                        .field("createdAt", "2018-03-25T04:55:37.000Z")
                        .field("tweetLang", "ko")
                        .field("text", "배고픈데 뭐해먹지")
                        .endObject()));

        bulkProcessor.add(new IndexRequest(INDEX_NAME, TYPE_NAME, "2")
                .source(jsonBuilder()
                        .startObject()
                        .field("tweetId", "977770968532664320")
                        .field("createdAt", "2018-03-25T04:55:39.000Z")
                        .field("tweetLang", "ko")
                        .field("text", "이정도면 만점")
                        .endObject()));


    }
}
