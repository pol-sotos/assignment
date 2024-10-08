package com.assignment.csv;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.assignment.api.model.CsvLine;


public interface CsvService
{
    void saveCsvFile(final MultipartFile file);

    List<CsvLine> fetchAllData();

    void deleteCsvLines();

    Optional<CsvLine> fetchCsvLine(String code);
}
