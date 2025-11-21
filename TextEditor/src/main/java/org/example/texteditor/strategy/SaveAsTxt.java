package org.example.texteditor.strategy;

import org.example.texteditor.model.File;

import java.io.FileWriter;
import java.io.IOException;

public class SaveAsTxt implements SaveStrategy {
    @Override
    public void save(File file) {
        try (FileWriter writer = new FileWriter(file.getFileName() + ".txt")) {
            writer.write(file.getContent());
            System.out.println("Збережено як TXT: " + file.getFileName() + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
