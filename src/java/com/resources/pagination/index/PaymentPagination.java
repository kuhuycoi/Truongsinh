package com.resources.pagination.index;

import java.util.Calendar;

public class PaymentPagination extends DefaultIndexPagination {

    private String grandController;
    private final static String CHANGE_MONTH = "/ChangeMonth";
    private final static String CHANGE_YEAR = "/ChangeYear";
    private Integer month=-1;
    private Integer year=-1;

    public PaymentPagination(String viewTitle,String viewName) {
        setViewTitle(viewTitle);
        setOrderColmn("datetimeCreated");
        setAsc(false);
        setViewName(viewName);
        setInsertViewName("/payment_insert");
        setChildrenController("/Payment");
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
    }

    public String getCHANGE_MONTH() {
        return CHANGE_MONTH;
    }

    public String getCHANGE_YEAR() {
        return CHANGE_YEAR;
    }

    public String getGrandController() {
        return grandController;
    }

    public void setGrandController(String grandController) {
        this.grandController = grandController;
    }
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
