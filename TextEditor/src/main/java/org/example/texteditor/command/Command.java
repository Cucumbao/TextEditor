package org.example.texteditor.command;

public interface Command {
    void execute();
    void undo();
}
