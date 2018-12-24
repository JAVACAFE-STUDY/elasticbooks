package io.javacafe.chap08;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

public class Example20 {

    /**
     * SUB Aggregation 사용하기
     * */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        String INDEX_NAME = "movie_search";
        String TYPE_NAME = "_doc";
        String FIELD_NAME = "repNationNm";
        String QUERY = "한국";

        String AGGREGATION_NAME_FOR_USER="repNationNm";
        String AGGREGATION_FIELD_FOR_USER = "repNationNm";

        String AGGREGATION_NAME_FOR_FOLLOWER = "typeNm";
        String AGGREGATION_FIELD_FOR_FOLLOWER = "typeNm";
        AggregationBuilder aggs = AggregationBuilders.terms(AGGREGATION_NAME_FOR_USER)
                .field(AGGREGATION_FIELD_FOR_USER )
                .size(1000);

        StatsAggregationBuilder statsData =
                AggregationBuilders.stats(AGGREGATION_NAME_FOR_FOLLOWER)
                        .field(AGGREGATION_FIELD_FOR_FOLLOWER );

        aggs.subAggregation(statsData);

        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(FIELD_NAME, QUERY))
                .addAggregation(aggs)
                .setSize(0)
                .get();

        Terms termBucket= response.getAggregations().get(AGGREGATION_NAME_FOR_USER);
        //결과 출력
        for(Terms.Bucket bucket: termBucket.getBuckets()) {
            String tweetLang = bucket.getKeyAsString();
            Long docCount = bucket.getDocCount();

            Stats agg = bucket.getAggregations().get(AGGREGATION_NAME_FOR_FOLLOWER);
            double min = agg.getMin();
            double max = agg.getMax();
            double avg = agg.getAvg();
            double sum = agg.getSum();
            long count = agg.getCount();
        }







    }
}
