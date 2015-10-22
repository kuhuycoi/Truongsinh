<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['INDEX_CUSTOMER_FOR_CUSTOMER_PAGINATION']}"/>
<c:if test="${f:size(PAGINATION.displayList)==0}">
    <div class="alert alert-danger">
        Không có kết quả nào được hiển thị
    </div>
</c:if>
<c:if test="${f:size(PAGINATION.displayList)!=0}">
    <table class="table table-condensed table-bordered table-hover table-valign-midle table-grid-view table-align-center">
        <thead>
            <tr controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.ORDER_DATA}/'/>">
                <th class="th-id" column="id">ID <span class="${PAGINATION.orderColmn=='id'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="lastName">Tên NPP <span class="${PAGINATION.orderColmn=='lastName'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="userName">Username NPP <span class="${PAGINATION.orderColmn=='userName'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="email">Email <span class="${PAGINATION.orderColmn=='email'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-3" column="createdOnUtc">Ngày tạo <span class="${PAGINATION.orderColmn=='createdOnUtc'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-3" column="isActive">Trạng thái <span class="${PAGINATION.orderColmn=='isActive'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="external"></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${PAGINATION.displayList}" var="cus">
                <tr class='${cus.isActive?"":"danger"}'>
                    <td>${cus.id}</td>
                    <td>${cus.firstName} ${cus.lastName}</td>
                    <td>${cus.userName}</td>
                    <td>${cus.email}</td>
                    <td>${f:formatTime(cus.createdOnUtc)}</td>
                    <td>${cus.isActive?'Đã kích hoạt':'Chưa kích hoạt'}</td>
                    <td>
                        <button class="btn btn-sm ${!cus.isActive?'btn-send-ajax btn-success':'btn-warning'}" ${cus.isActive?'disabled':''} confirm='true' controller='<c:url value="/Customer/ActiveCustomer/${cus.id}"/>' data-toggle="tooltip" data-placement="bottom" data-title="Kích hoạt tài khoản này.">
                            <i class="ti-check"></i>
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table><!-- end table -->
    <div class="buttonBar"></div>
</c:if>