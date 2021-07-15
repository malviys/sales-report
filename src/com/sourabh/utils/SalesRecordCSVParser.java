package com.sourabh.utils;

import com.sourabh.exception.InvalidFileFormatException;
import com.sourabh.model.SalesRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalesRecordCSVParser implements CSVParser<SalesRecord> {
    @Override
    public SalesRecord parse(String str) throws InvalidFileFormatException {
        if (!str.contains(",")) {
            throw new InvalidFileFormatException("File should be CSV");
        }

        final String[] fields = str.split(",");

        if (fields.length != SalesRecord.class.getDeclaredFields().length) {
            throw new InvalidFileFormatException("File fields mismatched");
        }

        if (!fields[0].matches("[0-9]+") &&
                !fields[3].matches("[0-9]+") &&
                !fields[4].matches("[0-9]+.[0-9]+") &&
                !fields[5].matches("[0-9]+.[0-9]+")
        ) {
            throw new InvalidFileFormatException("One of the field doesn't match with type of object");
        }

        final SalesRecord report = new SalesRecord();
        report.setReceiptNumber(Integer.parseInt(fields[0]));
        report.setItemName(fields[1]);
        report.setArea(fields[2]);
        report.setUnitsSold(Integer.parseInt(fields[3]));
        report.setTotalCollection(Double.parseDouble(fields[4]));
        report.setRate(Double.parseDouble(fields[5]));
        report.setSoldDate(LocalDate.parse(fields[6], DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
        return report;
    }

    @Override
    public String toCSV(SalesRecord record) {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                record.getReceiptNumber(),
                record.getItemName(),
                record.getArea(),
                record.getUnitsSold(),
                record.getTotalCollection(),
                record.getRate(),
                record.getSoldDate()
        );
    }
}
