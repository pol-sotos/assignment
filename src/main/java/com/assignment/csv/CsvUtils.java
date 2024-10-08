package com.assignment.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.exceptions.CsvProcessingException;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@UtilityClass
@Slf4j
public class CsvUtils
{
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static List<CsvLineEntity> csvToCsvLines(final MultipartFile file)
    {
        try (final BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             final CSVParser csvParser = CSVFormat.Builder.create()
                 .setHeader()
                 .setSkipHeaderRecord(true)
                 .build()
                 .parse(fileReader))
        {

            final List<CsvLineEntity> csvLineEntities = new ArrayList<>();

            final Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (final CSVRecord csvRecord : csvRecords)
            {
                final CsvLineEntity csvLineEntity = CsvLineEntity.builder()
                    .source(csvRecord.get("source"))
                    .code(csvRecord.get("code"))
                    .codeListCode(csvRecord.get("codeListCode"))
                    .displayValue(csvRecord.get("displayValue"))
                    .fromDate(parseDate(csvRecord.get("fromDate")))
                    .sortingPriority(parseInt(csvRecord.get("sortingPriority")))
                    .toDate(parseDate((csvRecord.get("toDate"))))
                    .longDescription(csvRecord.get("longDescription"))
                    .build();

                csvLineEntities.add(csvLineEntity);
            }

            return csvLineEntities;
        }
        catch (final IOException e)
        {
            log.error("There has been an error processing the CSV with exception: {}", e.getMessage());
            throw new CsvProcessingException("Fail to parse CSV file: " + e.getMessage());
        }
    }

    private static LocalDate parseDate(final String toDate)
    {
        if (toDate == null || toDate.trim().isEmpty())
        {
            return null;
        }
        return LocalDate.parse(toDate, DATE_FORMATTER);
    }

    private static Integer parseInt(final String sortingPriority)
    {
        if (sortingPriority == null || sortingPriority.trim().isEmpty())
        {
            return null;
        }
        return Integer.valueOf(sortingPriority);
    }
}
