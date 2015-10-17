<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<h1>Thông tin hệ thống</h1>
<div class="panel">
    <div class="panel-heading">
        <h2 class="login-title">Yêu cầu thanh toán</h2>
    </div>
    <div class="panel-body">
        <form class="form-insert" novalidate method="post" action='<c:url value="/Payment/Insert"/>'>
            <div class="row">
                <div class="col-md-3">
                    <label>Hình thức thanh toán</label>
                </div>
                <div class="col-md-9">
                    <label class="checkbox-inline"><input type="checkbox" name="isBank">Chuyển khoản qua ngân hàng</label>
                </div>
            </div> 
            <div class="row">
                <div class="col-md-3">
                    <label>Ngân hàng</label>
                </div>
                <div class="col-md-9">
                    <input type="text" name="bank" class="form-control">
                </div>
            </div> 
            <div class="row">
                <div class="col-md-3">
                    <label>Số tài khoản</label>
                </div>
                <div class="col-md-9">
                    <input type="text" name="codeBank" class="form-control">
                </div>
            </div> 
            <div class="row">
                <div class="col-md-3">
                    <label>Chủ tài khoản</label>
                </div>
                <div class="col-md-9">
                    <input type="text" name="chuTK" class="form-control">
                </div>
            </div> 
            <div class="row">
                <div class="col-md-3">
                    <label>Thời gian thanh toán (*)</label>
                </div>
                <div class="col-md-9"> 
                    <div class="input-group datepicker">
                        <input type="text" name="date" required readonly="" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                    </div>
                </div>
            </div> 
            <div class="row">
                <div class="col-md-3">
                    <label>Chi nhánh</label>
                </div>
                <div class="col-md-9">
                    <select class="form-control" name="provincialAgenciesID">
                        <c:forEach items="${f:findAllAvailableProvincialAgencies()}" var="p">
                            <option value="${p.id}">${p.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row">
                <div class="col-md-3">
                    <label>Ghi chú</label>
                </div>
                <div class="col-md-9">
                    <textarea name="descrip" rows="4" class="form-control"></textarea>
                </div>
            </div> 
            <div class="buttonBar">
                <button type="submit" class="btn btn-default">
                    Gửi yêu cầu thanh toán
                </button>
                <button type="reset" class="btn btn-default">
                    Nhập lại
                </button>
            </div>
        </form>
    </div>
</div>