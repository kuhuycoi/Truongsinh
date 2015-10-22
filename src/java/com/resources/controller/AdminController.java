package com.resources.controller;

import com.resources.facade.AdminFacade;
import com.resources.facade.CustomerFacade;
import com.resources.facade.CustomerRankCustomerFacade;
import com.resources.facade.HistoryAwardFacade;
import com.resources.entity.Admin;
import com.resources.function.CustomFunction;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.utils.StringUtils;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "/Admin")
public class AdminController {

    @RequestMapping(value = {"","/Home"}, method = RequestMethod.GET)
    public ModelAndView getHomeView(ModelMap mm) {
        mm.put("HISTORY_AWARD_MAP", new HistoryAwardFacade().reportAllAwardByMonth(2015));
        mm.put("HISTORY_COMISSION_MAP", new HistoryAwardFacade().reportAllComissionByMonth(2015));
        mm.put("HISTORY_TOTAL_AWARD_YEAR", new HistoryAwardFacade().reportAllTotalAwardInCurrentYear());
        mm.put("TOTAL_IN_CURRENT_MONTH", new HistoryAwardFacade().getTotalInInCurrentMonth());
        mm.put("TOTAL_IN_CURRENT_YEAR", new HistoryAwardFacade().getTotalInInCurrentYear());
        mm.put("TOTAL_OUT_CURRENT_MONTH", new HistoryAwardFacade().getTotalOutInCurrentMonth());
        mm.put("TOTAL_OUT_CURRENT_YEAR", new HistoryAwardFacade().getTotalOutInCurrentYear());
        mm.put("TOTAL_USER_CURRENT_MONTH", new HistoryAwardFacade().countNewUserInCurrentMonth());
        mm.put("NEWEST_DEPOSIT", new CustomerRankCustomerFacade().getNewestDeposit());
        mm.put("NEWEST_AWARD", new CustomerRankCustomerFacade().getNewestAward());
        mm.put("NEWEST_USER", new CustomerFacade().getNewestUser());
        mm.put("TOP_5_DEPOSIT_MONTH", new CustomerRankCustomerFacade().getTop5DepositPVInMonth());
        mm.put("TOP_5_DEPOSIT_YEAR", new CustomerRankCustomerFacade().getTop5DepositPVInYear());
        mm.put("TOP_5_AWARD_MONTH", new HistoryAwardFacade().getTop5AwardInMonth());
        mm.put("TOP_5_AWARD_YEAR", new HistoryAwardFacade().getTop5AwardInYear());
        return new ModelAndView("admin");
    }

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public ModelAndView getLoginView(ModelMap mm) {
        return new ModelAndView("includes/admin/login");
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(@RequestBody Admin admin, ModelMap mm, HttpServletRequest request) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination messagePagination;
        if (StringUtils.isEmpty(admin.getUserName()) || StringUtils.isEmpty(admin.getPassword())) {
            messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Vui lòng điền tên đăng nhập và mật khẩu!");
            mm.put("MESSAGE_PAGINATION", messagePagination);
            return mAV;
        }
        admin.setPassword(CustomFunction.md5(admin.getPassword()));
        try {
            admin = new AdminFacade().login(admin);
            if (admin == null || admin.getIsDelete()) {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Sai tên đăng nhập hoặc mật khẩu!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
            if (!admin.getIsActive()) {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Cảnh bảo", "Tài khoản của bạn đã bị khóa!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
            request.getSession().setAttribute("ADMIN", admin);
            mm.put("REDIRECT_URL", "/Admin/Home");
            mAV = new ModelAndView(DefaultAdminPagination.REDIRECT_FOLDER + DefaultAdminPagination.REDIRECT_VIEW);
            return mAV;
        } catch (Exception e) {
            messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", messagePagination);
            return mAV;
        }
    }

    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView register(@RequestBody Admin admin, ModelMap mm, HttpServletRequest request) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        int result;
        try {
            result = new AdminFacade().create(admin);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Yêu cầu nhập tất cả các thông tin được yêu cầu!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tên đăng nhập đã tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 4: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Đăng ký thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    @RequestMapping(value = "/Delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView delete(@PathVariable Integer id, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Admin myId = (Admin) session.getAttribute("ADMIN");
        if (Objects.equals(myId.getId(), id)) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không thể thực hiện hành động này!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            new AdminFacade().delete(id);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Xóa quản trị viên thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
    @RequestMapping(value = "/Block/{id}/{status}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView block(@PathVariable Integer id,@PathVariable Boolean status, 
            ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Admin myId = (Admin) session.getAttribute("ADMIN");
        if (Objects.equals(myId.getId(), id)) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không thể thực hiện hành động này!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            new AdminFacade().block(id,status);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Khóa quản trị viên thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }

    @RequestMapping(value = "/Logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("includes/admin/login");
    }
}
