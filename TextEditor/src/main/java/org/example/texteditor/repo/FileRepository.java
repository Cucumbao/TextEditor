package org.example.texteditor.repo;

import org.example.texteditor.db.DatabaseConnection;
import org.example.texteditor.model.File;
import org.example.texteditor.strategy.FileSaver;
import org.example.texteditor.strategy.SaveAsJson;
import org.example.texteditor.strategy.SaveAsTxt;
import org.example.texteditor.strategy.SaveAsXml;

import java.util.List;

public class FileRepository implements Repository<File> {
    private final DatabaseConnection database;

    public FileRepository(DatabaseConnection database) {
        this.database = database;
    }

    @Override
    public List<File> findAll() {
        List<File> files = database.getAllFiles();
        System.out.println(files);
        return files;
    }

    @Override
    public File findById(Long id) {
        File file = database.getFileById(id);
        if (file != null) {
            System.out.println(file);
            return file;
        } else {
            System.out.println("❌ Файл з id=" + id + " не знайдено.");
            return null;
        }
    }

    @Override
    public void save(File file) {
        database.saveFile(file);
        FileSaver saver = new FileSaver();
        String path = file.getFilePath().toLowerCase();
        if (path.endsWith("json")) {
            saver.setStrategy(new SaveAsJson());
        } else if (path.endsWith("xml")) {
            saver.setStrategy(new SaveAsXml());
        } else if (path.endsWith("txt")) {
            saver.setStrategy(new SaveAsTxt());
        }
        saver.save(file);
        System.out.println("💾 Файл збережено або оновлено.");
    }



    @Override
    public void delete(Long id) {
        boolean deleted = database.deleteFile(id);
        if (deleted) {
            System.out.println("🗑️ Файл з id=" + id + " видалено.");
        } else {
            System.out.println("❌ Файл з id=" + id + " не знайдено.");
        }
    }
}
