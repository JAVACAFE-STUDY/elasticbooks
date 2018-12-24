package io.javacafe.chap08;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Example8 {

    /**
     * 문서 수정
     * */

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        //Index명
        String INDEX_NAME="movie_auto_java";

        //타입명
        String TYPE_NAME = "_doc";

        String _id = "20174244";
        UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id)
                .doc(jsonBuilder()
                        .startObject()
                        .field("movieNm", "수정 문서")
                        .endObject());

        client.update(updateRequest).get();


    }
}
