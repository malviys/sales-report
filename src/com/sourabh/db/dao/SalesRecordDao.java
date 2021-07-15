package com.sourabh.db.dao;

import com.sourabh.model.SalesRecord;

import java.time.LocalDate;
import java.util.List;

public interface SalesRecordDao {
    SalesRecord insertRecord(SalesRecord record);

    List<SalesRecord> getSalesRecordBetweenDate(LocalDate startDate, LocalDate endDate);
}
