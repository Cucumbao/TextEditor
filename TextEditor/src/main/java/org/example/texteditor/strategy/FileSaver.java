package org.example.texteditor.strategy;
import org.example.texteditor.model.File;

public class FileSaver {
    private SaveStrategy strategy;

    public void setStrategy(SaveStrategy strategy) {
        this.strategy = strategy;
    }

    public void save(File file) {
        if (strategy == null) {
            throw new IllegalStateException("❌ Не вибрано стратегію збереження!");
        }
        strategy.save(file);
    }
}
