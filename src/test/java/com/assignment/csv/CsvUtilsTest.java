package com.assignment.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;


@ExtendWith(MockitoExtension.class)
class CsvUtilsTest
{
    @Mock
    private MultipartFile file;

    @Test
    void testCsvToCsvLines_whenValidCsv() throws Exception
    {
        final String csvData = "source,code,codeListCode,displayValue,fromDate,sortingPriority,toDate,longDescription\n"
            + "A,123,001,Display Value,01-01-2020,1,01-01-2021,Description here";

        final InputStream inputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));
        when(file.getInputStream()).thenReturn(inputStream);

        final List<CsvLineEntity> result = CsvUtils.csvToCsvLines(file);

        assertNotNull(result);
        assertEquals(1, result.size());
        final CsvLineEntity csvLineEntity = result.get(0);
        assertEquals("A", csvLineEntity.getSource());
        assertEquals("123", csvLineEntity.getCode());
        assertEquals("001", csvLineEntity.getCodeListCode());
        assertEquals("Display Value", csvLineEntity.getDisplayValue());
        assertEquals(LocalDate.of(2020, 1, 1), csvLineEntity.getFromDate());
        assertEquals(1, csvLineEntity.getSortingPriority());
        assertEquals(LocalDate.of(2021, 1, 1), csvLineEntity.getToDate());
        assertEquals("Description here", csvLineEntity.getLongDescription());
    }

}
