<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['INDEX_HISTORY_SALARY_PAGINATION']}"/>
<h1>Thống kê lương</h1>
<div class="panel">
    <div class="panel-heading">
        <h2>${PAGINATION.viewTitle}</h2>
    </div><!-- end panel heading -->
    <div class="panel-body">
        <div class="row">
            <div class="col-md-1"><label class="control-label">Năm</label></div>
            <div class="col-md-5">
                <select class="form-control change-date" controller="<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CHANGE_YEAR}/"/>">
                    <option value="-1">-- Tất cả --</option>
                    <c:forEach items="${f:findAllHistoryAwardYear()}" var="year">
                        <option value="${year}" ${PAGINATION.year==year?'selected':''}>Năm ${year}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="ajax-content">
            <c:import url="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.GO_TO}/1"/>
        </div>
    </div>
</div><!-- end panel-->