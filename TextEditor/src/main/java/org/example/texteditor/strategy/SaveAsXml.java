package org.example.texteditor.strategy;

import org.example.texteditor.model.File;

import java.io.FileWriter;
import java.io.IOException;

public class SaveAsXml implements SaveStrategy {
    @Override
    public void save(File file) {
        String xml = "<file>\n" +
                "  <id>" + file.getId() + "</id>\n" +
                "  <name>" + file.getFileName() + "</name>\n" +
                "  <userId>" + file.getUser() + "</userId>\n" +
                "  <lastUpdate>" + file.getLastUpdate() + "</lastUpdate>\n" +
                "  <content>" + file.getContent() + "</content>\n" +
                "</file>";

        try (FileWriter writer = new FileWriter(file.getFileName() + ".xml")) {
            writer.write(xml);
            System.out.println("Збережено як XML: " + file.getFileName() + ".xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}