package com.resources.controller;

import com.resources.facade.CustomerFacade;
import com.resources.facade.ProvincialAgenciesFacade;
import com.resources.bean.CustomerNonActive;
import com.resources.function.CustomFunction;
import com.resources.pagination.admin.CustomerPagination;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.pagination.admin.ProvincialAgencyPagination;
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
@RequestMapping(value = "/Admin/Customer")
public class AdminCustomerController {

    @RequestMapping(value = "/TreeCustomer", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getTreeCustomerView(ModelMap mm, HttpSession session) {
        CustomerPagination treeCustomerPagination = new CustomerPagination("/customer_tree_customer", "/TreeCustomer");
        session.setAttribute("TREE_PAGINATION", treeCustomerPagination);
        mm.put("TREE_CUSTOMER", new CustomerFacade().getTreeCustomer(14476, 6));
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + treeCustomerPagination.getViewName());
    }

//    @RequestMapping(value = "/TreeCustomer/Reload", method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView reloadTreeCustomerView(ModelMap mm, HttpSession session) {
//        CustomerPagination treeCustomerPagination = (CustomerPagination) session.getAttribute("TREE_PAGINATION");
//        if (treeCustomerPagination == null) {
//            treeCustomerPagination = new CustomerPagination("/customer_tree_customer", "/TreeCustomer");
//            session.setAttribute("TREE_PAGINATION", treeCustomerPagination);
//        }
//        String tree = CustomFunction.buildTreeCustomer(new CustomerFacade().getTreeCustomer(2, 6), 1);
//        mm.put("TREE_CUSTOMER", tree);
//        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + treeCustomerPagination.getViewName());
//    }

