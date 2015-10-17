package com.resources.controller;

import com.resources.facade.HistoryAwardFacade;
import com.resources.bean.ExcelFile;
import com.resources.facade.HistoryPaymentFacade;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.pagination.admin.ReportPagination;
import com.resources.utils.StringUtils;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/Admin/Report")
public class AdminReportController {

    @RequestMapping(value = "/ComissionDistributor", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultComissionDistributorView(HttpSession session) {
        ReportPagination reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
        session.setAttribute("COMISSION_DISTRIBUTOR_PAGINATION", reportPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + reportPagination.getViewName());
    }

    @RequestMapping(value = "/ComissionDistributor/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForComissionDistributorView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination != null) {
            reportPagination.setDisplayPerPage(displayPerPage);
        }
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByComissionDistributorView(@PathVariable("orderBy") String orderBy, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination != null) {
            if (reportPagination.getOrderColmn().equals(orderBy)) {
                reportPagination.setAsc(!reportPagination.isAsc());
            }
            reportPagination.setOrderColmn(orderBy);
        }
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoComissionDistributorView(@PathVariable("page") int page, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination != null) {
            reportPagination.setCurrentPage(page);
        }
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/ChangeDay/{day}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDayComissionDistributorView(@PathVariable("day") int day, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
        }
        reportPagination.setDay(day);
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/ChangeMonth/{month}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeMonthComissionDistributorView(@PathVariable("month") int month, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
        }
        reportPagination.setMonth(month);
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/ChangeYear/{year}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeYearComissionDistributorView(@PathVariable("year") int year, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
        }
        reportPagination.setYear(year);
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchComissionDistributorView(@RequestBody Map map, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
        }
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return comissionDistributorView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        reportPagination.setSearchString(searchString);
        reportPagination.setKeywords(keywords);
        return comissionDistributorView(reportPagination, session);
    }

