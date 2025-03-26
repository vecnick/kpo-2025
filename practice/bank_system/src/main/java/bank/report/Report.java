package bank.report;

import bank.enums.DomainType;

import java.util.List;

public record Report<T>(DomainType title, List<T> content) {}

