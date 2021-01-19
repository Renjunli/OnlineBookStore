package com.group7.store.mapper;

import com.group7.store.entity.order.Expense;
import org.springframework.stereotype.Repository;


@Repository
public interface ExpenseMapper {
    int addExpense(Expense expense);
}
