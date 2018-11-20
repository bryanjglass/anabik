package anabik.core.datatypes;

public class FieldDefinition {
    private final String name;
    private final String type;

    public FieldDefinition(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
