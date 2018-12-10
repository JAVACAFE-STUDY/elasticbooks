package io.javacafe.chap11;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 인덱스 생성
 * */
public class Example2 {
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

//매핑정보 생성
        XContentBuilder indexBuilder = jsonBuilder().startObject().startObject(TYPE_NAME)
                .startObject("properties")
                .startObject("createdAt").field("type","date").field("store","true").field("index_options","docs").endObject()
                .startObject("latitude").field("type","float").field("store","true").field("index_options","docs").endObject()
                .startObject("longitude").field("type","float").field("store","true").field("index_options","docs").endObject()
                .startObject("tweetFavouritesCount").field("type","long").field("store","true").field("index_options","docs").endObject()
                .startObject("tweetId").field("type","long").field("store","true").field("index_options","docs").endObject()
                .startObject("tweetLang").field("type","text").field("store","true").field("index_options","docs").endObject()
                .startObject("userFollowersCount").field("type","long").field("store","true").field("index_options","docs").endObject()
                .startObject("userFriendsCount").field("type","long").field("store","true").field("index_options","docs").endObject()
                .startObject("userLocation").field("type","text").field("store","true").field("index_options","docs").endObject()
                .startObject("userName").field("type","keyword").field("store","true").field("index_options","docs").endObject()
                .startObject("userScreenName").field("type","text").field("store","true").field("index_options","docs").endObject()
                .startObject("text").field("type","text").field("store","true").field("index_options","docs").endObject()
                .startObject("language").field("type","keyword").field("store","true").field("index_options","docs").endObject()

                .endObject().endObject().endObject();

        client.admin().indices().prepareCreate(INDEX_NAME)
                .setSettings(Settings.builder()
                        .put("index.number_of_shards",3)
                        .put("index.number_of_replicas",1)
                ).addMapping(TYPE_NAME, indexBuilder)
                .get();


    }
}
