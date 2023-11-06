import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.jungle.creator.tables.Application;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
public class CheckConnectionTest {

    private static final String CUSTOMER_TABLE_QUERY = "CREATE TABLE customers (id INTEGER PRIMARY KEY, name TEXT, age INTEGER)";
    private static final String CUSTOMER_ENTRY_QUERY = "INSERT INTO customers VALUES (73, 'Brian', 33)";
    private static final String QUERY_BY_PARAMETER = "SELECT * FROM customers WHERE name = ?";
    private static final String QUERY = "SELECT * FROM customers";

    @Autowired
    Connection driverManagerConf;

    @Before("init db")
    public void initDb() throws SQLException {
        executeStatement(CUSTOMER_TABLE_QUERY);
        executeStatement(CUSTOMER_ENTRY_QUERY);
    }

    @Test
    public void shouldGetJdbcConnection() throws SQLException {
        assertTrue(driverManagerConf.isValid(1));
        assertFalse(driverManagerConf.isClosed());
    }

    @Test
    public void shouldSelectData() throws SQLException {
        PreparedStatement statement = driverManagerConf.prepareStatement(QUERY_BY_PARAMETER);
        statement.setString(1, "Brian");
        boolean hasResult = statement.execute();
        assertTrue(hasResult);
        // Обработка результат
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        int age = resultSet.getInt("age");
        assertEquals(33, age);
    }

    @Test
    public void shouldInsertInResultSet() throws SQLException {
        Statement statement = driverManagerConf.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
        resultSet.moveToInsertRow();
        resultSet.updateLong("id", 3L);
        resultSet.updateString("name", "John");
        resultSet.updateInt("age", 18);
        resultSet.insertRow();
        resultSet.moveToCurrentRow();
    }

    private int executeStatement(String query) throws SQLException {
        Statement statement = driverManagerConf.createStatement();
        return statement.executeUpdate(query);
    }

    @After("connection close")
    public void close() throws SQLException {
        driverManagerConf.close();
    }
}
