package org.example.texteditor;

import org.example.texteditor.db.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.example.texteditor.repo.FileRepository;


@SpringBootApplication
public class TextEditorApplication {
    public static void main(String[] args) {
        SpringApplication.run(TextEditorApplication.class, args);
    }
}
