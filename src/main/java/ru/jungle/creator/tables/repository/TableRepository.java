package ru.jungle.creator.tables.repository;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TableRepository {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (%s)";

    private final JdbcTemplate jdbcTemplate;

    public TableRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTable(String tableName, String columns) throws BadSqlGrammarException {
        jdbcTemplate.execute(CREATE_TABLE.formatted(tableName, columns));
    }
}