    @RequestMapping(value = "/Distributor", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultDistributorView(ModelMap mm, HttpSession session) {
        CustomerPagination distributorPagination = new CustomerPagination("/customer_distributor", "/Distributor");
        session.setAttribute("DISTRIBUTOR_PAGINATION", distributorPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + distributorPagination.getViewName());
    }

    @RequestMapping(value = "/Distributor/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForDistributorView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        CustomerPagination distributorPagination = (CustomerPagination) session.getAttribute("DISTRIBUTOR_PAGINATION");
        if (distributorPagination != null) {
            distributorPagination.setDisplayPerPage(displayPerPage);
        }
        return distributorView(distributorPagination, session);
    }

    @RequestMapping(value = "/Distributor/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByDistributorView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        CustomerPagination distributorPagination = (CustomerPagination) session.getAttribute("DISTRIBUTOR_PAGINATION");
        if (distributorPagination != null) {
            if (distributorPagination.getOrderColmn().equals(orderBy)) {
                distributorPagination.setAsc(!distributorPagination.isAsc());
            }
            distributorPagination.setOrderColmn(orderBy);
        }
        return distributorView(distributorPagination, session);
    }

    @RequestMapping(value = "/Distributor/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoDistributorView(@PathVariable("page") int page, HttpSession session) {
        CustomerPagination distributorPagination = (CustomerPagination) session.getAttribute("DISTRIBUTOR_PAGINATION");
        if (distributorPagination != null) {
            distributorPagination.setCurrentPage(page);
        }
        return distributorView(distributorPagination, session);
    }

    @RequestMapping(value = "/Distributor/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchDistributorView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return distributorView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        CustomerPagination distributorPagination = new CustomerPagination("/customer_distributor", "/Distributor");
        distributorPagination.setSearchString(searchString);
        distributorPagination.setKeywords(keywords);
        return distributorView(distributorPagination, session);
    }

    private ModelAndView distributorView(CustomerPagination distributorPagination, HttpSession session) {
        if (distributorPagination == null) {
            distributorPagination = new CustomerPagination("/customer_distributor", "/Distributor");
        }
        new CustomerFacade().pageData(distributorPagination);
        session.setAttribute("DISTRIBUTOR_PAGINATION", distributorPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + distributorPagination.getViewName());
    }

    @RequestMapping(value = {"/Distributor/ViewInsert", "/Non-Active/ViewInsert"}, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getViewInsert(HttpSession session) {
        CustomerPagination distributorPagination = (CustomerPagination) session.getAttribute("DISTRIBUTOR_PAGINATION");
        if (distributorPagination == null) {
            distributorPagination = new CustomerPagination("/customer_distributor", "/Distributor");
        }
        session.setAttribute("DISTRIBUTOR_PAGINATION", distributorPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + distributorPagination.getInsertViewName());
    }

    @RequestMapping(value = {"/Distributor/ViewEdit/{id}", "/Non-Active/ViewEdit/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getViewEdit(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        CustomerPagination distributorPagination = (CustomerPagination) session.getAttribute("DISTRIBUTOR_PAGINATION");
        if (distributorPagination == null) {
            distributorPagination = new CustomerPagination("/customer_distributor", "/Distributor");
        }
        session.setAttribute("DISTRIBUTOR_PAGINATION", distributorPagination);
        mm.put("CUSTOMER", new CustomerFacade().findDistributorById(id));
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + distributorPagination.getEditViewName());
    }

    //Non-active
    @RequestMapping(value = "/Non-Active", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultNonActiveView(HttpSession session) {
        CustomerPagination nonactivePagination = new CustomerPagination("createdOnUtc", "/customer_non_active", "/Non-Active");
        session.setAttribute("NON_ACTIVE_PAGINATION", nonactivePagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + nonactivePagination.getViewName());
    }

    @RequestMapping(value = "/Non-Active/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForNonActiveView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        CustomerPagination nonactivePagination = (CustomerPagination) session.getAttribute("NON_ACTIVE_PAGINATION");
        if (nonactivePagination != null) {
            nonactivePagination.setDisplayPerPage(displayPerPage);
        }
        return nonActiveView(nonactivePagination, session);
    }

    @RequestMapping(value = "/Non-Active/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForNonActiveView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        CustomerPagination nonactivePagination = (CustomerPagination) session.getAttribute("NON_ACTIVE_PAGINATION");
        if (nonactivePagination != null) {
            if (nonactivePagination.getOrderColmn().equals(orderBy)) {
                nonactivePagination.setAsc(!nonactivePagination.isAsc());
            }
            nonactivePagination.setOrderColmn(orderBy);
        }
        return nonActiveView(nonactivePagination, session);
    }

    @RequestMapping(value = "/Non-Active/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoNonActiveView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        CustomerPagination nonactivePagination = (CustomerPagination) session.getAttribute("NON_ACTIVE_PAGINATION");
        if (nonactivePagination != null) {
            nonactivePagination.setCurrentPage(page);
        }
        return nonActiveView(nonactivePagination, session);
    }

    @RequestMapping(value = "/Non-Active/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchNonActiveView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return nonActiveView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        CustomerPagination distributorPagination = new CustomerPagination("createdOnUtc", "/customer_non_active", "/Non-Active");
        distributorPagination.setSearchString(searchString);
        distributorPagination.setKeywords(keywords);
        return nonActiveView(distributorPagination, session);
    }

    private ModelAndView nonActiveView(CustomerPagination nonactivePagination, HttpSession session) {
        if (nonactivePagination == null) {
            nonactivePagination = new CustomerPagination("createdOnUtc", "/customer_non_active", "/Non-Active");
        }
        new CustomerFacade().pageDataNonActive(nonactivePagination);
        session.setAttribute("NON_ACTIVE_PAGINATION", nonactivePagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + nonactivePagination.getViewName());
    }

    //ProvincialAgencies
    @RequestMapping(value = "/ProvincialAgencies", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultProvincialAgenciesView(ModelMap mm, HttpSession session) {
        ProvincialAgencyPagination provincialAgencyPagination = new ProvincialAgencyPagination("/customer_provincial_agencies", "/ProvincialAgencies");
        session.setAttribute("PROVINCIAL_AGENCIES_PAGINATION", provincialAgencyPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + provincialAgencyPagination.getViewName());
    }

    @RequestMapping(value = "/ProvincialAgencies/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForProvincialAgenciesView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        ProvincialAgencyPagination provincialAgencyPagination = (ProvincialAgencyPagination) session.getAttribute("PROVINCIAL_AGENCIES_PAGINATION");
        if (provincialAgencyPagination != null) {
            provincialAgencyPagination.setDisplayPerPage(displayPerPage);
        }
        return provincialAgenciesView(provincialAgencyPagination, session);
    }

    @RequestMapping(value = "/ProvincialAgencies/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForProvincialAgenciesView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        ProvincialAgencyPagination provincialAgencyPagination = (ProvincialAgencyPagination) session.getAttribute("PROVINCIAL_AGENCIES_PAGINATION");
        if (provincialAgencyPagination != null) {
            if (provincialAgencyPagination.getOrderColmn().equals(orderBy)) {
                provincialAgencyPagination.setAsc(!provincialAgencyPagination.isAsc());
            }
            provincialAgencyPagination.setOrderColmn(orderBy);
        }
        return provincialAgenciesView(provincialAgencyPagination, session);
    }

    @RequestMapping(value = "/ProvincialAgencies/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoProvincialAgenciesView(@PathVariable("page") int page, HttpSession session) {
        ProvincialAgencyPagination provincialAgencyPagination = (ProvincialAgencyPagination) session.getAttribute("PROVINCIAL_AGENCIES_PAGINATION");
        if (provincialAgencyPagination != null) {

            provincialAgencyPagination.setCurrentPage(page);
        }
        return provincialAgenciesView(provincialAgencyPagination, session);
    }

    @RequestMapping(value = "/ProvincialAgencies/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchProvincialAgenciesView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return provincialAgenciesView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        ProvincialAgencyPagination provincialAgencyPagination = new ProvincialAgencyPagination("/customer_provincial_agencies", "/ProvincialAgencies");
        provincialAgencyPagination.setSearchString(searchString);
        provincialAgencyPagination.setKeywords(keywords);
        return provincialAgenciesView(provincialAgencyPagination, session);
    }

    private ModelAndView provincialAgenciesView(ProvincialAgencyPagination provincialAgencyPagination, HttpSession session) {
        if (provincialAgencyPagination == null) {
            provincialAgencyPagination = new ProvincialAgencyPagination("/customer_provincial_agencies", "/ProvincialAgencies");
        }
        new ProvincialAgenciesFacade().pageData(provincialAgencyPagination);
        session.setAttribute("PROVINCIAL_AGENCIES_PAGINATION", provincialAgencyPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + provincialAgencyPagination.getViewName());
    }

    //Auto complete ParentId
    @RequestMapping(value = "/SearchParentId/{searchString}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView searchParentId(@PathVariable("searchString") String searchString, ModelMap mm) {
        mm.put("PARENTIDLIST", new CustomerFacade().findAllCustomerForParentId(searchString));
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + "/customer_parentid_list");
    }

    //Auto complete CustomerId
    @RequestMapping(value = "/SearchCustomerId/{searchString}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView searchCustomerId(@PathVariable("searchString") String searchString, ModelMap mm) {
        mm.put("PARENTIDLIST", new CustomerFacade().findAllCustomerForCustomerId(searchString));
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + "/customer_parentid_list");
    }

    @RequestMapping(value = "/SearchCustomerId/{searchString}/{parentName}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView searchCustomerId(@PathVariable("searchString") String searchString, @PathVariable("parentName") String parentName, ModelMap mm) {
        mm.put("PARENTIDLIST", new CustomerFacade().findAllCustomerForCustomerId(searchString));
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + "/customer_parentid_list");
    }

    //Insert new Customer
    @RequestMapping(value = "/Distributor/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertNewCustomer(@RequestBody CustomerNonActive customerNonActive, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        customerNonActive.setPassword("123456");
        customerNonActive.setUserName(customerNonActive.getTitle());
        int result;
        try {
            result = new CustomerFacade().create(customerNonActive);
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Người dùng không hợp lệ!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Yêu cầu nhập tất cả các thông tin được yêu cầu!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tên đăng nhập nhanh đã tồn tại");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 4: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Người chỉ định không hợp lệ");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 5: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Người giới thiệu không hợp lệ");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 7: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Đăng ký thành công, Username: " + customerNonActive.getUserName());
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi1. Thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    //Edit Customer
    @RequestMapping(value = "/Distributor/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editCustomer(@RequestBody CustomerNonActive customerNonActive, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        int result;
        try {
            result = new CustomerFacade().edit(customerNonActive);
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }

        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật nhà phân phối thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi1. Thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    //Active customer
    @RequestMapping(value = "/Active/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView activeCustomer(@PathVariable(value = "id") Integer id, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new CustomerFacade().activeCustomer(id);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Active thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Nhánh cha đã đủ 2 người!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "ID người dùng không hợp lệ!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 4: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tài khoản có người chỉ định không hợp lệ!");
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

    //Active customer
    @RequestMapping(value = "/Active", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView activeCustomer(@RequestBody Integer[] ids, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new CustomerFacade().activeMultiCustomer(ids);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Active " + ids.length + " tài khoản thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tồn tại tài khoản chỉ định đã có 2 nhánh con!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tồn tại tài khoản có ID không hợp lệ!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 4: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tồn tại tài khoản có người chỉ định không hợp lệ!");
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

    //Delete customer
    @RequestMapping(value = {"/Distributor/Delete/{id}", "/Non-Active/Delete/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteCustomer(@PathVariable(value = "id") Integer id, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new CustomerFacade().deleteCustomer(id);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Xóa nhà phân phối thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tài khoản đã có nhánh con!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tài khoản đã từng nap tiền!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 4: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tài khoản không tồn tại hoặc đã bị xóa!");
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

    @RequestMapping(value = {"/Distributor/Delete", "/Non-Active/Delete"}, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView deleteMultiCustomer(@RequestBody Integer[] ids, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new CustomerFacade().deleteMultiCustomer(ids);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Xóa " + ids.length + " nhà phân phối thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Có tài khoản đã có nhánh con!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Có tài khoản đã từng nap tiền!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 4: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Có tài khoản không tồn tại hoặc đã bị xóa!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "thông báo", "Vui lòng chọn ít nhất một tài khoản!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    //Reset Password
    @RequestMapping(value = {"/Distributor/ResetPassword/{id}", "/Non-Active/ResetPassword/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView resetPassword(@PathVariable(value = "id") Integer id, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new CustomerFacade().resetPassword(id);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Reset mật khẩu thành công!");
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

    @RequestMapping(value = {"/Distributor/ResetPassword", "/Non-Active/ResetPassword"}, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView resetPassword(@RequestBody Integer[] ids, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            result = new CustomerFacade().resetMultiPassword(ids);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Reset mật khẩu " + result + " tài khoản thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
}
