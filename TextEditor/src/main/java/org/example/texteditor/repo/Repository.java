package org.example.texteditor.repo;


import java.util.List;

public interface Repository<T> {
    T findById(Long id);
    List<T> findAll();
    void save(T entity);
    void delete(Long id);
}