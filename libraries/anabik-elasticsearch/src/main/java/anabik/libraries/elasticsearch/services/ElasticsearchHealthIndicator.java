package anabik.libraries.elasticsearch.services;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticsearchHealthIndicator implements HealthIndicator {
    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public ElasticsearchHealthIndicator(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public Health health() {
        Health.Builder healthBuilder = Health.unknown();

        ClusterHealthResponse response;
        try {
            response = checkClusterHealth();
        } catch (IOException e) {
            return healthBuilder.down(e).build();
        }

        if(response.getStatus() == ClusterHealthStatus.GREEN) {
            healthBuilder.up();
        } else {
            healthBuilder.down();
        }

        healthBuilder.withDetail("nodes", response.getNumberOfNodes());
        healthBuilder.withDetail("active_shards", response.getActiveShards());
        healthBuilder.withDetail("relocating_shards", response.getRelocatingShards());
        healthBuilder.withDetail("unassigned_shards", response.getUnassignedShards());

        return healthBuilder.build();
    }

    private ClusterHealthResponse checkClusterHealth() throws IOException {
        ClusterHealthRequest request = new ClusterHealthRequest();

        return restHighLevelClient.cluster().health(request, RequestOptions.DEFAULT);
    }
}
