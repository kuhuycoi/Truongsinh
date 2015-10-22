<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['INDEX_HISTORY_TRIAN_PAGINATION']}"/>
<c:if test="${f:size(PAGINATION.displayList)==0}">
    <div class="alert alert-danger">
        Không có kết quả nào được hiển thị
    </div>
</c:if>
<c:if test="${f:size(PAGINATION.displayList)!=0}">
    <table class="table table-hover table-bordered table-valign-midle table-grid-view table-align-center">
        <thead>
            <tr>
                <th class="col-md-3 external">ID</th>
                <th class="col-md-3 external">Thời gian</th>
                <th class="col-md-3 external">Mức hoàn phí</th>
                <th class="col-md-3 external">Thành tiền</th>
            </tr>
        </thead>
        <tbody>
            <c:set var="money" value="0"/>
            <c:forEach items="${PAGINATION.displayList}" var="trian">
                <tr>
                    <td>${trian.trueVT}</td>
                    <td>${f:formatTime(trian.datetimecreated)}</td>
                    <td>${trian.levelup}</td>
                    <c:choose>
                        <c:when test="${trian.levelup==1}">
                            <c:set var="money" value="20"/>
                        </c:when>
                        <c:when test="${trian.levelup==2}">
                            <c:set var="money" value="120"/>
                        </c:when>
                        <c:when test="${trian.levelup==3}">
                            <c:set var="money" value="320"/>
                        </c:when>
                        <c:when test="${trian.levelup==4}">
                            <c:set var="money" value="1000"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="money" value="0"/>
                        </c:otherwise>
                    </c:choose>
                    <td>${f:formatCurrency(money)}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table><!-- end table -->

    <div class="buttonBar"></div>
</c:if>