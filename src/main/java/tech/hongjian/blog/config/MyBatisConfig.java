package tech.hongjian.blog.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MyBatisConfig {

    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        DatabaseIdProvider provider = new VendorDatabaseIdProvider();
        Properties prop = new Properties();
        prop.setProperty("MySQL", "mysql");
        prop.setProperty("SQLite3", "sqlite3");
        provider.setProperties(prop);
        return provider;
    }
}
