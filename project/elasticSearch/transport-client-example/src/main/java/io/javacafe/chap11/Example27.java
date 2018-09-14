package io.javacafe.chap11;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

public class Example27 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        String INDEX_NAME = "tweet";
        String TYPE_NAME = "info";
        String FIELD_NAME = "text";
        String QUERY = "만점";

        String AGGREGATION_NAME="follower_aggregation";
        String AGGREGATION_FIELD_FOR_USER = "userFollowersCount";

        AggregationBuilder aggs =
                AggregationBuilders
                        .range(AGGREGATION_NAME)
                        .field(AGGREGATION_FIELD_FOR_USER)
                        .addUnboundedTo(100.0f)
                        .addRange(100.0f, 200.0f)
                        .addUnboundedFrom(200.0f);

        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(FIELD_NAME, QUERY))
                .addAggregation(aggs)
                .setSize(0)
                .get();

        Range agg = response.getAggregations().get(AGGREGATION_NAME);

        for (Range.Bucket entry : agg.getBuckets()) {
            String key = entry.getKeyAsString();
            Number from = (Number) entry.getFrom();
            Number to = (Number) entry.getTo();
            long docCount = entry.getDocCount();
        }











    }
}
