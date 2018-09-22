package com.edgefintrack.expense.ui.expenses;

import android.os.Bundle;

import com.edgefintrack.expense.R;
import com.edgefintrack.expense.ui.BaseActivity;

public class ExpenseDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        String expenseId = getIntent().getStringExtra(ExpenseDetailFragment.EXPENSE_ID_KEY);
        replaceFragment(ExpenseDetailFragment.newInstance(expenseId), false);
    }
}
