package com.edgefintrack.expense.adapters;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.edgefintrack.expense.EdgeExpensesApp;
import com.edgefintrack.expense.R;
import com.edgefintrack.expense.custom.BaseViewHolder;
import com.edgefintrack.expense.entities.Expense;
import com.edgefintrack.expense.interfaces.IExpensesType;
import com.edgefintrack.expense.utils.ExpensesManager;
import com.edgefintrack.expense.utils.Util;

import java.text.SimpleDateFormat;
import java.util.List;


public class BaseExpenseAdapter<VH extends RecyclerView.ViewHolder> extends BaseExpenseRecyclerViewAdapter<BaseExpenseAdapter.BaseExpenseViewHolder> {

    protected List<Expense> mExpensesList;
    protected int lastPosition = -1;
    protected int colorExpense;
    protected int colorIncome;
    protected String prefixExpense;
    protected String prefixIncome;
    protected BaseViewHolder.RecyclerClickListener onRecyclerClickListener;
    private String titleTransitionName;

    public BaseExpenseAdapter(Context context, BaseViewHolder.RecyclerClickListener onRecyclerClickListener) {
        this.mExpensesList = ExpensesManager.getInstance().getExpensesList();
        this.onRecyclerClickListener = onRecyclerClickListener;
        this.colorExpense = EdgeExpensesApp.getContext().getResources().getColor(R.color.red_400);
        this.colorIncome = EdgeExpensesApp.getContext().getResources().getColor(R.color.SeaGreen);
        this.prefixExpense = EdgeExpensesApp.getContext().getResources().getString(R.string.expense_prefix);
        this.prefixIncome = EdgeExpensesApp.getContext().getResources().getString(R.string.income_prefix);
        this.titleTransitionName = EdgeExpensesApp.getContext().getString(R.string.tv_title_transition);
    }

    @Override
    public BaseExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_expense_item, parent, false);
        return new BaseExpenseViewHolder(v, onRecyclerClickListener);
    }

    @Override
    public void onBindViewHolder(BaseExpenseViewHolder holder, int position) {
        holder.itemView.setSelected(isSelected(position));
        final Expense expense = mExpensesList.get(position);
        String prefix = "";
        switch (expense.getType()) {
            case IExpensesType.MODE_EXPENSES:
                holder.tvTotal.setTextColor(colorExpense);
                prefix = String.format(prefixExpense, Util.getFormattedCurrency(expense.getTotal()));
                break;
            case IExpensesType.MODE_INCOME:
                holder.tvTotal.setTextColor(colorIncome);
                prefix = String.format(prefixIncome, Util.getFormattedCurrency(expense.getTotal()));
                break;
        }
        if (expense.getDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
            SimpleDateFormat yesrFormat = new SimpleDateFormat("MMMM, yyyy");
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
            holder.expanded_date.setText(String.valueOf(dateFormat.format(expense.getDate())));
            holder.expanded_year.setText(String.valueOf(yesrFormat.format(expense.getDate())));
            holder.expanded_day.setText(String.valueOf(dayFormat.format(expense.getDate())));
        }
        if (expense.getCategory() != null)
            holder.tvCategory.setText(expense.getCategory().getName());

        if (expense.getAccount() != null){
            holder.tv_account.setText(expense.getAccount().getName());
        }else {
            holder.tv_account.setText("Account");
        }


        if (expense.getDescription() != null && !expense.getDescription().isEmpty()) {
            holder.tvDescription.setText(expense.getDescription());
            holder.tvDescription.setVisibility(View.VISIBLE);
        } else {
            holder.tvDescription.setVisibility(View.GONE);
        }
        holder.tvTotal.setText(prefix);
        holder.itemView.setTag(expense);
        ViewCompat.setTransitionName(holder.tvTotal, titleTransitionName);
    }

    @Override
    public int getItemCount() {
        return mExpensesList.size();
    }

    public void updateExpenses(List<Expense> mExpensesList) {
        this.mExpensesList = mExpensesList;
        notifyDataSetChanged();
    }

    protected void setAnimation(BaseExpenseViewHolder holder, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(EdgeExpensesApp.getContext(), R.anim.push_left_in);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static class BaseExpenseViewHolder extends BaseViewHolder {

        public TextView tvCategory;
        public TextView tvDescription;
        public TextView tvTotal, expanded_date, expanded_year, expanded_day, tv_account;

        public BaseExpenseViewHolder(View v, RecyclerClickListener onRecyclerClickListener) {
            super(v, onRecyclerClickListener);
            tvCategory = (TextView) v.findViewById(R.id.tv_category);
            tvDescription = (TextView) v.findViewById(R.id.tv_description);
            tvTotal = (TextView) v.findViewById(R.id.tv_total);
            expanded_date = (TextView) v.findViewById(R.id.expanded_date);
            expanded_year = (TextView) v.findViewById(R.id.expanded_year);
            expanded_day = (TextView) v.findViewById(R.id.expanded_day);
            tv_account = (TextView) v.findViewById(R.id.tv_account);
        }

    }


}
