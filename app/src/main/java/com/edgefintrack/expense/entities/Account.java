package com.edgefintrack.expense.entities;

import com.edgefintrack.expense.interfaces.IExpensesType;
import com.edgefintrack.expense.utils.RealmManager;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Account extends RealmObject {
    @PrimaryKey
    private String id;

    private String name;
    private float total;

    public Account() {
    }

    public Account(String name, float total) {
        this.name = name;
        this.total = total;
    }
    public static List<Account> getAccount() {
        return RealmManager.getInstance().getRealmInstance().where(Account.class)
                .findAll();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
