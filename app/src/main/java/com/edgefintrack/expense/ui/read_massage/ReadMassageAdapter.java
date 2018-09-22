package com.edgefintrack.expense.ui.read_massage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edgefintrack.expense.R;

import java.util.ArrayList;

public class ReadMassageAdapter extends RecyclerView.Adapter<ReadMassageAdapter.MyViewHolder> {

    ArrayList<String> arrayList_mass;
    Context context;

    public ReadMassageAdapter(Context context, ArrayList<String> arrayList_mass) {
        this.context = context;
        this.arrayList_mass = arrayList_mass;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
/* this.colorExpense = EdgeExpensesApp.getContext().getResources().getColor(R.color.red_400);
        this.colorIncome = EdgeExpensesApp.getContext().getResources().getColor(R.color.SeaGreen);*/
        if (arrayList_mass.get(position).contains("is credited by")) {
            holder.title.setTextColor(context.getResources().getColor(R.color.SeaGreen));
        } else if (arrayList_mass.get(position).contains("been debited for") || arrayList_mass.get(position).contains("is debited for") || arrayList_mass.get(position).contains("successful payment")) {
            holder.title.setTextColor(context.getResources().getColor(R.color.red_400));
        } else if (arrayList_mass.get(position).contains("Clear Bal") || arrayList_mass.get(position).contains("Avail Bal") || arrayList_mass.get(position).contains("Avl Bal") || arrayList_mass.get(position).contains("Available Balance")|| arrayList_mass.get(position).contains("available balance")) {
            holder.title.setTextColor(context.getResources().getColor(R.color.yellow_900));
        }
        holder.title.setText(arrayList_mass.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList_mass.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}