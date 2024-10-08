package com.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.experimental.StandardException;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@StandardException
public class CsvProcessingException extends RuntimeException
{

}
