package com.sourabh.ui;

import com.sourabh.db.DataSource;
import com.sourabh.exception.InvalidFileException;
import com.sourabh.service.SalesService;
import com.sourabh.value.DateValue;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Menu {
    private final SalesService salesService = new SalesService(new DataSource("reports", "root", "root"));
    private final Scanner consoleInput = new Scanner(System.in);
    private boolean showMenu = true;

    public void showMenu() {
        int option;

        while (showMenu) {
            option = showMenuOptions();

            switch (option) {
                case 1:
                    System.out.println("Enter your file name");

                    final String fileName = consoleInput.nextLine();

                    storeData(fileName);
                    break;

                case 2:
                    final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

                    System.out.println("Enter start date (dd-MMM-yyyy)");
                    final DateValue startDate = new DateValue(consoleInput.nextLine(), df);

                    System.out.println("Enter end date (dd-MMM-yyyy)");
                    final DateValue endDate = new DateValue(consoleInput.nextLine(), df);

                    if (startDate.isValid() && endDate.isValid()) {
                        displayRecords(startDate, endDate);
                    } else {
                        System.out.println("Invalid date");
                    }

                    break;

                case 3:
                    exit();
                    break;
            }
        }
    }

    private void displayRecords(DateValue startDate, DateValue endDate) {
        String result = salesService.readSales(startDate.getValue(), endDate.getValue());
        System.out.println(result);
    }

    private void storeData(String fileName) {
        try {
            salesService.insertSalesRecordFromCsv(fileName);
        } catch (InvalidFileException e) {
            System.out.println(e.getMessage());
        }
    }

    private void exit() {
        consoleInput.close();
        showMenu = false;
    }

    private int showMenuOptions() {
        System.out.println("=======================Menu=========================");
        System.out.println("1. Upload Report");
        System.out.println("2. Display Summary");
        System.out.println("3. Quit");
        System.out.print(">> ");
        final int option = consoleInput.nextInt();
        consoleInput.nextLine();
        return option;
    }
}