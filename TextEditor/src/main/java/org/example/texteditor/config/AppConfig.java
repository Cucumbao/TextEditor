package org.example.texteditor.config;

import org.example.texteditor.db.DatabaseConnection;
import org.example.texteditor.repo.FileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DatabaseConnection databaseConnection() {
        return new DatabaseConnection(); // або як ти створюєш з параметрами
    }

    @Bean
    public FileRepository fileRepository(DatabaseConnection databaseConnection) {
        return new FileRepository(databaseConnection);
    }
}
