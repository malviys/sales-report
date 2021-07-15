package com.sourabh.db.dao;

import com.sourabh.model.SalesRecord;

import java.time.LocalDate;
import java.util.List;

public interface SalesRecordDao {
    /**
    * Insert record in database
    */
    SalesRecord insertRecord(SalesRecord record);

    /**
    * Reterive all the sales record from database between start-date and end-date
    */
    List<SalesRecord> getSalesRecordBetweenDate(LocalDate startDate, LocalDate endDate);
}
