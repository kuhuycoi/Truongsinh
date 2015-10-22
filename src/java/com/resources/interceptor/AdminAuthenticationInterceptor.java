package com.resources.interceptor;

import com.resources.entity.Admin;
import com.resources.entity.ModuleInRole;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AdminAuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        String rootContext = request.getServletContext().getContextPath();
        Admin aID = (Admin) request.getSession().getAttribute("ADMIN");
        String uri= request.getRequestURI();
        if (aID == null) {
            response.getWriter().write("<script>window.location.href='" + rootContext + "/Admin/Login'</script>");
            return false;
        } else if (uri.endsWith(".jsp") || uri.endsWith(".html")||uri.endsWith("/Admin")||uri.endsWith("/admin")) {
            return true;
        } else {
            String[] strs = uri.split("/");
            String includeUri = "";
            if (strs.length >= 3) {
                includeUri = "/" + strs[2];
            }
            for (ModuleInRole mIR : aID.getRoleAdmID().getModuleInRoles()) {
                if (includeUri.equalsIgnoreCase(mIR.getModuleID().getController())) {
                    return true;
                }
            }
            return false;
        }
    }
}
