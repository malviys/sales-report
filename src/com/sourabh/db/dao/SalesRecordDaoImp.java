package com.sourabh.db.dao;

import com.sourabh.db.DataSource;
import com.sourabh.model.SalesRecord;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesRecordDaoImp implements SalesRecordDao {
    private static final String TABLE_SALES = "sales";

    private static final String COL_RECEIPT_NUMBER = "receipt_number";
    private static final String COL_ITEM_NAME = "item_name";
    private static final String COL_AREA = "area";
    private static final String COL_UNIT_SOLD = "unit_sold";
    private static final String COL_TOTAL_COLLECTION = "total_collection";
    private static final String COL_RATE = "rate";
    private static final String COL_SOLD_DATE = "sold_date";

    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SALES + " ( " +
                    COL_RECEIPT_NUMBER + " INT(5) AUTO_INCREMENT, " +
                    COL_ITEM_NAME + " CHAR(20), " +
                    COL_AREA + " CHAR(50), " +
                    COL_UNIT_SOLD + " INT(5), " +
                    COL_TOTAL_COLLECTION + " DECIMAL(10,3), " +
                    COL_RATE + " DECIMAL(10,3), " +
                    COL_SOLD_DATE + " DATE, " +
                    "PRIMARY KEY (" + COL_RECEIPT_NUMBER + ")" +
                    " )";

    private static final String INSERT_RECORD_QUERY =
            "INSERT INTO " + TABLE_SALES + " VALUES ( " +
                    "?, ?, ?, ?, ?, ?, ?" +
                    ")";

    private static final String SELECT_RECORD_QUERY =
            "SELECT *" + " FROM " + TABLE_SALES +
                    " WHERE sold_date BETWEEN ? AND ? ORDER BY " +
                    COL_RECEIPT_NUMBER;

    private final DataSource dataSource;
    private boolean tableExists = false;

    public SalesRecordDaoImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public SalesRecord insertRecord(SalesRecord record) {
        if (!tableExists) {
            createTable();
        }

        dataSource.openConnection();

        try (final PreparedStatement statement = dataSource.getStatement(INSERT_RECORD_QUERY)) {
            statement.setInt(1, record.getReceiptNumber());
            statement.setString(2, record.getItemName());
            statement.setString(3, record.getArea());
            statement.setInt(4, record.getUnitsSold());
            statement.setDouble(5, record.getTotalCollection());
            statement.setDouble(6, record.getRate());
            statement.setDate(7, Date.valueOf(record.getSoldDate()));
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        dataSource.closeConnection();

        return record;
    }

    @Override
    public List<SalesRecord> getSalesRecordBetweenDate(LocalDate startDate, LocalDate endDate) {
        final List<SalesRecord> records = new ArrayList<>();

        if (!tableExists) {
            createTable();
        }

        dataSource.openConnection();

        try (final PreparedStatement statement = dataSource.getStatement(SELECT_RECORD_QUERY)) {
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));

            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final SalesRecord record = new SalesRecord();

                record.setReceiptNumber(resultSet.getInt(1));
                record.setItemName(resultSet.getString(2));
                record.setArea(resultSet.getString(3));
                record.setUnitsSold(resultSet.getInt(4));
                record.setTotalCollection(resultSet.getDouble(5));
                record.setRate(resultSet.getDouble(6));
                record.setSoldDate(resultSet.getDate(7).toLocalDate());

                records.add(record);
            }

            resultSet.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        dataSource.closeConnection();

        return records;
    }

    private void createTable() {
        boolean tableExists;

        dataSource.openConnection();

        try (final PreparedStatement statement = dataSource.getStatement(CREATE_TABLE_QUERY)) {
            statement.executeUpdate();
            tableExists = true;
        } catch (SQLException ex) {
            tableExists = false;
            System.out.println(ex.getMessage());
        }

        dataSource.closeConnection();

        this.tableExists = tableExists;
    }
}
