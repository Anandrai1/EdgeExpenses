package com.edgefintrack.expense.ui.expenses;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import com.edgefintrack.expense.R;
import com.edgefintrack.expense.adapters.AccountAdapter;
import com.edgefintrack.expense.adapters.ExpenseCategoriesAdapter;
import com.edgefintrack.expense.custom.RecyclerTouchListener;
import com.edgefintrack.expense.entities.Account;
import com.edgefintrack.expense.entities.Category;
import com.edgefintrack.expense.entities.Expense;
import com.edgefintrack.expense.interfaces.IDateMode;
import com.edgefintrack.expense.interfaces.IExpensesType;
import com.edgefintrack.expense.ui.CalculatorActivity;
import com.edgefintrack.expense.ui.account.AccountActivity;
import com.edgefintrack.expense.ui.categories.CategoriesActivity;
import com.edgefintrack.expense.utils.DialogManager;
import com.edgefintrack.expense.utils.RealmManager;
import com.edgefintrack.expense.utils.Util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class NewExpenseActivity extends AppCompatActivity implements View.OnClickListener, RecyclerView.OnItemTouchListener {
    public static final String ADD = "\u002B";
    public static final String SUB = "\u2212";
    public static final String DIV = "\u00F7";
    public static final String MUL = "\u2715";
    public String value = "0";
    public LinkedList<String> operators = new LinkedList<String>();
    Button bt_iadd;
    TextView et_amount, tv_date, tv_category, tv_account;
    int mExpenseType = IExpensesType.MODE_EXPENSES;
    String description;
    Category currentCategory;
    Account currentAccount;
    String selectedCategory = "null";
    String selectedAccount = "null";
    TextView tv_toamount;
    TextView tv_expense, tv_income;
    TableLayout TableLayout1;
    ExpenseCategoriesAdapter mCategoriesAdapter;
    AccountAdapter mAccountAdapter;
    Category[] categoriesList;
    Account[] accountsList;
    EditText et_description;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, EEE    hh:mm a");
    ImageButton ib_edit;
    TextView textViewname;
    Boolean isaccount;
    RelativeLayout rl_touch;
    private String TAG = "AddEIActivity";
    private Date selectedDate;
    private Button button;
    private RecyclerView rvCategories, rv_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        tv_toamount = (TextView) findViewById(R.id.tv_toamount);
        //currentCategory = (Category) spCategory.getSelectedItem();
        float total = Expense.getTotalExpensesByDateMode(IDateMode.MODE_MONTH);
        tv_toamount.setText(Util.getFormattedCurrency(total));
        tv_income = (TextView) findViewById(R.id.tv_income);
        tv_income.setOnClickListener(this);

        textViewname = (TextView) findViewById(R.id.textViewname);

        tv_expense = (TextView) findViewById(R.id.tv_expense);
        tv_expense.setOnClickListener(this);
        selectedDate = new Date();
        tv_date = (TextView) findViewById(R.id.tv_date);

        tv_date.setText(String.valueOf(dateFormat.format(selectedDate)));
        tv_date.setOnClickListener(this);

        et_amount = (TextView) findViewById(R.id.et_amount);
        et_amount.setOnClickListener(this);
        et_amount.setText(Util.getFormattedCurrency(Float.parseFloat("0")));

        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_category.setOnClickListener(this);

        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_account.setOnClickListener(this);

        ib_edit = (ImageButton) findViewById(R.id.ib_edit);
        ib_edit.setOnClickListener(this);

        et_description = (EditText) findViewById(R.id.et_description);
       /* et_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus) {
                    rl_touch.setVisibility(View.GONE);
                }
            }
        });*/

        bt_iadd = (Button) findViewById(R.id.bt_iadd);
        bt_iadd.setOnClickListener(this);

        rl_touch = (RelativeLayout) findViewById(R.id.rl_touch);

        TableLayout1 = (TableLayout) findViewById(R.id.TableLayout1);
        rvCategories = (RecyclerView) findViewById(R.id.rv_categories);

        rvCategories.setLayoutManager(new GridLayoutManager(this, 2));
        List<Category> i_categoriesList = Category.getCategoriesExpense();
        categoriesList = new Category[i_categoriesList.size()];
        categoriesList = i_categoriesList.toArray(categoriesList);
        mCategoriesAdapter = new ExpenseCategoriesAdapter(NewExpenseActivity.this, categoriesList);
        rvCategories.setAdapter(mCategoriesAdapter);
        rvCategories.setHasFixedSize(true);
        rvCategories.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCategories, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                currentCategory = categoriesList[position];
                selectedCategory = currentCategory.getName();
                //Toast.makeText(getApplicationContext(), currentCategory.getName(), Toast.LENGTH_SHORT).show();
                tv_category.setText(currentCategory.getName());
                rl_touch.setVisibility(View.GONE);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        rv_account = (RecyclerView) findViewById(R.id.rv_account);

        rv_account.setLayoutManager(new GridLayoutManager(this, 2));
        List<Account> accountList = Account.getAccount();
        accountsList = new Account[accountList.size()];
        accountsList = accountList.toArray(accountsList);
        mAccountAdapter = new AccountAdapter(NewExpenseActivity.this, accountsList);
        rv_account.setAdapter(mAccountAdapter);
        rv_account.setHasFixedSize(true);
        mAccountAdapter.notifyDataSetChanged();
        rv_account.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv_account, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                currentAccount = accountsList[position];
                selectedAccount = currentAccount.getName();
                //Toast.makeText(getApplicationContext(), currentCategory.getName(), Toast.LENGTH_SHORT).show();
                tv_account.setText(currentAccount.getName());
                rl_touch.setVisibility(View.GONE);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_income:
                mExpenseType = IExpensesType.MODE_INCOME;
                tv_category.setText("");
                List<Category> income_categoriesList = Category.getCategoriesIncome();
                categoriesList = new Category[income_categoriesList.size()];
                categoriesList = income_categoriesList.toArray(categoriesList);
                mCategoriesAdapter = new ExpenseCategoriesAdapter(NewExpenseActivity.this, categoriesList);
                rvCategories.setAdapter(mCategoriesAdapter);
                mCategoriesAdapter.notifyDataSetChanged();

                tv_income.setBackgroundColor(getResources().getColor(R.color.blue_600));
                tv_income.setTextColor(getResources().getColor(R.color.white));

                tv_expense.setBackgroundColor(getResources().getColor(R.color.white));
                tv_expense.setTextColor(getResources().getColor(R.color.grey));

                selectedCategory = "null";
                rl_touch.setVisibility(View.VISIBLE);
                et_description.setFocusable(false);
                break;

            case R.id.tv_expense:
                mExpenseType = IExpensesType.MODE_EXPENSES;
                selectedCategory = "null";
                tv_category.setText("");
                List<Category> Expense_categoriesList = Category.getCategoriesExpense();
                categoriesList = new Category[Expense_categoriesList.size()];
                categoriesList = Expense_categoriesList.toArray(categoriesList);
                mCategoriesAdapter = new ExpenseCategoriesAdapter(NewExpenseActivity.this, categoriesList);
                rvCategories.setAdapter(mCategoriesAdapter);
                rvCategories.setHasFixedSize(true);
                mCategoriesAdapter.notifyDataSetChanged();

                tv_income.setBackgroundColor(getResources().getColor(R.color.white));
                tv_income.setTextColor(getResources().getColor(R.color.grey));

                tv_expense.setBackgroundColor(getResources().getColor(R.color.red_400));
                tv_expense.setTextColor(getResources().getColor(R.color.white));
                rl_touch.setVisibility(View.VISIBLE);
                et_description.setFocusable(false);
                break;

            case R.id.tv_account:
                isaccount = true;
                ib_edit.setVisibility(View.VISIBLE);
                textViewname.setText("Account");
                List<Account> accountList = Account.getAccount();
                accountsList = new Account[accountList.size()];
                accountsList = accountList.toArray(accountsList);
                mAccountAdapter = new AccountAdapter(NewExpenseActivity.this, accountsList);
                rv_account.setAdapter(mAccountAdapter);
                rv_account.setHasFixedSize(true);
                mAccountAdapter.notifyDataSetChanged();

                rl_touch.setVisibility(View.VISIBLE);
                et_description.setFocusable(false);

                rv_account.setVisibility(View.VISIBLE);
                rvCategories.setVisibility(View.GONE);
                TableLayout1.setVisibility(View.GONE);
                break;

            case R.id.tv_date:
                showDateDialog();
                rl_touch.setVisibility(View.VISIBLE);
                et_description.setFocusable(false);
                break;

            case R.id.et_amount:
                ib_edit.setVisibility(View.GONE);
                textViewname.setText("Amount");
                rv_account.setVisibility(View.GONE);
                rvCategories.setVisibility(View.GONE);
                TableLayout1.setVisibility(View.VISIBLE);
                rl_touch.setVisibility(View.VISIBLE);
                et_description.setFocusable(false);
                break;

            case R.id.tv_category:
                isaccount = false;
                textViewname.setText("Category");
                ib_edit.setVisibility(View.VISIBLE);
                rvCategories.setVisibility(View.VISIBLE);
                TableLayout1.setVisibility(View.GONE);
                rl_touch.setVisibility(View.VISIBLE);
                rv_account.setVisibility(View.GONE);
                et_description.setFocusable(false);
                break;

            case R.id.ib_edit:
                if (isaccount) {
                    Intent AccountIntent = new Intent(this, AccountActivity.class);
                    startActivity(AccountIntent);
                } else {
                    Intent CategoriesIntent = new Intent(this, CategoriesActivity.class);
                    CategoriesIntent.putExtra("mCurrentMode", mExpenseType);
                    startActivity(CategoriesIntent);
                }
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;

            case R.id.bt_iadd:
                description = et_description.getText().toString();
               /* amount = et_amount.getText().toString();
                amount.replace("₹  ", "");*/
                if (!selectedCategory.equalsIgnoreCase("null")) {
                    RealmManager.getInstance().save(new Expense(description, selectedDate, mExpenseType, currentAccount, currentCategory, Float.parseFloat(value)), Expense.class);
                    tv_toamount.setText(Util.getFormattedCurrency(Float.parseFloat(value)));
                    //tv_date.setText(selectedDate.toString());
                    //  Toast.makeText(this, "" + String.valueOf(mExpenseType), Toast.LENGTH_SHORT).show();
                    NewExpenseActivity.this.finish();
                } else {
                    rvCategories.setVisibility(View.VISIBLE);
                    TableLayout1.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void showDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        DialogManager.getInstance().showDatePickerDialog(NewExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);
                selectedDate = calendar.getTime();
                updateDate();
            }
        }, calendar);
    }

    private void updateDate() {
        tv_date.setText(String.valueOf(dateFormat.format(selectedDate)));
        //tv_date.setText(Util.formatDateToString(selectedDate, Util.getCurrentDateFormat()));
    }

    // Event handlers must be public, void, and have a view object as their only parameter!
    public void registerKey(View view) {
        switch (view.getId()) {
            case R.id.button0:
                safelyPlaceOperand("0");
                break;
            case R.id.buttoncla:
                value = "0";
                break;
            case R.id.button1:
                safelyPlaceOperand("1");
                break;
            case R.id.button2:
                safelyPlaceOperand("2");
                break;
            case R.id.button3:
                safelyPlaceOperand("3");
                break;
            case R.id.button4:
                safelyPlaceOperand("4");
                break;
            case R.id.button5:
                safelyPlaceOperand("5");
                break;
            case R.id.button6:
                safelyPlaceOperand("6");
                break;
            case R.id.button7:
                safelyPlaceOperand("7");
                break;
            case R.id.button8:
                safelyPlaceOperand("8");
                break;
            case R.id.button9:
                safelyPlaceOperand("9");
                break;
            case R.id.buttonDot:
                safelyPlaceOperand(".");
                break;
            case R.id.buttonSub:
                //safelyPlaceOperand("9");
                break;
            case R.id.buttonDel:
                deleteFromLeft();
                break;
            case R.id.buttonCal:
              /*  Intent intentUpdateProfile = new Intent(NewExpenseActivity.this, CalculatorActivity.class);
                // intentUpdateProfile.putExtra("layout", "cardview_Presonal");
                startActivity(intentUpdateProfile);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);*/

                Intent i = new Intent(NewExpenseActivity.this, CalculatorActivity.class);
                startActivityForResult(i, 10);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.buttonDone:
                rvCategories.setVisibility(View.VISIBLE);
                TableLayout1.setVisibility(View.GONE);
                break;
        }
        display();
    }

    private void display() {
        try {
            et_amount.setText(Util.getFormattedCurrency(Float.parseFloat(value)));
        } catch (NumberFormatException e) {
            value = "0";
            et_amount.setText(Util.getFormattedCurrency(Float.parseFloat(value)));
            e.printStackTrace();
        }
    }

    private void safelyPlaceOperand(String op) {
        value += op;
    }

    private void deleteFromLeft() {
        if (value.length() > 0) {
            value = value.substring(0, value.length() - 1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                try {
                    String datas = data.getStringExtra("data");
                    Log.e("GetResponse", "---" + datas);
                    et_amount.setText(Util.getFormattedCurrency(Float.parseFloat(datas)));
                    value = datas;
                    //et_amount.setText(datas);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
