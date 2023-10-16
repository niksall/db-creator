package ru.jungle.creator.tables.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jungle.creator.tables.dto.request.TableRequest;
import ru.jungle.creator.tables.service.TableService;

@RestController
@RequestMapping("/api/table")
public class TableController {

    @Autowired
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping
    @Operation(summary = "Создать таблицу")
    public ResponseEntity<String> createTable(@Valid @RequestBody final TableRequest tableRequest){
        tableService.create(tableRequest);
        return new ResponseEntity<>("Параметры корректны", HttpStatus.OK);
    }
}
