package ru.jungle.creator.tables.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jungle.creator.tables.dto.request.TableRequest;
import ru.jungle.creator.tables.repository.TableRepository;
import ru.jungle.creator.tables.service.exception.ValidationException;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TableService {

    private static final String AUTO_INCREMENT_PK = " serial PRIMARY KEY";
    private static final String NOT_NULL = " NOT NULL";
    private static final String REFERENCES_TABLE = " REFERENCES %s(%s)";

    @Autowired
    private TableRepository tableRepository;

    public void create(TableRequest tableRequest) {
        String tableName = tableRequest.getTableName();
        String columns = tableRequest.getColumns()
                .stream()
                .map(this::getColumnDefinition)
                .collect(Collectors.joining(", "));
        tableRepository.createTable(tableName, columns);
    }

    private String getColumnDefinition(TableRequest.ColumnNameType column) {
        String columnName = column.getColumnName();

        if (isPrimaryKey(columnName)) {
            return columnName.concat(AUTO_INCREMENT_PK);
        } else {
            String columnDefinition = isNull(columnName, column);
            return isForeignKey(column, columnDefinition);
        }
    }

    private boolean isPrimaryKey(String columnName) {
        return "id".equalsIgnoreCase(columnName);
    }

    private String isNull(String columnName, TableRequest.ColumnNameType column) {
        if (!column.getNullable()) {
            return columnName.concat(" ").concat(column.getColumnType()).concat(NOT_NULL);
        }
        return columnName.concat(" ").concat(column.getColumnType());
    }

    private String isForeignKey(TableRequest.ColumnNameType column, String columnDefinition) {
        TableRequest.ColumnNameType.ReferenceTable referenceTable = column.getReferenceTable();
        if(!referenceTable.getTableName().isEmpty() || !referenceTable.getColumnName().isEmpty()) {
            return checkFieldsRefTable(columnDefinition, referenceTable);
        }
        return columnDefinition;
    }

    private static String checkFieldsRefTable(String expression, TableRequest.ColumnNameType.ReferenceTable referenceTable) {
        if (!referenceTable.getColumnName().contains("id")) {
            throw new ValidationException("columnName", "Название колонки должно содержать 'id'");
        }

        if (Objects.equals(referenceTable.getTableName(), "") && !Objects.equals(referenceTable.getColumnName(), "")) {
            throw new ValidationException("tableName", "Данное поле должно быть заполнено");
        } else if (!Objects.equals(referenceTable.getTableName(), "") && Objects.equals(referenceTable.getColumnName(), "")) {
            throw new ValidationException("columnName", "Данное поле должно быть заполнено");
        }
        return expression.concat(REFERENCES_TABLE.formatted(referenceTable.getTableName(), referenceTable.getColumnName()));
    }
}
