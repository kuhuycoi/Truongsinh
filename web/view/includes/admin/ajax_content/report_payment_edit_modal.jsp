<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h4 class="modal-title">Nạp PV</h4>
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                </div>
            </div>                           
            <form id="form-insert" confirm="true" class="form-insert form-horizontal" novalidate method="POST" action="${pageContext.servletContext.contextPath}/Admin/Report/Payment/Edit">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="day" required class="control-label col-sm-3">Chu kỳ <i>(*)</i></label>
                        <div class="col-sm-9">
                            <select class="form-control" name="day" id="day">
                                <option value="1">Chu kỳ 1</option>
                                <option value="2">Chu kỳ 2</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="month" required class="control-label col-sm-3">Tháng <i>(*)</i></label>
                        <div class="col-sm-9">
                            <select class="form-control" name="month" id="month">
                                <c:forEach begin="1" end="12" var="month">
                                    <option value="${month}">${month}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="year" required class="control-label col-sm-3">Năm <i>(*)</i></label>
                        <div class="col-sm-9">
                            <select class="form-control" name="year">
                                <c:forEach items="${f:findAllHistoryPaymentYear()}" var="year">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="percent" required class="control-label col-sm-3">Phần trăm <i>(*)</i></label>
                        <div class="col-sm-9">
                            <input type="number" name="percent" value="90" id="percent" min="1" max="100" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-info">Lưu</button>
                    <button type="reset" class="btn btn-danger">Nhập lai</button>
                </div>
            </form>
        </div>
    </div>
</div>