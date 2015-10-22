package com.resources.pagination.admin;

import java.util.Calendar;

public class ReportPagination extends DefaultAdminPagination {

    private String grandController;
    private Integer month=-1;
    private Integer year=-1;
    private Integer day=-1;
    private final static String CHANGE_DAY = "/ChangeDay";
    private final static String CHANGE_MONTH = "/ChangeMonth";
    private final static String CHANGE_YEAR = "/ChangeYear";
    private final static String EXPORT = "/Export";
    private final static String CALC_SALARY = "/CalcSalary";

    public ReportPagination(String viewTitle, String orderColumn, boolean asc, String grandController, String viewName) {
        setViewTitle(viewTitle);
        setOrderColmn(orderColumn);
        setAsc(asc);
        setViewName(viewName);
        setChildrenController("/Report");
        this.grandController = grandController;
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
    }

    public String getEXPORT() {
        return EXPORT;
    }

    public String getCALC_SALARY() {
        return CALC_SALARY;
    }
    public String getCHANGE_MONTH() {
        return CHANGE_MONTH;
    }

    public String getCHANGE_YEAR() {
        return CHANGE_YEAR;
    }

    public String getCHANGE_DAY() {
        return CHANGE_DAY;
    }

    public String getGrandController() {
        return grandController;
    }

    public void setGrandController(String grandController) {
        this.grandController = grandController;
    }

    public Integer getDay() {
        return day;
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
