package hse.kpo.export.visitors.JSONvisitors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import hse.kpo.entities.BankAccount;
import hse.kpo.entities.Category;
import hse.kpo.entities.Operation;
import hse.kpo.interfaces.Visitor;
import hse.kpo.storages.IdEntityStorage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
public class JsonVisitor {
    private Map<String, List<Object>> objects = Map.of(
            "BankAccount", new ArrayList<>(),
            "Category", new ArrayList<>(),
            "Operation", new ArrayList<>()
        );

    @Autowired
    IdEntityStorage<BankAccount> accountStorage;
    @Autowired
    IdEntityStorage<Category> categoryStorage;
    @Autowired
    IdEntityStorage<Operation> operationStorage;

    JsonEntityVisitor<BankAccount> BankAccountvisitor = new JsonEntityVisitor<BankAccount>();;
    JsonEntityVisitor<Category> Categoryvisitor = new JsonEntityVisitor<Category>();;
    JsonEntityVisitor<Operation> Operationvisitor = new JsonEntityVisitor<Operation>();;

    // JsonVisitor() {
    //     clear();
    //     BankAccountvisitor = new JsonEntityVisitor<BankAccount>();
    //     Categoryvisitor = new JsonEntityVisitor<Category>();
    //     Operationvisitor = new JsonEntityVisitor<Operation>();
    // }

    private void clear() {
        objects = Map.of(
                "BankAccount", new ArrayList<>(),
                "Category", new ArrayList<>(),
                "Operation", new ArrayList<>());
        BankAccountvisitor.clear();
        Categoryvisitor.clear();
        Operationvisitor.clear();
    }

    public boolean writeJson(PrintWriter printer) {
        accountStorage.accept(BankAccountvisitor);
        categoryStorage.accept(Categoryvisitor);
        operationStorage.accept(Operationvisitor);
        objects.put("BankAccount", BankAccountvisitor.getObjects());
        objects.put("Category", Categoryvisitor.getObjects());
        objects.put("Operation", Operationvisitor.getObjects());
        var mapper = new ObjectMapper();
        boolean result = true;
        try {
            mapper.writeValue(printer, objects);
        } catch (IOException e) {
            result = false;
        }
        clear();
        return result;
    }
}