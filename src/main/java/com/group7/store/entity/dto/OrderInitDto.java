package com.group7.store.entity.dto;


import com.group7.store.entity.order.Expense;
import com.group7.store.entity.user.Address;

import java.util.List;

public class OrderInitDto {
    /**
     * 用户账号
     */
    private String account;

    private List<OrderBookDto> bookList;
    /**
     * 返回给前端显示的某个用户的地址
     */
    private List<Address> addressList;
    private Expense expense;
    private Address address;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<OrderBookDto> getBookList() {
        return bookList;
    }

    public void setBookList(List<OrderBookDto> bookList) {
        this.bookList = bookList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderInitDto{" +
                "account='" + account + '\'' +
                ", bookList=" + bookList +
                ", addressList=" + addressList +
                ", expense=" + expense +
                ", address=" + address +
                '}';
    }
}
