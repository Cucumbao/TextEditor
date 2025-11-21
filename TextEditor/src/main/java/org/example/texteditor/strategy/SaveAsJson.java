package org.example.texteditor.strategy;

import org.example.texteditor.model.File;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class SaveAsJson implements SaveStrategy {
    @Override
    public void save(File file) {
        JSONObject obj = new JSONObject();
        obj.put("id", file.getId());
        obj.put("name", file.getFileName());
        obj.put("filecontent", file.getContent());
        obj.put("userid", file.getUser());
        obj.put("lastUpdate", file.getLastUpdate());

        try (FileWriter writer = new FileWriter(file.getFileName() + ".json")) {
            writer.write(obj.toString(4));
            System.out.println("Файл збережено як JSON: " + file.getFileName() + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
