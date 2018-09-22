package com.edgefintrack.expense.ui;

import android.content.Context;

import com.edgefintrack.expense.interfaces.IMainActivityListener;


public class MainFragment extends BaseFragment {

    protected IMainActivityListener mMainActivityListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivityListener = (IMainActivityListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainActivityListener = null;
    }

}
