package com.edgefintrack.expense.ui.categories;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.edgefintrack.expense.R;
import com.edgefintrack.expense.entities.Category;
import com.edgefintrack.expense.utils.RealmManager;

public class AddCategoryActivity extends AppCompatActivity {
    int mCurrentMode;
    TextView ed_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        setToolbar((Toolbar) findViewById(R.id.toolbar));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCurrentMode = extras.getInt("mCurrentMode");
        }
        ed_categories = (TextView) findViewById(R.id.ed_categories);
    }

    public void onSaveCategories(View view) {
        Category category = new Category(ed_categories.getText().toString(), mCurrentMode);
        RealmManager.getInstance().save(category, Category.class);
        onBackPressed();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

}
