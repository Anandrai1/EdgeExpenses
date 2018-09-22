package com.edgefintrack.expense.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edgefintrack.expense.R;
import com.edgefintrack.expense.entities.Category;

public class ExpenseCategoriesAdapter extends RecyclerView.Adapter<ExpenseCategoriesAdapter.MyViewHolder> {

    Category[] categoriesList;
    Context context;

    public ExpenseCategoriesAdapter(Context context, Category[] categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category category = categoriesList[position];
        holder.title.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categoriesList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
