package com.example.demo.controller;

import com.example.demo.entity.CSVData;
import com.example.demo.service.CSVDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CSVDataController {

    private final CSVDataService service;

    @Autowired
    public CSVDataController(CSVDataService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) {
        try {
            service.uploadData(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/get")
    public List<CSVData> fetchAll() {
        return service.fetchAll();
    }

    @GetMapping("/get/{code}")
    public ResponseEntity<CSVData> fetchByCode(@PathVariable String code) {
        try {
            return ResponseEntity.ok(service.fetchByCode(code));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok("All records deleted.");
    }
}
