package org.example.texteditor.controllers;

import org.example.texteditor.model.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.texteditor.repo.FileRepository;

@Controller
public class FileController {

    private final FileRepository fileRepository;

    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping("/files")
    public String showFiles(Model model) {
        model.addAttribute("files", fileRepository.findAll());
        return "files";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
    @GetMapping("/files/{id}")
    public String viewFile(@PathVariable Long id, Model model) {
        File file = fileRepository.findById(id);
        if (file == null) {
            model.addAttribute("errorMessage", "Файл не знайдено!");
            return null;
        }
        model.addAttribute("file", file);
        return "file-view";
    }
    @GetMapping("/files/{id}/edit")
    public String editFile(@PathVariable Long id, Model model) {
        File file = fileRepository.findById(id);
        if (file == null) {
            model.addAttribute("errorMessage", "Файл не знайдено!");
            return null;
        }
        model.addAttribute("file", file);
        return "file-edit";
    }
    @PostMapping("/files/{id}/edit")
    public String updateFile(@PathVariable Long id, @RequestParam String content) {
        File file = fileRepository.findById(id);
        if (file != null) {
            file.setContent(content);
            fileRepository.save(file);
        }
        return "redirect:/files/" + id;
    }
    @PostMapping("/files/{id}/delete")
    public String deleteFile(@PathVariable Long id) {
        File file = fileRepository.findById(id);
        if (file != null) {
            fileRepository.delete(id);
        }
        return "redirect:/files";
    }
    @GetMapping("/files/new")
    public String newFileForm(Model model) {
        model.addAttribute("file", new File());
        return "files-new";
    }

    @PostMapping("/files/new")
    public String createFile(@ModelAttribute File file) {
        fileRepository.save(file);
        return "redirect:/files";
    }
}