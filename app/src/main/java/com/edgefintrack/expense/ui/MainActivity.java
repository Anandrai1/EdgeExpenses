package com.edgefintrack.expense.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edgefintrack.expense.R;
import com.edgefintrack.expense.entities.Category;
import com.edgefintrack.expense.entities.Expense;
import com.edgefintrack.expense.interfaces.IDateMode;
import com.edgefintrack.expense.interfaces.IMainActivityListener;
import com.edgefintrack.expense.ui.expenses.ExpensesContainerFragment;
import com.edgefintrack.expense.ui.help.HelpActivity;
import com.edgefintrack.expense.ui.read_massage.ReadMassageActivity;
import com.edgefintrack.expense.ui.reminders.ReminderFragment;
import com.edgefintrack.expense.ui.settings.SettingsActivity;
import com.edgefintrack.expense.ui.statistics.StatisticsFragment;
import com.edgefintrack.expense.utils.DateUtils;
import com.edgefintrack.expense.utils.Util;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IMainActivityListener {

    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_TABS = 1;
    public static final String NAVIGATION_POSITION = "navigation_position";
    //private NavigationView mainNavigationView;
    BottomNavigationView navigation;
    int dateMode = IDateMode.MODE_TODAY;
    ArcProgress arcProgress;
    private int mCurrentMode = NAVIGATION_MODE_STANDARD;
    private int idSelectedNavigationItem;
    private DrawerLayout mainDrawerLayout;
    private Toolbar mToolbar;
    private TabLayout mainTabLayout;
    private FloatingActionButton mFloatingActionButton;
    // Expenses Summary related views
    private LinearLayout llExpensesSummary;
    private TextView tvDate;
    private TextView tvDescription;
    private TextView tvTotal;
    private TextView tv_AvailableAmount;
    private TextView tv_income_am;
    private TextView tv_expense_am;
    private List<Category> mCategoryList;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            item.setChecked(true);
            switchFragment(item.getItemId());
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCategoryList = Category.getCategoriesExpense();
        initUI();
        setUpDrawer();
        setUpToolbar();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState != null) {
            int menuItemId = savedInstanceState.getInt(NAVIGATION_POSITION);
            //navigation.setCheckedItem(menuItemId);
            navigation.getMenu().performIdentifierAction(menuItemId, 0);
        } else {
            navigation.getMenu().performIdentifierAction(R.id.nav_expenses, 0);
        }
    }


    @NavigationMode
    public int getNavigationMode() {
        return mCurrentMode;
    }

    private void initUI() {
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // mainNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_main);
        llExpensesSummary = (LinearLayout) findViewById(R.id.ll_expense_container);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        tv_AvailableAmount = (TextView) findViewById(R.id.tv_AvailableAmount);
        tv_income_am = (TextView) findViewById(R.id.tv_income_am);
        tv_expense_am = (TextView) findViewById(R.id.tv_expense_am);
        arcProgress = (ArcProgress) findViewById(R.id.arc_progress);

    }

    private void setUpDrawer() {
        //mainNavigationView.setNavigationItemSelectedListener(this);
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
       /* mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawerLayout.openDrawer(GravityCompat.START);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            return true;
        } else if (id == R.id.action_massage) {
            startActivity(new Intent(this, ReadMassageActivity.class));
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            return true;
        } else if (id == R.id.action_help) {
            startActivity(new Intent(this, HelpActivity.class));
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            return true;
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, HelpActivity.class));
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(NAVIGATION_POSITION, idSelectedNavigationItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mainDrawerLayout.closeDrawers();
        switchFragment(menuItem.getItemId());
        return false;
    }

    @Override
    public void setTabs(List<String> tabList, final TabLayout.OnTabSelectedListener onTabSelectedListener) {
        mainTabLayout.removeAllTabs();
        mainTabLayout.setVisibility(View.VISIBLE);
        mainTabLayout.setOnTabSelectedListener(onTabSelectedListener);
        for (String tab : tabList) {
            mainTabLayout.addTab(mainTabLayout.newTab().setText(tab).setTag(tab));
        }
    }

    @Override
    public void setMode(@NavigationMode int mode) {
        mFloatingActionButton.setVisibility(View.GONE);
        llExpensesSummary.setVisibility(View.GONE);
        mCurrentMode = mode;
        switch (mode) {
            case NAVIGATION_MODE_STANDARD:
                setNavigationModeStandard();
                break;
            case NAVIGATION_MODE_TABS:
                setNavigationModeTabs();
                break;
        }
    }

    @Override
    public void setExpensesSummary(@IDateMode int dateMode) {
        this.dateMode = dateMode;
        float totaltv_AvailableAmount = Expense.getAvailableAmount();
        tv_AvailableAmount.setText(Util.getFormattedCurrency(totaltv_AvailableAmount));

        float total = Expense.getTotalExpensesByDateMode(dateMode);
        tvTotal.setText(Util.getFormattedCurrency(total));

        float total_income = Expense.getIncomeByDateMode(dateMode);
        tv_income_am.setText(Util.getFormattedCurrency(total_income));

        float total_expense = Expense.getExpensesByDateMode(dateMode);
        tv_expense_am.setText(Util.getFormattedCurrency(total_expense));

        int expense_per = (int) ((total_expense * 100) / total_income);
        //arcProgress.setProgress((int) ((int) (total_expense * 100) / total_income));
        if (expense_per > 100) {
            arcProgress.setTextColor(getResources().getColor(R.color.red));
            arcProgress.setBottomText(expense_per + " % Expense");
            arcProgress.setProgress(100);
        } else {
            arcProgress.setTextColor(getResources().getColor(R.color.white));
            arcProgress.setBottomText("Expense as %");
            arcProgress.setProgress(expense_per);
        }

        String date;
        switch (dateMode) {
            case IDateMode.MODE_TODAY:
                date = Util.formatDateToString(DateUtils.getToday(), Util.getCurrentDateFormat());
                break;
            case IDateMode.MODE_WEEK:
                date = Util.formatDateToString(DateUtils.getFirstDateOfCurrentWeek(), Util.getCurrentDateFormat()).concat(" - ").concat(Util.formatDateToString(DateUtils.getRealLastDateOfCurrentWeek(), Util.getCurrentDateFormat()));
                break;
            case IDateMode.MODE_MONTH:
                date = Util.formatDateToString(DateUtils.getFirstDateOfCurrentMonth(), Util.getCurrentDateFormat()).concat(" - ").concat(Util.formatDateToString(DateUtils.getRealLastDateOfCurrentMonth(), Util.getCurrentDateFormat()));
                break;
            default:
                date = "";
                break;
        }
        tvDate.setText(date);
    }

    @Override
    public void setFAB(@DrawableRes int drawableId, View.OnClickListener onClickListener) {
        mFloatingActionButton.setImageDrawable(getResources().getDrawable(drawableId));
        mFloatingActionButton.setOnClickListener(onClickListener);
        mFloatingActionButton.show();
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setPager(ViewPager vp, final TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener) {
        mainTabLayout.setupWithViewPager(vp);
        mainTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                @IDateMode int dateMode;
                switch (tab.getPosition()) {
                    case 0:
                        dateMode = IDateMode.MODE_TODAY;
                        break;
                    case 1:
                        dateMode = IDateMode.MODE_WEEK;
                        break;
                    case 2:
                        dateMode = IDateMode.MODE_MONTH;
                        break;
                    default:
                        dateMode = IDateMode.MODE_TODAY;
                }
                setExpensesSummary(dateMode);
                viewPagerOnTabSelectedListener.onTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPagerOnTabSelectedListener.onTabUnselected(tab);
            }
        });
        setExpensesSummary(IDateMode.MODE_TODAY);
    }

    public ActionMode setActionMode(final ActionMode.Callback actionModeCallback) {
        return mToolbar.startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                return actionModeCallback.onCreateActionMode(mode, menu);
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return actionModeCallback.onPrepareActionMode(mode, menu);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return actionModeCallback.onActionItemClicked(mode, item);
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                actionModeCallback.onDestroyActionMode(mode);
            }
        });
    }

    private void setNavigationModeTabs() {
        mainTabLayout.setVisibility(View.VISIBLE);
        llExpensesSummary.setVisibility(View.VISIBLE);
    }

    private void setNavigationModeStandard() {
        CoordinatorLayout coordinator = (CoordinatorLayout) findViewById(R.id.main_coordinator);
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null && appbar != null) {
            int[] consumed = new int[2];
            behavior.onNestedPreScroll(coordinator, appbar, null, 0, -1000, consumed);
        }
        mainTabLayout.setVisibility(View.GONE);
    }

    private void switchFragment(int menuItemId) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
        switch (menuItemId) {
            case R.id.nav_expenses:
                if (!(currentFragment instanceof ExpensesContainerFragment))
                    replaceFragment(ExpensesContainerFragment.newInstance(), false);
                break;
          /*  case R.id.nav_categories:
                if (!(currentFragment instanceof CategoriesFragment))
                    replaceFragment(CategoriesFragment.newInstance(), false);
                break;*/
            case R.id.nav_statistics:
                if (!(currentFragment instanceof StatisticsFragment))
                    replaceFragment(StatisticsFragment.newInstance(), false);
                break;
            case R.id.nav_reminders:
                if (!(currentFragment instanceof ReminderFragment))
                    replaceFragment(ReminderFragment.newInstance(), false);
                break;
           /* case R.id.nav_history:
                if (!(currentFragment instanceof HistoryFragment))
                    replaceFragment(HistoryFragment.newInstance(), false);
                break;*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        float totaltv_AvailableAmount = Expense.getAvailableAmount();
        //tv_AvailableAmount.setText("Available Amount " + Util.getFormattedCurrency(totaltv_AvailableAmount));
        tv_AvailableAmount.setText(Util.getFormattedCurrency(totaltv_AvailableAmount));

        float total = Expense.getTotalExpensesByDateMode(dateMode);
        tvTotal.setText(Util.getFormattedCurrency(total));
        float total_income = Expense.getIncomeByDateMode(dateMode);
        tv_income_am.setText(Util.getFormattedCurrency(total_income));
        float total_expense = Expense.getExpensesByDateMode(dateMode);
        tv_expense_am.setText(Util.getFormattedCurrency(total_expense));
        int expense_per = (int) ((total_expense * 100) / total_income);
        //arcProgress.setProgress((int) ((int) (total_expense * 100) / total_income));
        if (expense_per > 100) {
            arcProgress.setTextColor(getResources().getColor(R.color.red));
            arcProgress.setBottomText(expense_per + " % Expense");
            arcProgress.setProgress(100);
        } else {
            arcProgress.setTextColor(getResources().getColor(R.color.white));
            arcProgress.setBottomText("Expense as %");
            arcProgress.setProgress(expense_per);
        }
    }

    @IntDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_TABS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationMode {
    }

}
