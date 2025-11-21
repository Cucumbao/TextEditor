package org.example.texteditor.repo;
import org.example.texteditor.model.Snippet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SnippetRepository implements Repository<Snippet> {
    private final String jsonFilePath;

    public SnippetRepository(String jsonFilePath ) {
        this.jsonFilePath = jsonFilePath;
    }

    public List<Snippet> getSnippetFromJson() {
        List<Snippet> snippets = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Snippet snippet = new Snippet(
                        obj.getLong("id"),
                        obj.getString("name"),
                        obj.getString("content")
                );
                snippets.add(snippet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return snippets;
    }

    public static void main(String[] args) {
        SnippetRepository Snippetrepo = new SnippetRepository("src/main/java/TestData/Snippet.json");
        System.out.println("Всі файли:");
        Snippetrepo.findAll();
        System.out.println("\nСніппет з id=1:");
        Snippetrepo.findById(1L);
    }

    @Override
    public Snippet findById(Long id) {
        List<Snippet> snippets = getSnippetFromJson();
        for (Snippet b : snippets) {
            if (b.getId().equals(id)) {
                System.out.println(b);
            }
        }
        System.out.println("Macros with id=" + id + " not found.");
        return null;
    }

    @Override
    public List<Snippet> findAll() {
        return getSnippetFromJson();
    }

    @Override
    public void save(Snippet snippet) {
    }

    @Override
    public void delete(Long id) {
    }
}
