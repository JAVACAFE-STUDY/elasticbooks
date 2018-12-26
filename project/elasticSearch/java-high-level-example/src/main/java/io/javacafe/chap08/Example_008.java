package io.javacafe.chap08;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Example_008 {
    /**
     * UPSERT API
     * */
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        //인덱스 명
        String INDEX_NAME = "movie_auto_java";
        //타입 명
        String TYPE_NAME="_doc";
        //문서 키값
        String _id = "1";

        UpdateRequest request = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id);

        Map<String, Object> parameters = singletonMap("count", 10);

        Script inline = new Script(ScriptType.INLINE, "painless", "ctx._source.prdtYear += params.count", parameters);
        request.script(inline);


        try {
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            e.printStackTrace();
            if (e.status() == RestStatus.NOT_FOUND) {

            }
        }

        XContentBuilder builder = jsonBuilder();
        builder.startObject();
        builder.field("createdAt", new Date());
        builder.field("prdtYear", "2019");
        builder.field("typeNm", "장편");
        builder.endObject();
        UpdateRequest newTypUpdateRequest = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id).doc(builder);

        try {
            UpdateResponse updateResponse = client.update(newTypUpdateRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {

            }
        }



        _id = "2";
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME, TYPE_NAME, _id)
                .source(jsonBuilder()
                        .startObject()
                        .field("movieCd", "20173732")
                        .field("movieNm", "살아남은 아이")
                        .field("movieNmEn", "Last Child")
                        .field("openDt", "")
                        .field("typeNm", "장편")
                        .endObject());

        XContentBuilder upsertBuilder = jsonBuilder();
        upsertBuilder.startObject();
        upsertBuilder.field("createdAt", new Date());
        upsertBuilder.endObject();

        UpdateRequest upsertRequest = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id).doc(upsertBuilder).upsert(indexRequest);

        try {
            UpdateResponse updateResponse = client.update(upsertRequest,RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {

            }
        }

    }
}
