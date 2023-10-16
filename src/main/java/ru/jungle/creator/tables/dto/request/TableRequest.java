package ru.jungle.creator.tables.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class TableRequest {

    private static final String MESSAGE_ERROR = "Данное поле должно быть заполнено";

    @NotBlank(message = MESSAGE_ERROR)
    private String tableName;
    @Valid
    private List<ColumnNameType> columns;

    public TableRequest(String name,
                        List<ColumnNameType> columns) {
        this.tableName = name;
        this.columns = columns;
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<ColumnNameType> getColumns() {
        return columns;
    }

    public static class ColumnNameType {
        @NotBlank(message = MESSAGE_ERROR)
        private String columnName;
        @NotBlank(message = MESSAGE_ERROR)
        private String columnType;
        private Boolean nullable;
        private ReferenceTable referenceTable;

        public ColumnNameType(String columnName, String columnType, Boolean nullable, ReferenceTable referenceTable) {
            this.columnName = columnName;
            this.columnType = columnType;
            this.nullable = nullable;
            this.referenceTable = referenceTable;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getColumnType() {
            return columnType;
        }

        public Boolean getNullable() {
            return nullable;
        }

        public ReferenceTable getReferenceTable() {
            return referenceTable;
        }

        public static class ReferenceTable {
            private String tableName;
            private String columnName;

            public ReferenceTable(String name, String columnName) {
                this.tableName = name;
                this.columnName = columnName;
            }

            public String getTableName() {
                return tableName;
            }

            public String getColumnName() {
                return columnName;
            }
        }
    }
}
