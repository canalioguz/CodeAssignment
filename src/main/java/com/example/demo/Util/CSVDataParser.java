package com.example.demo.util;

import com.example.demo.entity.CSVData;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVDataParser {

    public List<CSVData> parse(MultipartFile file) throws Exception {
        List<CSVData> csvDataList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = csvReader.readAll();

            if (rows.isEmpty()) {
                throw new IllegalArgumentException("CSV file is empty.");
            }

            rows.remove(0); 

            for (String[] fields : rows) {
                CSVData csvData = new CSVData();
                csvData.setSource(fields.length > 0 ? fields[0].replace("\"", "").trim() : null);
                csvData.setCodeListCode(fields.length > 1 ? fields[1].replace("\"", "").trim() : null);
                csvData.setCode(fields.length > 2 ? fields[2].replace("\"", "").trim() : null);
                csvData.setDisplayValue(fields.length > 3 ? fields[3].replace("\"", "").trim() : null);
                csvData.setLongDescription(fields.length > 4 ? fields[4].replace("\"", "").trim() : null);
                csvData.setFromDate(fields.length > 5 ? fields[5].replace("\"", "").trim() : null);
                csvData.setToDate(fields.length > 6 ? fields[6].replace("\"", "").trim() : null);
                csvData.setSortingPriority(fields.length > 7 && !fields[7].isEmpty()
                        ? Integer.parseInt(fields[7].replace("\"", "").trim())
                        : null);
                csvDataList.add(csvData);
            }
        }

        return csvDataList;
    }
}
