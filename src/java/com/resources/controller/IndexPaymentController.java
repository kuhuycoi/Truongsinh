package com.resources.controller;

import com.resources.facade.HistoryPaymentFacade;
import com.resources.pagination.index.DefaultIndexPagination;
import com.resources.pagination.index.MessagePagination;
import com.resources.pagination.index.PaymentPagination;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "Payment")
public class IndexPaymentController {

    @RequestMapping(value = "/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getViewInsert(HttpSession session) {
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + new PaymentPagination("Yêu cầu thanh toán", "payment_list").getInsertViewName());
    }

    @RequestMapping(value = "/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insert(@RequestBody Map map, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(MessagePagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination messagePagination;
        Integer result = 0;
        try {
            map.put("cusId", session.getAttribute("CUSTOMER_ID"));
            result = new HistoryPaymentFacade().insert(map);
        } catch (Exception e) {
            messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", messagePagination);
            return mAV;
        }

        switch (result) {
            case 1: {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Gửi yêu cầu thanh toán thành công!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
            case 2: {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Chưa đến ngày thanh toán!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
            case 3: {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn đã từng gửi yêu cầu thanh toán chu kỳ này!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
            case 4: {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Chu kỳ này bạn không có hoa hồng!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
            case 5: {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Vui lòng chọn ngày 1,2,16,17 trong tháng!");
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
