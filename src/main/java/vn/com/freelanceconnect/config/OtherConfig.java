package vn.com.freelanceconnect.config;

import vn.com.freelanceconnect.util.DateTimeFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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
