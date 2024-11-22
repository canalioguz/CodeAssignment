package com.example.demo.repository;

import com.example.demo.entity.CSVData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CSVDataRepository extends JpaRepository<CSVData, String> {
}
