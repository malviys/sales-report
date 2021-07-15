package com.sourabh.value;

import com.sourabh.exception.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DateValue extends ValueObject<LocalDate, InvalidDateFormatException> {
    public DateValue(String dateStr, DateTimeFormatter df) {
        try {
            String dateString = Arrays.stream(dateStr.split("-")).map(token ->
                    token.substring(0, 1).toUpperCase() + token.substring(1).toLowerCase()
            ).collect(Collectors.joining("-"));
            this.value = LocalDate.parse(dateString, df);

        } catch (DateTimeParseException ex) {
            this.exception = new InvalidDateFormatException(ex.getMessage());
        }
    }
}
