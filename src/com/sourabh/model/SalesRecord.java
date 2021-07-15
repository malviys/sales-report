package com.sourabh.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.sourabh.exception.InvalidFileFormatException;
import com.sourabh.utils.CSVParser;

public class SalesRecord {

	private Integer receiptNumber;
	private String itemName;
	private String area;
	private Integer unitsSold;
	private Double totalCollection;
	private Double rate;
	private LocalDate soldDate;

	public Integer getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(Integer receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getUnitsSold() {
		return unitsSold;
	}

	public void setUnitsSold(Integer unitsSold) {
		this.unitsSold = unitsSold;
	}

	public Double getTotalCollection() {
		return totalCollection;
	}

	public void setTotalCollection(Double totalCollection) {
		this.totalCollection = totalCollection;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public LocalDate getSoldDate() {
		return soldDate;
	}

	public void setSoldDate(LocalDate soldDate) {
		this.soldDate = soldDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SalesRecord report = (SalesRecord) o;
		return Objects.equals(receiptNumber, report.receiptNumber) && Objects.equals(itemName, report.itemName)
				&& Objects.equals(area, report.area) && Objects.equals(unitsSold, report.unitsSold)
				&& Objects.equals(totalCollection, report.totalCollection) && Objects.equals(rate, report.rate)
				&& Objects.equals(soldDate, report.soldDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(receiptNumber, itemName, area, unitsSold, totalCollection, rate, soldDate);
	}

	@Override
	public String toString() {
		return "Sales {" + "receiptNumber=" + receiptNumber + ", itemName=" + itemName + ", area=" + area
				+ ", unitsSold=" + unitsSold + ", totalCollection=" + totalCollection + ", rate=" + rate + ", soldDate="
				+ soldDate + "}";
	}
}
