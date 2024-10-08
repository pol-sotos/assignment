package com.assignment.csv;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.api.model.CsvLine;
import com.assignment.metrics.CsvMetrics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class CsvServiceImpl implements CsvService
{

    private final CsvLineRepository csvLineRepository;
    private final CsvLineMapper csvLineMapper;
    private final CsvMetrics csvMetrics;

    @Override
    public void saveCsvFile(final MultipartFile file)
    {
        log.info("Saving CSV file...");
        final List<CsvLineEntity> csvLineEntityList = CsvUtils.csvToCsvLines(file);
        csvLineRepository.saveAll(csvLineEntityList);
        csvMetrics.incrementCsvUploadedCount();
        csvMetrics.incrementCsvLines(csvLineEntityList.size());
    }

    @Override
    public List<CsvLine> fetchAllData()
    {
        log.info("Fetching all CSV lines...");
        return csvLineRepository.findAll().stream()
            .map(csvLineMapper::fromCsvLineEntityToCsvLine)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteCsvLines()
    {
        log.info("Deleting all CSV lines...");
        csvLineRepository.deleteAll();
        csvMetrics.setCsvLinesTo0();
    }

    @Override
    public Optional<CsvLine> fetchCsvLine(final String code)
    {
        log.info("Fetching csv line with code:{}", code);
        return csvLineRepository.findById(code)
            .map(csvLineMapper::fromCsvLineEntityToCsvLine);
    }

}
