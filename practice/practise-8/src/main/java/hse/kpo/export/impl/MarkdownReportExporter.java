package hse.kpo.export.impl;

import hse.kpo.domains.Report;
import hse.kpo.export.ReportExporter;

import java.io.IOException;
import java.io.Writer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MarkdownReportExporter implements ReportExporter {
    @Override
    public void export(Report report, Writer writer) throws IOException {
        writer.write("# " + report.title() + "\n\n");

        // Регулярное выражение для поиска покупателей
        Pattern pattern1 = Pattern.compile("(?<=^.*)Customer\\(.*?\\)");
        Matcher matcher1 = pattern1.matcher(report.content());
        String customers_before = matcher1.results().map(MatchResult::group).collect(Collectors.joining("\n"));

        writer.write("##CUSTOMERS BEFORE SELLS\n\n");
        writer.write(customers_before);
        writer.write("\n\n");

        Pattern pattern2 = Pattern.compile("(?<=Операция: Продажа: )\\s*(.*)");
        Matcher matcher2 = pattern2.matcher(report.content());
        String sells = matcher2.results().map(MatchResult::group).collect(Collectors.joining("\n"));

        writer.write("##SELLS\n\n");
        writer.write(sells);
        writer.write("\n\n");

        Pattern pattern3 = Pattern.compile("Customer\\(.*?\\)(?=.*$)");
        Matcher matcher3 = pattern3.matcher(report.content());
        String customers_after = matcher3.results().map(MatchResult::group).collect(Collectors.joining("\n"));

        writer.write("##CUSTOMERS AFTER SELLS\n\n");
        writer.write(customers_after);
        writer.write("\n\n");

        writer.flush();
    }
}