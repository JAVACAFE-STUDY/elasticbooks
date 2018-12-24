package io.javacafe.chap08;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.index.query.QueryBuilders.geoDistanceQuery;
import static org.elasticsearch.index.query.QueryBuilders.geoPolygonQuery;

/**
 * FUNCTION SCORE QUERY API
 * */
public class Example39 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));

        String INDEX_NAME = "movie_search_datatype";
        String FIELD_NAME = "filmLocation";

        List<GeoPoint> points = new ArrayList<GeoPoint>();
        points.add(new GeoPoint(40, -70));
        points.add(new GeoPoint(30, -80));
        points.add(new GeoPoint(20, -90));

        QueryBuilder queryBuilder =  geoPolygonQuery(FIELD_NAME, points);

        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setQuery(queryBuilder)
                .setSize(30).get();







    }
}