    private ModelAndView comissionDistributorView(ReportPagination reportPagination, HttpSession session) {
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
        }
        new HistoryAwardFacade().pageData(reportPagination);
        session.setAttribute("COMISSION_DISTRIBUTOR_PAGINATION", reportPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + reportPagination.getViewName());
    }

    @RequestMapping(value = "/ComissionDistributor/Export", method = RequestMethod.GET)
    public ModelAndView exportComissionDistributorView(HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
        }
        ExcelFile file = new ExcelFile();
        new HistoryAwardFacade().setExportComissionDistributorFile(file, reportPagination.getMonth(), reportPagination.getYear());
        return new ModelAndView("ExcelView", "myModel", file);
    }

    @RequestMapping(value = "/ComissionDistributor/CalcSalary", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView calcSalary(ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        int result;
        try {
            result = new HistoryAwardFacade().calcSalary();
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 0: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_INFO, "Thông báo", "Không có NPP nào được tính lương!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thông báo", "Đã tính lương cho " + result + " NPP!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    //Trian
    @RequestMapping(value = "/Trian", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultTrianView(HttpSession session) {
        ReportPagination reportPagination = new ReportPagination("Thống kê tri ân", "trueVT", true, "/Trian", "/report_trian");
        session.setAttribute("TRIAN_PAGINATION", reportPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + reportPagination.getViewName());
    }

    @RequestMapping(value = "/Trian/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForTrianView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("TRIAN_PAGINATION");
        if (reportPagination != null) {
            reportPagination.setDisplayPerPage(displayPerPage);
        }
        return trianView(reportPagination, session);
    }

    @RequestMapping(value = "/Trian/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByTrianView(@PathVariable("orderBy") String orderBy, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("TRIAN_PAGINATION");
        if (reportPagination != null) {
            if (reportPagination.getOrderColmn().equals(orderBy)) {
                reportPagination.setAsc(!reportPagination.isAsc());
            }
            reportPagination.setOrderColmn(orderBy);
        }
        return trianView(reportPagination, session);
    }

    @RequestMapping(value = "/Trian/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoTrianView(@PathVariable("page") int page, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("TRIAN_PAGINATION");
        if (reportPagination != null) {
            reportPagination.setCurrentPage(page);
        }
        return trianView(reportPagination, session);
    }

    @RequestMapping(value = "/Trian/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchTrianView(@RequestBody Map map, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("TRIAN_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê tri ân", "trueVT", true, "/Trian", "/report_trian");
        }
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return trianView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        reportPagination.setSearchString(searchString);
        reportPagination.setKeywords(keywords);
        return trianView(reportPagination, session);
    }

    private ModelAndView trianView(ReportPagination reportPagination, HttpSession session) {
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê tri ân", "trueVT", true, "/Trian", "/report_trian");
        }
        new HistoryAwardFacade().pageDataTrian(reportPagination);
        session.setAttribute("TRIAN_PAGINATION", reportPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + reportPagination.getViewName());
    }

    @RequestMapping(value = "/Trian/Export", method = RequestMethod.GET)
    public ModelAndView exportTrianView(HttpSession session) {
        ExcelFile file = new ExcelFile();
        new HistoryAwardFacade().setExportTrianFile(file);
        return new ModelAndView("ExcelView", "myModel", file);
    }

    //Payment
    @RequestMapping(value = "/Payment", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultPaymentView(HttpSession session) {
        ReportPagination reportPagination = new ReportPagination("Danh sách thanh toán hoa hồng NPP", "datetimeCreated", false, "/Payment", "/report_payment");
        reportPagination.setEditViewName("/report_payment_edit_modal");
        session.setAttribute("PAYMENT_PAGINATION", reportPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + reportPagination.getViewName());
    }

    @RequestMapping(value = "/Payment/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForPaymentView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("PAYMENT_PAGINATION");
        if (reportPagination != null) {
            reportPagination.setDisplayPerPage(displayPerPage);
        }
        return paymentView(reportPagination, session);
    }

    @RequestMapping(value = "/Payment/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByPaymentView(@PathVariable("orderBy") String orderBy, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("PAYMENT_PAGINATION");
        if (reportPagination != null) {
            if (reportPagination.getOrderColmn().equals(orderBy)) {
                reportPagination.setAsc(!reportPagination.isAsc());
            }
            reportPagination.setOrderColmn(orderBy);
        }
        return paymentView(reportPagination, session);
    }

    @RequestMapping(value = "/Payment/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoPaymentView(@PathVariable("page") int page, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("PAYMENT_PAGINATION");
        if (reportPagination != null) {
            reportPagination.setCurrentPage(page);
        }
        return paymentView(reportPagination, session);
    }

    @RequestMapping(value = "/Payment/ChangeDay/{day}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDayPaymentView(@PathVariable("day") int day, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("PAYMENT_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Danh sách thanh toán hoa hồng NPP", "datetimeCreated", false, "/Payment", "/report_payment");
            reportPagination.setEditViewName("/report_payment_edit_modal");
        }
        reportPagination.setDay(day);
        return paymentView(reportPagination, session);
    }

    @RequestMapping(value = "/Payment/ChangeMonth/{month}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeMonthPaymentView(@PathVariable("month") int month, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("PAYMENT_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Danh sách thanh toán hoa hồng NPP", "datetimeCreated", false, "/Payment", "/report_payment");
            reportPagination.setEditViewName("/report_payment_edit_modal");
        }
        reportPagination.setMonth(month);
        return paymentView(reportPagination, session);
    }

    @RequestMapping(value = "/Payment/ChangeYear/{year}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeYearPaymentView(@PathVariable("year") int year, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("PAYMENT_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Danh sách thanh toán hoa hồng NPP", "datetimeCreated", false, "/Payment", "/report_payment");
            reportPagination.setEditViewName("/report_payment_edit_modal");
        }
        reportPagination.setYear(year);
        return paymentView(reportPagination, session);
    }

    @RequestMapping(value = "/Payment/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchPaymentView(@RequestBody Map map, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("PAYMENT_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Danh sách thanh toán hoa hồng NPP", "datetimeCreated", false, "/Payment", "/report_payment");
            reportPagination.setEditViewName("/report_payment_edit_modal");
        }
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return paymentView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        reportPagination.setSearchString(searchString);
        reportPagination.setKeywords(keywords);
        return paymentView(reportPagination, session);
    }

    private ModelAndView paymentView(ReportPagination reportPagination, HttpSession session) {
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Danh sách thanh toán hoa hồng NPP", "datetimeCreated", false, "/Payment", "/report_payment");
        }
        new HistoryPaymentFacade().pageDataPayment(reportPagination);
        session.setAttribute("PAYMENT_PAGINATION", reportPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + reportPagination.getViewName());
    }

    @RequestMapping(value = "/Payment/ViewEdit", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getViewEdit(HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("PAYMENT_PAGINATION");
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + reportPagination.getEditViewName());
    }

    @RequestMapping(value = "/Payment/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView finishPayment(@RequestBody Map map, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(MessagePagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination messagePagination;
        Integer result = 0;
        try {
            result = new HistoryPaymentFacade().edit(map);
        } catch (Exception e) {
            messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", messagePagination);
            return mAV;
        }

        switch (result) {
            case 1: {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Kết toán thành công!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
            default: {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
        }
    }
}
