package bank.interfaces;

import bank.report.Report;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public interface ImporterStrategy<T> {
    Report<T> parse(Class<T> clazz, BufferedReader bufferedReader) throws IOException;
}

