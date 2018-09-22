package com.edgefintrack.expense.ui.categories;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.edgefintrack.expense.R;
import com.edgefintrack.expense.adapters.CategoriesAdapter;
import com.edgefintrack.expense.custom.BaseViewHolder;
import com.edgefintrack.expense.custom.DefaultRecyclerViewItemDecorator;
import com.edgefintrack.expense.entities.Category;
import com.edgefintrack.expense.utils.DialogManager;
import com.edgefintrack.expense.utils.RealmManager;
import com.edgefintrack.expense.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements BaseViewHolder.RecyclerClickListener {
    private static String TAG = "CategoriesActivity";
    int mCurrentMode;
    FloatingActionButton fb_add_cat;
    private RecyclerView rvCategories;
    private TextView tvEmpty;
    private List<Category> mCategoryList;
    private CategoriesAdapter mCategoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        setToolbar((Toolbar) findViewById(R.id.toolbar));
        mCategoryList = new ArrayList<>();
        mCategoriesAdapter = new CategoriesAdapter(mCategoryList, this);
        rvCategories = (RecyclerView) findViewById(R.id.rv_categories);
        tvEmpty = (TextView) findViewById(R.id.tv_empty);
        rvCategories.setLayoutManager(new LinearLayoutManager(CategoriesActivity.this));
        rvCategories.setAdapter(mCategoriesAdapter);
        rvCategories.setHasFixedSize(true);
        rvCategories.addItemDecoration(new DefaultRecyclerViewItemDecorator(getResources().getDimension(R.dimen.dimen_10dp)));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCurrentMode = extras.getInt("mCurrentMode");
            reloadData(mCurrentMode);
        }
        fb_add_cat = (FloatingActionButton) findViewById(R.id.fb_add_cat);

        fb_add_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CategoriesIntent = new Intent(CategoriesActivity.this, AddCategoryActivity.class);
                CategoriesIntent.putExtra("mCurrentMode", mCurrentMode);
                startActivity(CategoriesIntent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });

    }

    @Override
    public void onClick(RecyclerView.ViewHolder vh, int position) {
        createCategoryDialog(vh);
    }

    @Override
    public void onLongClick(RecyclerView.ViewHolder vh, int position) {
        toggleSelection(position);
    }

    private void setToolbar(Toolbar mToolbar) {
        setSupportActionBar(mToolbar);
        getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void reloadData(int mCurrentMode) {
        mCategoryList = Category.getCategoriesForType(mCurrentMode);
        if (mCategoryList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
        mCategoriesAdapter.updateCategories(mCategoryList);
        mCategoriesAdapter.notifyDataSetChanged();
    }

    private void eraseCategories() {
        DialogManager.getInstance().createCustomAcceptDialog(CategoriesActivity.this, getString(R.string.delete), getString(R.string.confirm_delete_items), getString(R.string.confirm), getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    List<Category> categoriesToDelete = new ArrayList<>();
                    for (int position : mCategoriesAdapter.getSelectedItems()) {
                        categoriesToDelete.add(mCategoryList.get(position));
                    }
                    RealmManager.getInstance().delete(categoriesToDelete);
                }
                reloadData(mCurrentMode);
                mCategoriesAdapter.clearSelection();
            }
        });
    }

    public void toggleSelection(int position) {
        mCategoriesAdapter.toggleSelection(position);
        int count = mCategoriesAdapter.getSelectedItemCount();
        eraseCategories();
    }

    private void createCategoryDialog(final RecyclerView.ViewHolder vh) {
        AlertDialog alertDialog = DialogManager.getInstance().createEditTextDialog(CategoriesActivity.this, vh != null ? getString(R.string.edit_category) : getString(R.string.create_category), getString(R.string.save), getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    EditText etTest = (EditText) ((AlertDialog) dialog).findViewById(R.id.et_main);
                    if (!Util.isEmptyField(etTest)) {
                        Category category = new Category(etTest.getText().toString(), mCurrentMode);
                        if (vh != null) {
                            Category categoryToUpdate = (Category) vh.itemView.getTag();
                            category.setId(categoryToUpdate.getId());
                            RealmManager.getInstance().update(category);
                        } else {
                            RealmManager.getInstance().save(category, Category.class);
                        }
                        reloadData(mCurrentMode);
                    } else {
                        DialogManager.getInstance().showShortToast(getString(R.string.error_name));
                    }
                }
            }
        });
        if (vh != null) {
            EditText etCategoryName = (EditText) alertDialog.findViewById(R.id.et_main);
            Category category = (Category) vh.itemView.getTag();
            etCategoryName.setText(category.getName());
            Log.e(TAG, "emoji " + category.getName());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData(mCurrentMode);
    }
}
