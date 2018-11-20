package anabik.core.datatypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataTypeService {

    private final DataTypeRepository dataTypeRepository;

    @Autowired
    public DataTypeService(DataTypeRepository dataTypeRepository) {
        this.dataTypeRepository = dataTypeRepository;
    }

    public DataType getDataTypeByName(String dataTypeName) {
        return dataTypeRepository.findAllDataTypes().stream()
                .filter(dataType -> dataType.getName().equalsIgnoreCase(dataTypeName))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public FieldDefinition getFieldDefinition(String dataTypeName, String fieldName) {
        return getDataTypeByName(dataTypeName).getFields().stream()
                .filter(fieldDefinition -> fieldDefinition.getName().equalsIgnoreCase(fieldName))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
