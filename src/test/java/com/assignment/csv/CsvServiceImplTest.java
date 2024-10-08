package com.assignment.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.api.model.CsvLine;
import com.assignment.metrics.CsvMetrics;


@ExtendWith(MockitoExtension.class)
class CsvServiceImplTest
{
    @Mock
    private CsvLineRepository csvLineRepository;
    @Mock
    private CsvLineMapper csvLineMapper;
    @Mock
    private CsvMetrics csvMetrics;
    @Mock
    private MultipartFile file;

    @InjectMocks
    private CsvServiceImpl csvService;

    @Test
    void testSaveCsvFile_whenSaveIsSuccessful()
    {
        final List<CsvLineEntity> csvLineEntityList = Collections.singletonList(new CsvLineEntity());
        try (final MockedStatic<CsvUtils> csvUtilsMockedStatic = Mockito.mockStatic(CsvUtils.class))
        {
            csvUtilsMockedStatic.when(() -> CsvUtils.csvToCsvLines(any())).thenReturn(csvLineEntityList);
            csvService.saveCsvFile(file);
        }

        verify(csvLineRepository, times(1)).saveAll(csvLineEntityList);
        verify(csvMetrics, times(1)).incrementCsvUploadedCount();
        verify(csvMetrics, times(1)).incrementCsvLines(csvLineEntityList.size());
    }

    @Test
    void testFetchAllData_whenFetchIsSuccessful()
    {
        final CsvLineEntity csvLineEntity = new CsvLineEntity();
        final CsvLine csvLine = new CsvLine();
        when(csvLineRepository.findAll()).thenReturn(Collections.singletonList(csvLineEntity));
        when(csvLineMapper.fromCsvLineEntityToCsvLine(csvLineEntity)).thenReturn(csvLine);

        final List<CsvLine> result = csvService.fetchAllData();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(csvLineRepository, times(1)).findAll();
        verify(csvLineMapper, times(1)).fromCsvLineEntityToCsvLine(csvLineEntity);
    }

    @Test
    void testDeleteCsvLines_whenDeleteIsSuccessful()
    {
        csvService.deleteCsvLines();

        verify(csvLineRepository, times(1)).deleteAll();
        verify(csvMetrics, times(1)).setCsvLinesTo0();
    }

    @Test
    void testFetchCsvLine_whenFetchIsSuccessful()
    {
        final String code = "someCode";
        final CsvLineEntity csvLineEntity = new CsvLineEntity();
        final CsvLine csvLine = new CsvLine();
        when(csvLineRepository.findById(code)).thenReturn(Optional.of(csvLineEntity));
        when(csvLineMapper.fromCsvLineEntityToCsvLine(csvLineEntity)).thenReturn(csvLine);

        final Optional<CsvLine> result = csvService.fetchCsvLine(code);

        assertTrue(result.isPresent());
        assertEquals(csvLine, result.get());
        verify(csvLineRepository, times(1)).findById(code);
        verify(csvLineMapper, times(1)).fromCsvLineEntityToCsvLine(csvLineEntity);
    }

    @Test
    void testFetchCsvLine_whenNotFound()
    {
        final String code = "nonExistentCode";
        when(csvLineRepository.findById(code)).thenReturn(Optional.empty());

        final Optional<CsvLine> result = csvService.fetchCsvLine(code);

        assertFalse(result.isPresent());
        verify(csvLineRepository, times(1)).findById(code);
        verify(csvLineMapper, never()).fromCsvLineEntityToCsvLine(any());
    }
}
