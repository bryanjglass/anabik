package anabik.modules.web.controllers;

import anabik.core.datatypes.DataType;
import anabik.core.datatypes.DataTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataTypeController {

    private final DataTypeRepository dataTypeRepository;

    @Autowired
    public DataTypeController(DataTypeRepository dataTypeRepository) {
        this.dataTypeRepository = dataTypeRepository;
    }

    @GetMapping("/api/data-type")
    public List<DataType> list() {
        return dataTypeRepository.findAllDataTypes();
    }

    @GetMapping("/api/data-type/{type}")
    public DataType getByType(@PathVariable String type) throws Exception {
        // TODO Return 404 instead of throwing an exception
        return dataTypeRepository.findAllDataTypes().stream()
                .filter(item -> item.getName().equalsIgnoreCase(type))
                .findAny()
                .orElseThrow(Exception::new);
    }
}
