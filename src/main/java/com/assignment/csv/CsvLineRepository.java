package com.assignment.csv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CsvLineRepository extends JpaRepository<CsvLineEntity, String>
{
}
