<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['INDEX_HISTORY_SALARY_PAGINATION']}"/>
<c:if test="${f:size(PAGINATION.displayList)==0}">
    <div class="alert alert-danger">
        Không có kết quả nào được hiển thị
    </div>
</c:if>
<c:if test="${f:size(PAGINATION.displayList)!=0}">
    <table class="table table-hover table-bordered table-valign-midle table-grid-view table-align-center">
        <thead>
            <tr>
                <th class="col-md-6 external">Tháng</th>
                <th class="col-md-3 external">Tiền Lương</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${PAGINATION.displayList}" var="award">
                <tr>
                    <td>${award[0]}</td>
                    <td>${f:formatCurrency(award[1])}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table><!-- end table -->

    <div class="buttonBar"></div>
</c:if>