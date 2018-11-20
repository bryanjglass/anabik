package anabik.modules.web.controllers;

import anabik.libraries.elasticsearch.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class QueryController {
    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/api/query/{type}")
    public List<Object> search(@PathVariable String type, @RequestParam String query) throws IOException {
        return queryService.query(type, query);
    }

    @GetMapping("/api/aggregate/{type}")
    public List<?> aggregate(@PathVariable String type, @RequestParam String query, @RequestParam String[] dimensions) throws IOException {
        return queryService.aggregate(type, query, dimensions);
    }
}
