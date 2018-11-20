package anabik.core.datatypes;

import java.util.ArrayList;
import java.util.List;

public class DataTypeBuilder {
    private String name;
    private List<FieldDefinition> fields = new ArrayList<>();

    public DataTypeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DataTypeBuilder withField(String name, String type) {
        this.fields.add(new FieldDefinition(name, type));
        return this;
    }

    public DataType build() {
        return new DataType(this.name, this.fields);
    }
}
