package io.javacafe.chap11;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Example4 {

    /**
     * 하나의 문서 추가
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

        IndexResponse response = client.prepareIndex(INDEX_NAME, TYPE_NAME, _id)
                .setSource(jsonBuilder()
                        .startObject()
                        .field("tweetId", "977770960160895000")
                        .field("createdAt", "2018-03-25T04:55:37.000Z")
                        .field("tweetLang", "ko")
                        .field("text", "배고픈데 뭐해먹지")
                        .endObject())
                .get();




    }
}
