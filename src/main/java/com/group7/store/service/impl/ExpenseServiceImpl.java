package com.group7.store.service.impl;


import com.group7.store.entity.order.Expense;
import com.group7.store.mapper.ExpenseMapper;
import com.group7.store.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("expense")
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseMapper expenseMapper;

    @Override
    public int addExpense(Expense expense) {
        return expenseMapper.addExpense(expense);
    }
}
