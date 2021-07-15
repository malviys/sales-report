package com.sourabh.utils;

import com.sourabh.exception.InvalidFileFormatException;

public interface CSVParser<T> {
    T parse(String str) throws InvalidFileFormatException;

    String toCSV(T t);
}
