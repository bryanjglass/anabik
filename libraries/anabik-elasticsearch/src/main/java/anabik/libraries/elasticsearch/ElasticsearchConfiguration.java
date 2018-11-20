package anabik.libraries.elasticsearch;

import anabik.core.CoreConfiguration;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
@ComponentScan
@Import(CoreConfiguration.class)
public class ElasticsearchConfiguration {

    private final ElasticsearchProperties elasticsearchProperties;

    @Autowired
    public ElasticsearchConfiguration(ElasticsearchProperties elasticsearchProperties) {
        this.elasticsearchProperties = elasticsearchProperties;
    }

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(HttpHost.create(elasticsearchProperties.getHost())));
    }

    public ElasticsearchProperties getElasticsearchProperties() {
        return elasticsearchProperties;
    }
}
