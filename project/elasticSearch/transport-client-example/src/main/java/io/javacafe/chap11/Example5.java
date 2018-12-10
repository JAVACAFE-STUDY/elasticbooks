package io.javacafe.chap11;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Example5 {

    /**
     * 대량의 문서 추가
     * */

    public static void main(String[] args) throws IOException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        //Index명
        String INDEX_NAME="tweet";

        //타입명
        String TYPE_NAME = "info";
        
        //문서 키값
        String _id = "1";

        //여러개의 데이터를 저장할 XContentBuilder의 리스트 오브젝트 생성
        List<XContentBuilder> xContentBuilders = new ArrayList<>();

        //1번 데이터 추가
        XContentBuilder builder = jsonBuilder().startObject()
                .field("tweetId", "977770960160895000")
                .field("createdAt", "2018-03-25T04:55:37.000Z")
                .field("tweetLang", "ko")
                .field("text", "배고픈데 뭐해먹지")
                .endObject();
        xContentBuilders.add(builder);

        //2번 데이터 추가
        builder = jsonBuilder().startObject()
                .field("tweetId", "977770968532664320")
                .field("createdAt", "2018-03-25T04:55:39.000Z")
                .field("tweetLang", "ko")
                .field("text", "이정도면 만점")
                .endObject();
        xContentBuilders.add(builder);

        //BulkRequestBuilder 객체 생성
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for(XContentBuilder xContentBuilder: xContentBuilders){
            bulkRequest.add(client.prepareIndex(INDEX_NAME,TYPE_NAME)
                    .setSource(xContentBuilder));
        }

        //문서 전송
        BulkResponse bulkResponse = bulkRequest.get();




    }
}
