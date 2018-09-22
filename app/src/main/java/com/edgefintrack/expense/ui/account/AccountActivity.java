package com.edgefintrack.expense.ui.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.edgefintrack.expense.R;
import com.edgefintrack.expense.entities.Account;
import com.edgefintrack.expense.utils.RealmManager;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private List<Account> mAccountList;
    TextView accountlist;
    EditText ed_account, ed_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        accountlist = (TextView) findViewById(R.id.accountlist);
        ed_account = (EditText) findViewById(R.id.ed_account);
        ed_amount = (EditText) findViewById(R.id.ed_amount);
        mAccountList = new ArrayList<>();
        mAccountList = Account.getAccount();
        accountlist.setText(mAccountList.toString());
    }

    public void onSaveCategories(View view) {
        RealmManager.getInstance().save(new Account(ed_account.getText().toString(), Float.parseFloat(ed_amount.getText().toString())), Account.class);
        mAccountList = Account.getAccount();
        accountlist.setText(mAccountList.toString());
    }
}
