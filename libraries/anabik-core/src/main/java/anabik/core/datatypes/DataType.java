package anabik.core.datatypes;

import java.util.List;

public class DataType {
    private final String name;
    private final List<FieldDefinition> fields;

    public DataType(String name, List<FieldDefinition> fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }
}
