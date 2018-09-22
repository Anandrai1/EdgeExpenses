package com.edgefintrack.expense.ui.reminders;

import android.os.Bundle;

import com.edgefintrack.expense.R;
import com.edgefintrack.expense.interfaces.IUserActionsMode;
import com.edgefintrack.expense.ui.BaseActivity;

public class NewReminderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        @IUserActionsMode int mode = getIntent().getIntExtra(IUserActionsMode.MODE_TAG, IUserActionsMode.MODE_CREATE);
        String reminderId = getIntent().getStringExtra(NewReminderFragment.REMINDER_ID_KEY);
        replaceFragment(NewReminderFragment.newInstance(mode, reminderId), false);
    }

}
