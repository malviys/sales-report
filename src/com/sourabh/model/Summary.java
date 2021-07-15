package com.sourabh.model;

import java.util.Objects;

public class Summary {
    private int totalRecords;
    private int totalRecordsProcessed;

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalRecordsProcessed() {
        return totalRecordsProcessed;
    }

    public void setTotalRecordsProcessed(int totalRecordsProcessed) {
        this.totalRecordsProcessed = totalRecordsProcessed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summary summary = (Summary) o;
        return totalRecords == summary.totalRecords &&
                totalRecordsProcessed == summary.totalRecordsProcessed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalRecords, totalRecordsProcessed);
    }

    @Override
    public String toString() {
        return "Summary{" +
                "totalRecords=" + totalRecords +
                ", totalRecordsProcessed=" + totalRecordsProcessed +
                '}';
    }
}
