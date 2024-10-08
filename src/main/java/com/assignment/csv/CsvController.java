package com.assignment.csv;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.api.CsvApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CsvController implements CsvApi
{

    private final CsvService csvService;

    @Override
    public ResponseEntity<Void> uploadCsvFile(final MultipartFile file)
    {
        csvService.saveCsvFile(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
