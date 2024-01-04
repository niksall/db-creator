package ru.jungle.creator.tables.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jungle.creator.tables.dto.request.TableRequest;
import ru.jungle.creator.tables.dto.response.ResultColumns;
import ru.jungle.creator.tables.service.TableService;

import java.util.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createTable(@Valid @RequestBody final List<TableRequest> tableRequest){
        tableService.create(tableRequest);
        return new ResponseEntity<>("Параметры корректны", HttpStatus.OK);
    }

    @PostMapping("/desc")
    @Operation(summary = "Посмотреть структуру таблицы")
    public ResponseEntity<Map<String, List<ResultColumns>>> getTables(String tableName) {
        return new ResponseEntity<>(tableService.getCreatedTable(tableName), HttpStatus.OK);
    }
}
