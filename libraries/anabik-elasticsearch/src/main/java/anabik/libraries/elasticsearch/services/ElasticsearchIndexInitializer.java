package anabik.libraries.elasticsearch.services;

import anabik.core.datatypes.DataType;
import anabik.core.datatypes.DataTypeRepository;
import anabik.core.datatypes.FieldDefinition;
import com.google.common.collect.ImmutableMap;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchIndexInitializer {
    private final Logger logger = LoggerFactory.getLogger(ElasticsearchIndexInitializer.class);
    private final RestHighLevelClient restHighLevelClient;
    private final DataTypeRepository dataTypeRepository;

    @Autowired
    public ElasticsearchIndexInitializer(RestHighLevelClient restHighLevelClient, DataTypeRepository dataTypeRepository) {
        this.restHighLevelClient = restHighLevelClient;
        this.dataTypeRepository = dataTypeRepository;
    }

    @PostConstruct
    public void init() throws IOException {
        List<DataType> dataTypes = dataTypeRepository.findAllDataTypes();

        logger.info("Initializing index templates for data {} types", dataTypes.size());

        for (DataType dataType : dataTypes) {
            initializeDataTypeIndex(dataType);
        }
    }

    public void initializeDataTypeIndex(DataType dataType) throws IOException {
        logger.info("Building index template for DataType [{}]", dataType.getName());

        PutIndexTemplateRequest putIndexTemplateRequest = new PutIndexTemplateRequest();
        putIndexTemplateRequest.name(dataType.getName())
                .alias(new Alias(dataType.getName()))
                .patterns(Arrays.asList(dataType.getName() + "-*"));

        Map<String, Object> properties = new HashMap<>();
        for (FieldDefinition field : dataType.getFields()) {
            properties.put(field.getName(), ImmutableMap.of("type", field.getType()));
        }
        putIndexTemplateRequest.mapping("doc", ImmutableMap.of("properties", properties));


        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().putTemplate(putIndexTemplateRequest, RequestOptions.DEFAULT);
        if (!acknowledgedResponse.isAcknowledged()) {
            throw new IOException(acknowledgedResponse.toString());
        }

        logger.info("Building index template for DataType [{}] successful.", dataType.getName());
    }
}
