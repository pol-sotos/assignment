package com.assignment.metrics;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Component
public class CsvMetrics
{

    private final MeterRegistry meterRegistry;

    protected final AtomicInteger currentCsvLinesCount = new AtomicInteger(0);

    public CsvMetrics(final MeterRegistry registry)
    {
        meterRegistry = registry;
        initializeCsvLinesGauge();
    }

    @RequiredArgsConstructor
    @Getter
    public enum Metric
    {
        UPLOADED_CSV("uploaded.csv"),
        CURRENT_CSV_LINES("current.csv.lines");

        private final String name;
    }

    private void initializeCsvLinesGauge()
    {
        Gauge.builder(Metric.CURRENT_CSV_LINES.getName(), currentCsvLinesCount, AtomicInteger::get)
            .description("The current number of csv lines")
            .register(meterRegistry);
    }

    public void incrementCsvUploadedCount()
    {
        meterRegistry.counter(Metric.UPLOADED_CSV.getName()).increment();
    }

    public void setCsvLinesTo0()
    {
        currentCsvLinesCount.set(0);
    }

    public void incrementCsvLines(final int size)
    {
        currentCsvLinesCount.getAndAdd(size);
    }


}
