package com.assignment.csv;

import org.springframework.stereotype.Component;

import com.assignment.api.model.CsvLine;


@Component
public class CsvLineMapper
{
    public CsvLine fromCsvLineEntityToCsvLine(final CsvLineEntity entity)
    {
        if (entity == null)
        {
            return null;
        }
        return CsvLine.builder()
            .code(entity.getCode())
            .source(entity.getSource())
            .codeListCode(entity.getCodeListCode())
            .displayValue(entity.getDisplayValue())
            .longDescription(entity.getLongDescription())
            .fromDate(entity.getFromDate())
            .toDate(entity.getToDate())
            .sortingPriority(entity.getSortingPriority())
            .build();
    }

}
