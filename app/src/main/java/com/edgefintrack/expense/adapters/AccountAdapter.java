package com.edgefintrack.expense.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edgefintrack.expense.R;
import com.edgefintrack.expense.entities.Account;
import com.edgefintrack.expense.utils.Util;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {

    Account[] accountsList;
    Context context;

    public AccountAdapter(Context context, Account[] accountsList) {
        this.context = context;
        this.accountsList = accountsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Account account = accountsList[position];
        holder.title.setText(account.getName()+" - "+ Util.getFormattedCurrency(account.getTotal()));
    }

    @Override
    public int getItemCount() {
        return accountsList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
