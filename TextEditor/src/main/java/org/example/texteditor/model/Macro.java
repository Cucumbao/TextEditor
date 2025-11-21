package org.example.texteditor.model;

public class Macro {
    private Long id;
    private String name;
    private String commands;

    public Macro(Long id, String name, String commands) {
        this.id = id;
        this.name = name;
        this.commands = commands;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCommands() { return commands; }
    public void setCommand(String command) { this.commands = command; }

    @Override
    public String toString() {
        return "model.Macro{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", command='" + commands + '\'' +
                '}';
    }
}
