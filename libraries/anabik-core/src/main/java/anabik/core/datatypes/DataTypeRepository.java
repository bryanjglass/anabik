package anabik.core.datatypes;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataTypeRepository {

    public List<DataType> findAllDataTypes() {
        List<DataType> dataTypes = new ArrayList<>();
        dataTypes.add(new DataTypeBuilder().withName("test")
                .withField("time", "date")
                .withField("foo", "keyword")
                .withField("bar", "keyword")
                .withField("amount", "integer")
                .build()
        );

        return dataTypes;
    }
}
