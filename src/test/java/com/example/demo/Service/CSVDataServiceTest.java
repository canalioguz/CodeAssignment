package com.example.demo.service;

import com.example.demo.entity.CSVData;
import com.example.demo.repository.CSVDataRepository;
import com.example.demo.util.CSVDataParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CSVDataServiceTest {

    private final CSVDataRepository mockRepository = mock(CSVDataRepository.class);
    private final CSVDataParser mockParser = mock(CSVDataParser.class);
    private final CSVDataService service = new CSVDataService(mockRepository, mockParser);

    @Test
    void testUploadData() throws Exception {
        String csvContent = "\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"\n" +
        "\"ZIB\",\"ZIB001\",\"271636001\",\"Polsslag regelmatig\",\"Description\",\"01-01-2019\",\"\",\"1\"\n";

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        CSVData csvData = new CSVData();
        csvData.setCode("271636001");

        when(mockParser.parse(file)).thenReturn(List.of(csvData));

        service.uploadData(file);

        verify(mockParser, times(1)).parse(file);
        verify(mockRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testFetchAll() {
        CSVData csvData = new CSVData();
        csvData.setCode("271636001");

        when(mockRepository.findAll()).thenReturn(List.of(csvData));

        List<CSVData> result = service.fetchAll();

        assertEquals(1, result.size());
        assertEquals("271636001", result.get(0).getCode());
    }

    @Test
    void testFetchByCode() {
        CSVData csvData = new CSVData();
        csvData.setCode("271636001");

        when(mockRepository.findById("271636001")).thenReturn(Optional.of(csvData));

        CSVData result = service.fetchByCode("271636001");

        assertNotNull(result);
        assertEquals("271636001", result.getCode());
    }

    @Test
    void testFetchByCode_NotFound() {
        when(mockRepository.findById("999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.fetchByCode("999"));

        assertEquals("Record not found for code: 999", exception.getMessage());
    }

    @Test
    void testDeleteAll() {
        service.deleteAll();
        verify(mockRepository, times(1)).deleteAll();
    }
}
