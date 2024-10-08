package com.assignment.csv;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.api.CsvLinesApi;
import com.assignment.api.model.CsvLine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CsvLinesController implements CsvLinesApi
{
    private final CsvService csvService;

    @Override
    public ResponseEntity<Void> deleteCsvLines()
    {
        log.info("Deleting all lines...");
        csvService.deleteCsvLines();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CsvLine> fetchCsvLine(final String code)
    {
        log.info("Fetching csv line with code:{}", code);
        final Optional<CsvLine> csvLine = csvService.fetchCsvLine(code);
        return csvLine.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<CsvLine>> fetchCsvLines()
    {
        log.info("Fetching all lines...");
        final List<CsvLine> csvLineList = csvService.fetchAllData();
        return ResponseEntity.ok(csvLineList);
    }
}
