package com.edgefintrack.expense.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.edgefintrack.expense.R;
import com.edgefintrack.expense.entities.Category;
import com.edgefintrack.expense.interfaces.IExpensesType;
import com.edgefintrack.expense.ui.login.WelcomePage;
import com.edgefintrack.expense.utils.RealmManager;

import java.util.ArrayList;


public class WelcomePagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 3;

    int expenses_Mode = IExpensesType.MODE_EXPENSES;
    int income_Mode = IExpensesType.MODE_INCOME;

    public WelcomePagerAdapter(FragmentManager fm) {
        super(fm);
        onSetECategory();
        onSetICategory();
    }

    @Override
    public Fragment getItem(int position) {
        WelcomePage tp = null;
        switch (position) {
            case 0:
                tp = WelcomePage.newInstance(R.layout.layout_welcome_first);
                break;
            case 1:
                tp = WelcomePage.newInstance(R.layout.layout_welcome_second);
                break;
            case 2:
                tp = WelcomePage.newInstance(R.layout.layout_welcome_third);
                break;
        }
        return tp;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    public void onSetECategory() {
        //String ArrayList
        ArrayList<String> cList = new ArrayList<String>();
        cList.add("\uD83C\uDF54 Food and Groceries");
        cList.add("\uD83D\uDCFD Entertainment");
        cList.add("\uD83D\uDC8A Health & Fitness");
        cList.add("\uD83C\uDFEB Education");
        cList.add("\uD83D\uDECD Shopping");
        cList.add("\uD83D\uDC68\u200D\uD83D\uDD27 Personal Care");
        cList.add("\uD83D\uDC9D Gifts & Donations");
        cList.add("\uD83D\uDCDF Bills & Utilities");
        cList.add("\uD83D\uDE95 Auto & Transport");
        for (int i = 0; i < cList.size(); i++) {
            String category_name = cList.get(i);
            Category category = new Category(category_name, expenses_Mode);
            RealmManager.getInstance().save(category, Category.class);
        }

    }

    public void onSetICategory() {
        //String ArrayList
        ArrayList<String> cList = new ArrayList<String>();
        cList.add("\uD83D\uDCB0 Salary");
        cList.add("\uD83D\uDCB2 Paycheck");
        cList.add("\uD83E\uDD11 Bonus");
        cList.add("\uD83D\uDCB5 Interest Income");
        cList.add("\uD83D\uDCBC Reimbursement");
        cList.add("\uD83C\uDFE0 Rental Income");
        for (int i = 0; i < cList.size(); i++) {
            String category_name = cList.get(i);
            Category category = new Category(category_name, income_Mode);
            RealmManager.getInstance().save(category, Category.class);
        }

    }

}