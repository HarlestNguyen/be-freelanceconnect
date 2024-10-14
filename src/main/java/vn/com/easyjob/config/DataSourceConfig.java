package vn.com.easyjob.config;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //run before spring removes bean
    @PreDestroy
    public void cleanup() {
//        dropDatabaseIfExists();
    }

    //query drop database after application terminate
    private void dropDatabaseIfExists() {
        try {
            String sql = "DROP DATABASE IF EXISTS " +
                    getCurrentDatabaseName();
            System.out.println("Database dropped if it existed.");
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            System.err.println("An error occurred while dropping the database.");
            e.printStackTrace();
        }
    }

    public String getCurrentDatabaseName() {
        return jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
    }
}
