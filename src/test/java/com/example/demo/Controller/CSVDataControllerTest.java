package com.example.demo.controller;

import com.example.demo.entity.CSVData;
import com.example.demo.service.CSVDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CSVDataController.class)
class CSVDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CSVDataService service;

    @Test
    void testUploadData() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "content".getBytes());

        mockMvc.perform(multipart("/api/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded and processed successfully."));

        verify(service, times(1)).uploadData(file);
    }

    @Test
    void testFetchAll() throws Exception {
        CSVData csvData = new CSVData();
        csvData.setCode("271636001");

        when(service.fetchAll()).thenReturn(List.of(csvData));

        mockMvc.perform(get("/api/get").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("271636001"));
    }

    @Test
    void testFetchByCode() throws Exception {
        CSVData csvData = new CSVData();
        csvData.setCode("271636001");

        when(service.fetchByCode("271636001")).thenReturn(csvData);

        mockMvc.perform(get("/api/get/271636001").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("271636001"));
    }

    @Test
    void testFetchByCode_NotFound() throws Exception {
        when(service.fetchByCode("999")).thenThrow(new IllegalArgumentException("Record not found for code: 999"));

        mockMvc.perform(get("/api/get/999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAll() throws Exception {
        mockMvc.perform(delete("/api/delete"))
                .andExpect(status().isOk())
                .andExpect(content().string("All records deleted."));

        verify(service, times(1)).deleteAll();
    }
}
