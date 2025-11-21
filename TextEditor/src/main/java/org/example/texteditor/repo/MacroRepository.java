package org.example.texteditor.repo;
import org.example.texteditor.model.Macro;
import org.example.texteditor.model.Snippet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MacroRepository implements Repository<Macro> {
    private final String jsonFilePath;

    public MacroRepository(String jsonFilePath ) {
        this.jsonFilePath = jsonFilePath;
    }

    public List<Macro> getMacrosFromJson() {
        List<Macro> macros = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Macro macro = new Macro(
                        obj.getLong("id"),
                        obj.getString("name"),
                        obj.getString("content")
                );
                macros.add(macro);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return macros;
    }

    public static void main(String[] args) {
        MacroRepository Macrorepo = new MacroRepository("src/main/java/TestData/Macros.json");
        System.out.println("Всі файли:");
        Macrorepo.findAll();
        System.out.println("\nМакрос з id=2:");
        Macrorepo.findById(2L);
    }


    @Override
    public Macro findById(Long id) {
        List<Macro> macros = getMacrosFromJson();
        for (Macro b : macros) {
            if (b.getId().equals(id)) {
                System.out.println(b);
            }
        }
        System.out.println("Macros with id=" + id + " not found.");
        return null;
    }

    @Override
    public List<Macro> findAll() {
        return getMacrosFromJson();
    }

    @Override
    public void save(Macro macro) {
    }

    @Override
    public void delete(Long id) {
    }
}
