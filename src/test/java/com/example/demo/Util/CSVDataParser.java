package com.example.demo.util;

import com.example.demo.entity.CSVData;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVDataParserTest {

    private final CSVDataParser parser = new CSVDataParser();

    @Test
    void testParseValidFile() throws Exception {
        String csvContent = "\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"\n" +
        "\"ZIB\",\"ZIB001\",\"271636001\",\"Polsslag regelmatig\",\"Description\",\"01-01-2019\",\"\",\"1\"\n";

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        List<CSVData> csvDataList = parser.parse(file);

        assertEquals(1, csvDataList.size());
        assertEquals("271636001", csvDataList.get(0).getCode());
    }

    @Test
    void testParseEmptyFile() throws Exception {
        String csvContent = "\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"";

        MockMultipartFile file = new MockMultipartFile("file", "empty.csv", "text/csv", csvContent.getBytes());

        List<CSVData> csvDataList = parser.parse(file);

        assertTrue(csvDataList.isEmpty());
    }

}
