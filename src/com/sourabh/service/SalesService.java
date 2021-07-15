package com.sourabh.service;

import com.sourabh.db.DataSource;
import com.sourabh.db.dao.SalesRecordDao;
import com.sourabh.db.dao.SalesRecordDaoImp;
import com.sourabh.exception.InvalidFileException;
import com.sourabh.exception.InvalidFileFormatException;
import com.sourabh.model.SalesRecord;
import com.sourabh.model.Summary;
import com.sourabh.utils.SalesRecordCSVParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SalesService {
    private final SalesRecordDao salesRecordDao;

    public SalesService(DataSource dataSource) {
        this.salesRecordDao = new SalesRecordDaoImp(dataSource);
    }

    public Summary insertSalesRecordFromCsv(String filePath) throws InvalidFileException {
        final File file = new File(filePath);
        final Summary summary = new Summary();
        final List<String> errors = new ArrayList<>();

        if (!file.exists()) {
            throw new InvalidFileException("Invalid file location!");
        }

        try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            final Stream<String> stream = reader.lines();

            summary.setTotalRecords((int) stream.count());

            final Optional<Integer> totalInsertedRecords = stream.map(rec -> {
                try {
                    final SalesRecord record = new SalesRecordCSVParser().parse(rec);
                    insertSalesRecord(record);
                } catch (InvalidFileFormatException ex) {
                    errors.add(ex.getMessage());
                }
                return 1;
            }).reduce(Integer::sum);

            if(!errors.isEmpty()){
                throw new InvalidFileException(errors.toString());
            }

            summary.setTotalRecords(totalInsertedRecords.get());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return summary;
    }

    public String readSales(LocalDate startDate, LocalDate endDate) {
        final List<SalesRecord> records = this.salesRecordDao.getSalesRecordBetweenDate(startDate, endDate);
        final StringBuilder sbr = new StringBuilder();

        if (records.isEmpty()) {
            sbr.append(String.format("No records were found between %s and %s", startDate, endDate));
            return sbr.toString();
        }

        final int monthsBetween = (
                endDate.getYear() > startDate.getYear()
                        ? 12 - startDate.getMonthValue() + endDate.getMonthValue()
                        : Period.between(startDate, endDate).getMonths()
        ) + 1;

        for (int i = 0; i < monthsBetween; ++i) {
            final LocalDate currentDate = startDate.plusMonths(i);
            sbr.append(String.format(
                    "%s%s\t\t",
                    currentDate.getMonth().toString().substring(0, 3),
                    currentDate.getYear()
            ));
        }
        sbr.append("\n");

        records.forEach(rec -> {
            for (int i = 0; i < monthsBetween; ++i) {
                final LocalDate currentDate = startDate.plusMonths(i);

                if (rec.getSoldDate().getMonth() == currentDate.getMonth() &&
                        rec.getSoldDate().getYear() == currentDate.getYear()) {
                    sbr.append(
                            String.format("%s %s\t\t", rec.getUnitsSold(), rec.getTotalCollection())
                    );
                } else {
                    sbr.append(String.format("%s %s\t\t", " ", " "));
                }
            }
            sbr.append("\n");
        });

        return sbr.toString();
    }

    public void insertSalesRecord(SalesRecord record) {
        this.salesRecordDao.insertRecord(record);
    }
}
