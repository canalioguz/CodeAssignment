package com.example.demo.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileParser<T> {
    List<T> parse(MultipartFile file) throws Exception;
}