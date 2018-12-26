package io.javacafe.chap08;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class Example_009 {
    /**
     * Bulk API
     * */
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        //인덱스 명
        String INDEX_NAME = "movie_auto_java";
        //타입 명
        String TYPE_NAME="_doc";

        String FIELD_NAME = "movieNm";

        BulkRequest request = new BulkRequest();
        String _id = "4";
        request.add(new IndexRequest(INDEX_NAME, TYPE_NAME, _id).source(XContentType.JSON,FIELD_NAME, "살아남은 아이"));
         _id = "5";
        request.add(new IndexRequest(INDEX_NAME, TYPE_NAME, _id).source(XContentType.JSON,FIELD_NAME, "프렌즈: 몬스터섬의비밀"));
         _id = "6";
        request.add(new IndexRequest(INDEX_NAME, TYPE_NAME, _id).source(XContentType.JSON,FIELD_NAME, "캡틴아메리카 시빌워"));


        BulkResponse bulkResponse = client.bulk(request,RequestOptions.DEFAULT);
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();

            if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX
                    || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
                IndexResponse indexResponse = (IndexResponse) itemResponse;

            } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
                UpdateResponse updateResponse = (UpdateResponse) itemResponse;

            } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
                DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
            }
        }


    }
}
