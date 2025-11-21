package org.example.texteditor.command;
import org.example.texteditor.model.File;
import org.example.texteditor.repo.FileRepository;
import org.example.texteditor.strategy.SaveStrategy;

public class SaveFileCommand implements Command {
    private FileRepository receiver;
    private File file;
    private SaveStrategy strategy;

    public SaveFileCommand(FileRepository receiver, File file, SaveStrategy strategy) {
        this.receiver = receiver;
        this.file = file;
        this.strategy = strategy;
    }

    @Override
    public void execute() {
        strategy.save(file);
        receiver.save(file);
        System.out.println("File saved: " + file.getFileName());
    }

    @Override
    public void undo() {
        receiver.delete(file.getId());
        System.out.println("Undo save: " + file.getFileName());
    }
}
