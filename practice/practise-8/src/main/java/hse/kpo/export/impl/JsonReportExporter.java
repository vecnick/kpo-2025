package hse.kpo.export.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.domains.Report;
import hse.kpo.domains.ReportJson;
import hse.kpo.export.ReportExporter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JsonReportExporter implements ReportExporter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void export(Report report, Writer writer) throws IOException {
        Pattern pattern1 = Pattern.compile("(?<=^.*)Customer\\(.*?\\)");
        Matcher matcher1 = pattern1.matcher(report.content());
        List<String> customers_before = matcher1.results().map(MatchResult::group).collect(Collectors.toList());

        Pattern pattern2 = Pattern.compile("(?<=Операция: Продажа: )\\s*(.*)");
        Matcher matcher2 = pattern2.matcher(report.content());
        List<String> sells = matcher2.results().map(MatchResult::group).collect(Collectors.toList());

        Pattern pattern3 = Pattern.compile("Customer\\(.*?\\)(?=.*$)");
        Matcher matcher3 = pattern3.matcher(report.content());
        List<String> customers_after = matcher3.results().map(MatchResult::group).collect(Collectors.toList());

        // Составляем объект с разделёнными полями
        ReportJson reportJson = new ReportJson(report.title(), customers_before, sells, customers_after);
        objectMapper.writeValue(writer, reportJson);
    }
}