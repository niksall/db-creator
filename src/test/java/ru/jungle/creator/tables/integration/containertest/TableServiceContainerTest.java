package ru.jungle.creator.tables.integration.containertest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jungle.creator.tables.service.TableService;
import ru.jungle.creator.tables.dto.response.ResultColumns;
import ru.jungle.creator.tables.dto.response.ResultTableColumns;
import ru.jungle.creator.tables.integration.IntegrationTestBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableServiceContainerTest extends IntegrationTestBase {

    private static final String TABLE_NAME = "customers";

    private static final List<ResultColumns> RESULT_COLUMNS = List.of(
            new ResultColumns("customers", "id", "integer"),
            new ResultColumns("customers", "username", "character varying")
    );
    private static final Map<String, List<ResultColumns>> EXPECT_RESULT = new HashMap<>();

    @Autowired
    private TableService tableService;

    @Test
    public void getCreatedTable() {
        EXPECT_RESULT.put(TABLE_NAME, RESULT_COLUMNS);

       Map<String, List<ResultColumns>>  actualTable = tableService.getCreatedTable(TABLE_NAME);

       Assertions.assertFalse(actualTable.isEmpty());
       Assertions.assertEquals(EXPECT_RESULT, actualTable);
    }
}
