package io.javacafe.chap11;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Example3 {

    /**
     * 한글 형태소 분석기 추가
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

        XContentBuilder settingBuilder = jsonBuilder()
                .startObject()
                    .startObject("index")
                        .startObject("analysis")
                            .startObject("tokenizer")
                                .startObject("seunjeon_tok_ko")
                                    .field("type","seunjeon_tokenizer")
                                    .field("index_eojeol",false)
                                    .field("pos_tagging",false)
                                .endObject()
                            .endObject()
                            .startObject("analyzer")
                                .startObject("seunjeon_anal_ko")
                                    .field("tokenizer","seunjeon_tok_ko")
                                    .array("filter","lowercase")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();

        client.admin().indices().prepareCreate(INDEX_NAME)
                .addMapping(TYPE_NAME, indexBuilder)
                .setSource(settingBuilder)
                .get();



    }
}
