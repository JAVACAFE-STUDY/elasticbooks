package io.javacafe.chap08;

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
        String INDEX_NAME="moive_auto_java";

//타입명
        String TYPE_NAME = "_doc";

//매핑정보 생성
        XContentBuilder indexBuilder = jsonBuilder().startObject().startObject(TYPE_NAME)
                .startObject("properties")
                .startObject("movieCd").field("type","keyword").field("store","true").field("index_options","docs").endObject()
                .startObject("movieNm").field("type","text").field("store","true").field("index_options","docs").endObject()
                .startObject("movieNmEn").field("type","text").field("store","true").field("index_options","docs").endObject()
                .endObject().endObject().endObject();

        XContentBuilder settingBuilder = jsonBuilder()
                .startObject()
                    .startObject("index")
                        .startObject("analysis")
                            .startObject("tokenizer")
                                .startObject("nori_user_dict_tokenizer")
                                    .field("type","nori_tokenizer")
                                    .field("decompound_mode","mixed")
                                    .field("user_dictionary","userdict_ko.txt")
                                .endObject()
                            .endObject()
                            .startObject("analyzer")
                                .startObject("nori_token_analyzer")
                                    .field("tokenizer","nori_user_dict_tokenizer")
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
