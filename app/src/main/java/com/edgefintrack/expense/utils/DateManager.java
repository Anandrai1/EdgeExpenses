package com.edgefintrack.expense.utils;

import java.util.Date;


public class DateManager {

    private static DateManager ourInstance = new DateManager();
    private Date mDateFrom;
    private Date mDateTo;

    private DateManager() {
        mDateFrom = DateUtils.getFirstDateOfCurrentMonth();
        mDateTo = DateUtils.getLastDateOfCurrentMonth();
    }

    public static DateManager getInstance() {
        return ourInstance;
    }

    public Date getDateFrom() {
        return mDateFrom;
    }

    public void setDateFrom(Date mDateFrom) {
        this.mDateFrom = mDateFrom;
    }

    public Date getDateTo() {
        return mDateTo;
    }

    public void setDateTo(Date mDateTo) {
        this.mDateTo = mDateTo;
    }

}
