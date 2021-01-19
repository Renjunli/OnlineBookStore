package com.group7.store.service;

import com.group7.store.entity.order.Expense;
import org.springframework.stereotype.Service;


@Service
public interface ExpenseService {
    int addExpense(Expense expense);
}
