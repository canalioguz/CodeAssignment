package com.example.demo.service;

import com.example.demo.entity.CSVData;
import com.example.demo.repository.CSVDataRepository;
import com.example.demo.util.CSVDataParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CSVDataService {

    private static final Logger logger = LoggerFactory.getLogger(CSVDataService.class);

    private final CSVDataRepository repository;
    private final CSVDataParser csvDataParser;

    @Autowired
    public CSVDataService(CSVDataRepository repository, CSVDataParser csvDataParser) {
        this.repository = repository;
        this.csvDataParser = csvDataParser;
    }

    public void uploadData(MultipartFile file) throws Exception {
        logger.info("Starting to process file: {}", file.getOriginalFilename());
        List<CSVData> csvDataList = csvDataParser.parse(file);
        repository.saveAll(csvDataList);
        logger.info("Successfully processed and saved {} records.", csvDataList.size());
    }

    public List<CSVData> fetchAll() {
        logger.info("Fetching all records from the database.");
        return repository.findAll();
    }

    public CSVData fetchByCode(String code) {
        logger.info("Fetching record with code: {}", code);
        return repository.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("Record not found for code: " + code));
    }

    public void deleteAll() {
        logger.info("Deleting all records from the database.");
        repository.deleteAll();
    }
}
