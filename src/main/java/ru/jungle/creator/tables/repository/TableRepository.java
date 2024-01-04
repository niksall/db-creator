package ru.jungle.creator.tables.repository;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jungle.creator.tables.dto.response.ResultColumns;
import ru.jungle.creator.tables.dto.response.ResultTableColumns;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TableRepository {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (%s)";
    private static final String GET_INFORMATION_TABLE = "SELECT table_name\n" +
            "     , column_name\n" +
            "     , data_type\n" +
            "  FROM information_schema.columns\n" +
            " WHERE table_name = ?";

    private final JdbcTemplate jdbcTemplate;

    public TableRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTable(String tableName, String columns) throws BadSqlGrammarException {
        jdbcTemplate.execute(CREATE_TABLE.formatted(tableName, columns));
    }

    public Map<String, List<ResultColumns>> findTableByName(String tableName) throws BadSqlGrammarException {
        List<ResultTableColumns> query = jdbcTemplate.query(GET_INFORMATION_TABLE,
                (rs, rowNum) -> new ResultTableColumns(
                        rs.getString("table_name"),
                        rs.getString("column_name"),
                        rs.getString("data_type")
                ), tableName);

        return query
                .stream()
                .collect(Collectors.groupingBy(ResultTableColumns::tableName,
                                Collectors.mapping(r -> new ResultColumns(r.tableName(), r.columnName(), r.dataType()),
                                        Collectors.toList()
                                )
                        )
                );
    }
}
