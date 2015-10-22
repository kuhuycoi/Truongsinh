package com.resources.controller;

import com.resources.bean.ExcelFile;
import com.resources.entity.StudyPromotion;
import com.resources.facade.CheckAwardsFacade;
import com.resources.facade.CustomerFacade;
import com.resources.facade.CustomerRankCustomerFacade;
import com.resources.facade.HistoryAwardFacade;
import com.resources.facade.PinSysFacade;
import com.resources.facade.RankCustomersFacade;
import com.resources.facade.StudyPromotionFacade;
import com.resources.pagination.admin.HistoryPagination;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/Admin/History")
public class AdminHistoryController {

    //CustomerRankCustomer    
    @RequestMapping(value = "/CustomerRankCustomer", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultCustomerRankCustomerView(ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        session.setAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + customerRankCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/CustomerRankCustomer/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForCustomerRankCustomerView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination != null) {
            customerRankCustomerPagination.setDisplayPerPage(displayPerPage);
        }
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByCustomerRankCustomerView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination != null) {
            if (customerRankCustomerPagination.getOrderColmn().equals(orderBy)) {
                customerRankCustomerPagination.setAsc(!customerRankCustomerPagination.isAsc());
            }
            customerRankCustomerPagination.setOrderColmn(orderBy);
        }
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoCustomerRankCustomerView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination != null) {
            customerRankCustomerPagination.setCurrentPage(page);
        }
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchDistributorView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return customerRankCustomerView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        customerRankCustomerPagination.setSearchString(searchString);
        customerRankCustomerPagination.setKeywords(keywords);
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    private ModelAndView customerRankCustomerView(HistoryPagination customerRankCustomerPagination, HttpSession session) {
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        }
        new CustomerRankCustomerFacade().pageData(customerRankCustomerPagination);
        session.setAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + customerRankCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/CustomerRankCustomer/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getCustomerRankCustomerViewInsert(HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + customerRankCustomerPagination.getInsertViewName());
    }

    @RequestMapping(value = "/CustomerRankCustomer/ViewInsert/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getCustomerRankCustomerViewInsert(@PathVariable(value = "userName") String userName, ModelMap mm, HttpSession session) {
        mm.put("USERNAME", userName);
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + customerRankCustomerPagination.getInsertViewName());
    }

    @RequestMapping(value = "/CustomerRankCustomer/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertCustomerRankCustomer(@RequestParam(value = "userName") String userName,
            @RequestParam(value = "rankCustomerId") Integer rankCustomerId,
            @RequestParam(value = "multipleGrateful", required = false) Integer multipleGrateful,
            ModelMap mm, HttpSession session) {
        System.out.println(rankCustomerId + " - " + multipleGrateful + " - " + userName);
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new CustomerRankCustomerFacade().depositPv(userName, rankCustomerId, multipleGrateful);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tài khoản đã từng nạp gói PV này!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Nạp PV thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Yêu cầu nạp gói 300PV trước!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    //Award  
    @RequestMapping(value = "/Award", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultAwardView(HttpSession session) {
        HistoryPagination awardPagination = new HistoryPagination("Lịch sử thưởng", "/Award", "/history_award");
        session.setAttribute("HISTORY_AWARD_PAGINATION", awardPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + awardPagination.getViewName());
    }

    @RequestMapping(value = "/Award/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForAwardView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        HistoryPagination awardPagination = (HistoryPagination) session.getAttribute("HISTORY_AWARD_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setDisplayPerPage(displayPerPage);
        }
        return awardView(awardPagination, session);
    }

    @RequestMapping(value = "/Award/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByAwardView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination awardPagination = (HistoryPagination) session.getAttribute("HISTORY_AWARD_PAGINATION");
        if (awardPagination != null) {
            if (awardPagination.getOrderColmn().equals(orderBy)) {
                awardPagination.setAsc(!awardPagination.isAsc());
            }
            awardPagination.setOrderColmn(orderBy);
        }
        return awardView(awardPagination, session);
    }

    @RequestMapping(value = "/Award/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoAwardView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination awardPagination = (HistoryPagination) session.getAttribute("HISTORY_AWARD_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setCurrentPage(page);
        }
        return awardView(awardPagination, session);
    }

    @RequestMapping(value = "/Award/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchAwardView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return awardView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination awardPagination = new HistoryPagination("Lịch sử thưởng", "/Award", "/history_award");
        awardPagination.setSearchString(searchString);
        awardPagination.setKeywords(keywords);
        return awardView(awardPagination, session);
    }

    private ModelAndView awardView(HistoryPagination awardPagination, HttpSession session) {
        if (awardPagination == null) {
            awardPagination = new HistoryPagination("Lịch sử thưởng", "/Award", "/history_award");
        }
        new HistoryAwardFacade().pageData(awardPagination);
        session.setAttribute("HISTORY_AWARD_PAGINATION", awardPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + awardPagination.getViewName());
    }

    @RequestMapping(value = "/Award/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getAwardViewInsert(HttpSession session) {
        HistoryPagination awardPagination = (HistoryPagination) session.getAttribute("HISTORY_AWARD_PAGINATION");
        if (awardPagination == null) {
            awardPagination = new HistoryPagination("Lịch sử thưởng", "/Award", "/history_award");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + awardPagination.getInsertViewName());
    }

    // Never up rank
    @RequestMapping(value = "/NeverUpRank", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultNeverUpRankView(HttpSession session) {
        HistoryPagination neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        session.setAttribute("HISTORY_NEVER_UP_RANK_PAGINATION", neverUpRankPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + neverUpRankPagination.getViewName());
    }

    @RequestMapping(value = "/NeverUpRank/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForNeverUpRankView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("HISTORY_NEVER_UP_RANK_PAGINATION");
        if (neverUpRankPagination != null) {
            neverUpRankPagination.setDisplayPerPage(displayPerPage);

        }
        return neverUpRankView(neverUpRankPagination, session);
    }

    @RequestMapping(value = "/NeverUpRank/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByNeverUpRankView(@PathVariable("orderBy") String orderBy, HttpSession session) {
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("HISTORY_NEVER_UP_RANK_PAGINATION");
        if (neverUpRankPagination != null) {
            if (neverUpRankPagination.getOrderColmn().equals(orderBy)) {
                neverUpRankPagination.setAsc(!neverUpRankPagination.isAsc());
            }
            neverUpRankPagination.setOrderColmn(orderBy);
        }
        return neverUpRankView(neverUpRankPagination, session);
    }

    @RequestMapping(value = "/NeverUpRank/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoNeverUpRankView(@PathVariable("page") int page, HttpSession session) {
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("HISTORY_NEVER_UP_RANK_PAGINATION");
        if (neverUpRankPagination != null) {
            neverUpRankPagination.setCurrentPage(page);
        }
        return neverUpRankView(neverUpRankPagination, session);
    }

    @RequestMapping(value = "/NeverUpRank/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchNeverUpRankView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return neverUpRankView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        neverUpRankPagination.setSearchString(searchString);
        neverUpRankPagination.setKeywords(keywords);
        return neverUpRankView(neverUpRankPagination, session);
    }

    private ModelAndView neverUpRankView(HistoryPagination neverUpRankPagination, HttpSession session) {
        if (neverUpRankPagination == null) {
            neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        }
        new CustomerFacade().pageDataNeverUpRank(neverUpRankPagination);
        session.setAttribute("HISTORY_NEVER_UP_RANK_PAGINATION", neverUpRankPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + neverUpRankPagination.getViewName());
    }

    @RequestMapping(value = "/NeverUpRank/ViewInsert/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getCustomerRankCustomerViewInsert1(@PathVariable(value = "userName") String userName, ModelMap mm, HttpSession session) {
        mm.put("USERNAME", userName);
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (neverUpRankPagination == null) {
            neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + neverUpRankPagination.getInsertViewName());
    }

    @RequestMapping(value = "/NeverUpRank/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getNeverUpRankViewInsert(HttpSession session) {
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (neverUpRankPagination == null) {
            neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + neverUpRankPagination.getInsertViewName());
    }

    // Rank Customer
    @RequestMapping(value = "/RankCustomer", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultRankCustomerView(ModelMap mm, HttpSession session) {
        HistoryPagination rankCustomerPagniation = new HistoryPagination("Danh sách gói PV", "price", true, "/RankCustomer", "/history_rank_customer");
        session.setAttribute("RANK_CUSTOMER_PAGINATION", rankCustomerPagniation);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + rankCustomerPagniation.getViewName());
    }

    @RequestMapping(value = "/RankCustomer/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForRankCustomerView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("RANK_CUSTOMER_PAGINATION");
        if (rankCustomerPagniation != null) {
            rankCustomerPagniation.setDisplayPerPage(displayPerPage);

        }
        return rankCustomerView(rankCustomerPagniation, session);
    }

    @RequestMapping(value = "/RankCustomer/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByRankCustomerView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("RANK_CUSTOMER_PAGINATION");
        if (rankCustomerPagniation != null) {
            if (rankCustomerPagniation.getOrderColmn().equals(orderBy)) {
                rankCustomerPagniation.setAsc(!rankCustomerPagniation.isAsc());
            }
            rankCustomerPagniation.setOrderColmn(orderBy);
        }
        return rankCustomerView(rankCustomerPagniation, session);
    }

    @RequestMapping(value = "/RankCustomer/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoRankCustomerView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("RANK_CUSTOMER_PAGINATION");
        if (rankCustomerPagniation != null) {
            rankCustomerPagniation.setCurrentPage(page);
        }
        return rankCustomerView(rankCustomerPagniation, session);
    }

    @RequestMapping(value = "/RankCustomer/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchRankCustomerView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return rankCustomerView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination rankCustomerPagniation = new HistoryPagination("Danh sách gói PV", "price", true, "/RankCustomer", "/history_rank_customer");
        rankCustomerPagniation.setSearchString(searchString);
        rankCustomerPagniation.setKeywords(keywords);
        return rankCustomerView(rankCustomerPagniation, session);
    }

    private ModelAndView rankCustomerView(HistoryPagination rankCustomerPagniation, HttpSession session) {
        if (rankCustomerPagniation == null) {
            rankCustomerPagniation = new HistoryPagination("Danh sách gói PV", "price", true, "/RankCustomer", "/history_rank_customer");
        }
        new RankCustomersFacade().pageData(rankCustomerPagniation);
        session.setAttribute("RANK_CUSTOMER_PAGINATION", rankCustomerPagniation);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + rankCustomerPagniation.getViewName());
    }

    @RequestMapping(value = "/RankCustomer/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRankCustomerViewInsert(HttpSession session) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("RANK_CUSTOMER_PAGINATION");
        if (rankCustomerPagniation == null) {
            rankCustomerPagniation = new HistoryPagination("Danh sách gói PV", "id", false, "/RankCustomer", "/history_rank_customer");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + rankCustomerPagniation.getInsertViewName());
    }

    //CheckAward
    @RequestMapping(value = "/CheckAward", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultCheckAwardView(ModelMap mm, HttpSession session) {
        HistoryPagination checkAwardPagniation = new HistoryPagination("Danh sách các gói thưởng", "id", true, "/CheckAward", "/history_check_award");
        session.setAttribute("CHECK_AWARD_PAGINATION", checkAwardPagniation);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + checkAwardPagniation.getViewName());
    }

    @RequestMapping(value = "/CheckAward/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForCheckAwardView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination checkAwardPagniation = (HistoryPagination) session.getAttribute("CHECK_AWARD_PAGINATION");
        if (checkAwardPagniation != null) {
            checkAwardPagniation.setDisplayPerPage(displayPerPage);

        }
        return checkAwardView(checkAwardPagniation, session);
    }

    @RequestMapping(value = "/CheckAward/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByCheckAwardView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination checkAwardPagniation = (HistoryPagination) session.getAttribute("CHECK_AWARD_PAGINATION");
        if (checkAwardPagniation != null) {
            if (checkAwardPagniation.getOrderColmn().equals(orderBy)) {
                checkAwardPagniation.setAsc(!checkAwardPagniation.isAsc());
            }
            checkAwardPagniation.setOrderColmn(orderBy);
        }
        return checkAwardView(checkAwardPagniation, session);
    }

    @RequestMapping(value = "/CheckAward/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoCheckAwardView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination checkAwardPagniation = (HistoryPagination) session.getAttribute("CHECK_AWARD_PAGINATION");
        if (checkAwardPagniation != null) {
            checkAwardPagniation.setCurrentPage(page);
        }
        return checkAwardView(checkAwardPagniation, session);
    }

    @RequestMapping(value = "/CheckAward/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchCheckAwardView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return checkAwardView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination checkAwardPagniation = new HistoryPagination("Danh sách các gói thưởng", "id", true, "/CheckAward", "/history_check_award");
        checkAwardPagniation.setSearchString(searchString);
        checkAwardPagniation.setKeywords(keywords);
        return checkAwardView(checkAwardPagniation, session);
    }

    private ModelAndView checkAwardView(HistoryPagination checkAwardPagniation, HttpSession session) {
        if (checkAwardPagniation == null) {
            checkAwardPagniation = new HistoryPagination("Danh sách các gói thưởng", "id", true, "/CheckAward", "/history_check_award");
        }
        new CheckAwardsFacade().pageData(checkAwardPagniation);
        session.setAttribute("CHECK_AWARD_PAGINATION", checkAwardPagniation);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + checkAwardPagniation.getViewName());
    }

    //Not used Pinsys
    @RequestMapping(value = "/NotUsedPinSys", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultPinSysView(ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = new HistoryPagination("Danh sách mã PIN chưa sử dụng", "createdDate", false, "/NotUsedPinSys", "/history_not_used_pin_sys", "/history_insert_pin_sys_modal");
        session.setAttribute("NOT_USED_PIN_SYS_PAGINATION", pinSysPagniation);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pinSysPagniation.getViewName());
    }

    @RequestMapping(value = "/NotUsedPinSys/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForPinSysView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("NOT_USED_PIN_SYS_PAGINATION");
        if (pinSysPagniation != null) {
            pinSysPagniation.setDisplayPerPage(displayPerPage);

        }
        return notUsedPinSysView(pinSysPagniation, session);
    }

    @RequestMapping(value = "/NotUsedPinSys/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByPinSysView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("NOT_USED_PIN_SYS_PAGINATION");
        if (pinSysPagniation != null) {
            if (pinSysPagniation.getOrderColmn().equals(orderBy)) {
                pinSysPagniation.setAsc(!pinSysPagniation.isAsc());
            }
            pinSysPagniation.setOrderColmn(orderBy);
        }
        return notUsedPinSysView(pinSysPagniation, session);
    }

    @RequestMapping(value = "/NotUsedPinSys/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoPinSysView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("NOT_USED_PIN_SYS_PAGINATION");
        if (pinSysPagniation != null) {
            pinSysPagniation.setCurrentPage(page);
        }
        return notUsedPinSysView(pinSysPagniation, session);
    }

    @RequestMapping(value = "/NotUsedPinSys/ChangeDate/{day}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoUsedPinSysView(@PathVariable("day") Long day, ModelMap mm, HttpSession session) {
        HistoryPagination pinsysPagination = (HistoryPagination) session.getAttribute("NOT_USED_PIN_SYS_PAGINATION");
        if (pinsysPagination != null) {
            if (day == -1) {
                pinsysPagination.setDay(null);
            } else {
                pinsysPagination.setDay(new Date(day));
            }
        }
        return notUsedPinSysView(pinsysPagination, session);
    }

    @RequestMapping(value = "/NotUsedPinSys/ChangePinType/{pinType}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoUsedPinSysView(@PathVariable("pinType") Integer pinType, ModelMap mm, HttpSession session) {
        HistoryPagination pinsysPagination = (HistoryPagination) session.getAttribute("NOT_USED_PIN_SYS_PAGINATION");
        if (pinsysPagination != null) {
            if (pinType == -1) {
                pinsysPagination.setPinType(null);
            } else {
                pinsysPagination.setPinType(pinType);
            }
        }
        return notUsedPinSysView(pinsysPagination, session);
    }

    @RequestMapping(value = "/NotUsedPinSys/Export", method = RequestMethod.GET)
    public ModelAndView exportView(HttpSession session) {
        HistoryPagination pinsysPagination = (HistoryPagination) session.getAttribute("NOT_USED_PIN_SYS_PAGINATION");
        ExcelFile file = new ExcelFile();
        Long day = null;
        Integer pinType = null;
        if (pinsysPagination != null && pinsysPagination.getDay() != null) {
            day = pinsysPagination.getDay().getTime();
        }
        if (pinsysPagination != null) {
            pinType = pinsysPagination.getPinType();
        }
        new PinSysFacade().setExportFile(file, day, pinType);
        return new ModelAndView("ExcelView", "myModel", file);
    }

    @RequestMapping(value = "/NotUsedPinSys/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchNotUsedPinSysView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return notUsedPinSysView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination pinSysPagniation = new HistoryPagination("Danh sách mã PIN chưa sử dụng", "createdDate", false, "/NotUsedPinSys", "/history_not_used_pin_sys", "/history_insert_pin_sys_modal");
        pinSysPagniation.setSearchString(searchString);
        pinSysPagniation.setKeywords(keywords);
        return notUsedPinSysView(pinSysPagniation, session);
    }

    private ModelAndView notUsedPinSysView(HistoryPagination pinSysPagniation, HttpSession session) {
        if (pinSysPagniation == null) {
            pinSysPagniation = new HistoryPagination("Danh sách mã PIN chưa sử dụng", "createdDate", false, "/NotUsedPinSys", "/history_not_used_pin_sys", "/history_insert_pin_sys_modal");
        }
        new PinSysFacade().pageDataNotUsed(pinSysPagniation);
        session.setAttribute("NOT_USED_PIN_SYS_PAGINATION", pinSysPagniation);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pinSysPagniation.getViewName());
    }

    @RequestMapping(value = "/NotUsedPinSys/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getNotUsedPinSysViewInsert(HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("NOT_USED_PIN_SYS_PAGINATION");
        if (pinSysPagniation == null) {
            pinSysPagniation = new HistoryPagination("Danh sách mã PIN chưa sử dụng", "createdDate", false, "/NotUsedPinSys", "/history_not_used_pin_sys", "/history_insert_pin_sys_modal");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pinSysPagniation.getInsertViewName());
    }

    @RequestMapping(value = "/NotUsedPinSys/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertPinSys(@RequestParam(value = "rankCustomerId") Integer rankCustomerId,
            @RequestParam(value = "count") Integer count, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new PinSysFacade().insertPinSys(count, rankCustomerId, (Integer) session.getAttribute("ADMIN_ID"));
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Đã thêm mới " + count + " mã PIN!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    //Used pin sys
    @RequestMapping(value = "/UsedPinSys", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultUsedPinSysView(ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = new HistoryPagination("Lịch sử nạp mã pin", "usedDate", false, "/UsedPinSys", "/history_used_pin_sys", "/history_insert_pin_sys_modal");
        session.setAttribute("USED_PIN_SYS_PAGINATION", pinSysPagniation);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pinSysPagniation.getViewName());
    }

    @RequestMapping(value = "/UsedPinSys/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForUsedPinSysView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("USED_PIN_SYS_PAGINATION");
        if (pinSysPagniation != null) {
            pinSysPagniation.setDisplayPerPage(displayPerPage);

        }
        return usedPinSysView(pinSysPagniation, session);
    }

    @RequestMapping(value = "/UsedPinSys/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByUsedPinSysView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("USED_PIN_SYS_PAGINATION");
        if (pinSysPagniation != null) {
            if (pinSysPagniation.getOrderColmn().equals(orderBy)) {
                pinSysPagniation.setAsc(!pinSysPagniation.isAsc());
            }
            pinSysPagniation.setOrderColmn(orderBy);
        }
        return usedPinSysView(pinSysPagniation, session);
    }

    @RequestMapping(value = "/UsedPinSys/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoUsedPinSysView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("USED_PIN_SYS_PAGINATION");
        if (pinSysPagniation != null) {
            pinSysPagniation.setCurrentPage(page);
        }
        return usedPinSysView(pinSysPagniation, session);
    }

    @RequestMapping(value = "/UsedPinSys/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchUsedPinSysView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return usedPinSysView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination pinSysPagniation = new HistoryPagination("Lịch sử nạp mã pin", "usedDate", false, "/UsedPinSys", "/history_used_pin_sys", "/history_insert_pin_sys_modal");
        pinSysPagniation.setSearchString(searchString);
        pinSysPagniation.setKeywords(keywords);
        return usedPinSysView(pinSysPagniation, session);
    }

    private ModelAndView usedPinSysView(HistoryPagination pinSysPagniation, HttpSession session) {
        if (pinSysPagniation == null) {
            pinSysPagniation = new HistoryPagination("Lịch sử nạp mã pin", "usedDate", false, "/UsedPinSys", "/history_used_pin_sys", "/history_insert_pin_sys_modal");
        }
        new PinSysFacade().pageDataUsedPinSys(pinSysPagniation);
        session.setAttribute("USED_PIN_SYS_PAGINATION", pinSysPagniation);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pinSysPagniation.getViewName());
    }

    //Study Promotion   
    @RequestMapping(value = "/StudyPromotion", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultStudyPromotionView(ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = new HistoryPagination("Quỹ khuyến học", "id", true, "/StudyPromotion", "/history_study_promotion", "/history_insert_study_promotion_modal");
        session.setAttribute("STUDY_PROMOTION_PAGINATION", pinSysPagniation);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pinSysPagniation.getViewName());
    }

    @RequestMapping(value = "/StudyPromotion/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForStudyPromotionView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("STUDY_PROMOTION_PAGINATION");
        if (pinSysPagniation != null) {
            pinSysPagniation.setDisplayPerPage(displayPerPage);

        }
        return studyPromotionView(pinSysPagniation, session);
    }

    @RequestMapping(value = "/StudyPromotion/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByStudyPromotionView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("STUDY_PROMOTION_PAGINATION");
        if (pinSysPagniation != null) {
            if (pinSysPagniation.getOrderColmn().equals(orderBy)) {
                pinSysPagniation.setAsc(!pinSysPagniation.isAsc());
            }
            pinSysPagniation.setOrderColmn(orderBy);
        }
        return studyPromotionView(pinSysPagniation, session);
    }

    @RequestMapping(value = "/StudyPromotion/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoStudyPromotionView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination pinSysPagniation = (HistoryPagination) session.getAttribute("STUDY_PROMOTION_PAGINATION");
        if (pinSysPagniation != null) {
            pinSysPagniation.setCurrentPage(page);
        }
        return studyPromotionView(pinSysPagniation, session);
    }

    @RequestMapping(value = "/StudyPromotion/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchStudyPromotionView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return studyPromotionView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination pinSysPagniation = new HistoryPagination("Quỹ khuyến học", "id", true, "/StudyPromotion", "/history_study_promotion", "/history_insert_study_promotion_modal");
        pinSysPagniation.setSearchString(searchString);
        pinSysPagniation.setKeywords(keywords);
        return studyPromotionView(pinSysPagniation, session);
    }

    private ModelAndView studyPromotionView(HistoryPagination pinSysPagniation, HttpSession session) {
        if (pinSysPagniation == null) {
            pinSysPagniation = new HistoryPagination("Quỹ khuyến học", "id", true, "/StudyPromotion", "/history_study_promotion", "/history_insert_study_promotion_modal");
        }
        new StudyPromotionFacade().pageData(pinSysPagniation);
        session.setAttribute("STUDY_PROMOTION_PAGINATION", pinSysPagniation);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pinSysPagniation.getViewName());
    }

    @RequestMapping(value = "/StudyPromotion/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getStudyPromotionViewInsert(HttpSession session) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("STUDY_PROMOTION_PAGINATION");
        if (rankCustomerPagniation == null) {
            rankCustomerPagniation = new HistoryPagination("Quỹ khuyến học", "id", true, "/StudyPromotion", "/history_study_promotion", "/history_insert_study_promotion_modal");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + rankCustomerPagniation.getInsertViewName());
    }

    @RequestMapping(value = "/StudyPromotion/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertStudyPromotion(@RequestParam(value = "idStart") Integer idStart,
            @RequestParam(value = "idEnd") Integer idEnd,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "totalMoney") BigDecimal totalMoney,
            @RequestParam(value = "moneypercircle") BigDecimal moneypercircle, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new StudyPromotionFacade().insertMemberPromotion(idStart, idEnd, name, moneypercircle, totalMoney);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Đã thêm mới chương trình thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Không tồn tại người bắt đầu hoặc kết thúc!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "ID bắt đầu phải bé hơn ID kết thúc");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    @RequestMapping(value = "/StudyPromotion/ViewEdit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getStudyPromotionViewEdit(@PathVariable(value = "id") Integer id, HttpSession session, ModelMap mm) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("STUDY_PROMOTION_PAGINATION");
        if (rankCustomerPagniation == null) {
            rankCustomerPagniation = new HistoryPagination("Quỹ khuyến học", "id", true, "/StudyPromotion", "/history_study_promotion", "/history_insert_study_promotion_modal");
        }
        rankCustomerPagniation.setEditViewName("/history_edit_study_promotion_modal");
        mm.put("STUDY_PROMOTION", new StudyPromotionFacade().find(id));
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + rankCustomerPagniation.getEditViewName());
    }

    @RequestMapping(value = "/StudyPromotion/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView EditStudyPromotion(@RequestBody StudyPromotion studyPromotion, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            new StudyPromotionFacade().edit(studyPromotion);
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Đã cập nhật chương trình thành công!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

    @RequestMapping(value = "/StudyPromotion/Active/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoStudyPromotionView(@PathVariable(value = "id") Integer id, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new StudyPromotionFacade().activeStudyPromotion(id);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 0: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Kích hoạt quỹ khuyến học thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }
}
