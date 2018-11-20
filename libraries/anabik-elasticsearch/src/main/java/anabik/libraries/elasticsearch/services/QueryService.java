package anabik.libraries.elasticsearch.services;

import anabik.core.datatypes.DataTypeService;
import anabik.core.datatypes.FieldDefinition;
import com.google.common.collect.ImmutableMap;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.composite.*;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QueryService {
    private final RestHighLevelClient restHighLevelClient;
    private final DataTypeService dataTypeService;

    @Autowired
    public QueryService(RestHighLevelClient restHighLevelClient, DataTypeService dataTypeService) {
        this.restHighLevelClient = restHighLevelClient;
        this.dataTypeService = dataTypeService;
    }

    public List<Object> query(String type, String query) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(type + "*");
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        return Stream.of(search.getHits().getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
    }

    public List<?> aggregate(String type, String query, String[] dimensions) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0); // only return the aggregations
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));

        List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();

        for (String dimension : dimensions) {
            FieldDefinition fieldDefinition = this.dataTypeService.getFieldDefinition(type, dimension);

            if (fieldDefinition.getType().equals("date")) {
                DateHistogramValuesSourceBuilder dateHistogramValuesSourceBuilder = new DateHistogramValuesSourceBuilder(dimension)
                        .field(dimension)
                        .dateHistogramInterval(DateHistogramInterval.DAY);
                sources.add(dateHistogramValuesSourceBuilder);
            } else {
                TermsValuesSourceBuilder termsValuesSourceBuilder = new TermsValuesSourceBuilder(dimension)
                        .field(dimension)
                        .missingBucket(true);
                sources.add(termsValuesSourceBuilder);
            }
        }

        CompositeAggregationBuilder compositeAggregationBuilder = new CompositeAggregationBuilder("aggregate", sources);

        searchSourceBuilder.aggregation(compositeAggregationBuilder);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(type + "*");
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        CompositeAggregation aggregation = search.getAggregations().get("aggregate");
        return aggregation.getBuckets()
                .stream()
                .map(bucket -> ImmutableMap.of("dimensions", bucket.getKey(), "count", bucket.getDocCount()))
                .collect(Collectors.toList());

    }
}
