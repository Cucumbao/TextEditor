package org.example.texteditor.repo;

import org.example.texteditor.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {
    private final String jsonUserPath;


    public UserRepository(String jsonUserPath ) {
        this.jsonUserPath = jsonUserPath;
    }

    public List<User> getUserFromJson() {
        List<User> users = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(jsonUserPath)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj1 = jsonArray.getJSONObject(i);
                User user = new User(
                        obj1.getLong("id"),
                        obj1.getString("username"),
                        obj1.getString("password"),
                        obj1.getString("email")
                );
                users.add(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void main(String[] args) {
        UserRepository repo = new UserRepository("src/main/java/TestData/User.json");
        System.out.println("Всі користувачі:");
        repo.findAll();

        System.out.println("\nКористувач id=2:");
        repo.findById(2L);
    }
    @Override
    public User findById(Long id) {
        List<User> users = getUserFromJson();
        for (User b : users) {
            if (b.getUserid().equals(id)) {
                System.out.println(b);
            }
        }
        System.out.println("User with id=" + id + " not found.");
        return null;
    }
    @Override
    public List<User> findAll() {
        return getUserFromJson();
    }

    @Override
    public void save(User user) {
    }

    @Override
    public void delete(Long id) {
    }
}

