package org.example.texteditor.strategy;

import org.example.texteditor.model.File;
import org.example.texteditor.repo.FileRepository;

public class SaveToDatabase implements SaveStrategy {
    private final FileRepository repository;

    public SaveToDatabase(FileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(File file) {
        repository.save(file);
        System.out.println("✅ Файл '" + file.getFileName() + "' збережено у базі даних.");
    }
}