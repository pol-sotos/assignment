package com.assignment.csv;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CsvLineEntity
{
    @Id
    @Column(nullable = false, unique = true)
    private String code;

    @Column
    private String source;

    @Column
    private String codeListCode;

    @Column
    private String displayValue;

    @Column
    private String longDescription;

    @Column
    private LocalDate fromDate;

    @Column
    private LocalDate toDate;

    @Column
    private Integer sortingPriority;

}
