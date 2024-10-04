package vn.com.freelanceconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import vn.com.freelanceconnect.util.DateTimeFormat;

import javax.sql.DataSource;

@Configuration
public class OtherConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DateTimeFormat dateTimeFormat() {
        return new DateTimeFormat();
    }
}
