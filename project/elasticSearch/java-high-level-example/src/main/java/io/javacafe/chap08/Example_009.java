package io.javacafe.chap11;

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

        String FIELD_NAME = "userName";

        BulkRequest request = new BulkRequest();
        String _id = "4";
        request.add(new IndexRequest(INDEX_NAME, TYPE_NAME, _id).source(XContentType.JSON,FIELD_NAME, "blaze"));
         _id = "5";
        request.add(new IndexRequest(INDEX_NAME, TYPE_NAME, _id).source(XContentType.JSON,FIELD_NAME, "nobaksan"));
         _id = "6";
        request.add(new IndexRequest(INDEX_NAME, TYPE_NAME, _id).source(XContentType.JSON,FIELD_NAME, "dokzon"));


        BulkResponse bulkResponse = client.bulk(request);
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
