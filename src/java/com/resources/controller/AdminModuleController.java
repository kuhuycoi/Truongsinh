package com.resources.controller;

import com.resources.facade.ModuleFacade;
import com.resources.entity.Module;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.ModulePagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/Admin/Module")
public class AdminModuleController {
    @RequestMapping(value = "/Sidebar", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getSidebar(ModelMap mm) {
        ModulePagination moduleP=new ModulePagination("/module_sidebar", "/Sidebar"); 
        new ModuleFacade().pageData(moduleP);
        mm.put("SIDEBAR_PAGINATION",moduleP);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + new ModulePagination("/module_sidebar", "/Sidebar").getViewName());
    }

    @RequestMapping(value = "/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insert(@RequestParam(value = "module") Module module, ModelMap mm) {

        return new ModelAndView("includes/admin/message_content");
    }
}
