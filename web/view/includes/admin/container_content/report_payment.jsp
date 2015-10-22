<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['PAYMENT_PAGINATION']}"/>
<input type="hidden" id="reloadController" value="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}'/>">
<div class="content-title">
    <h3 class="text-center">${PAGINATION.viewTitle}</h3>
</div>
<div class="panel panel-default">
    <div class="panel-heading">
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>                       
                    <a type="button" class="navbar-brand btn-reload-content" data-toggle="tooltip" data-placement="bottom" data-title="Tải lại nội dung hiển thị">
                        <i class="fa fa-refresh"></i>
                    </a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <form class="form-search navbar-form navbar-left" novalidate method="post" action='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.SEARCH}"/>'>
                        <div class="input-group">
                            <div class="input-group-btn" data-toggle="tooltip" data-placement="left" data-title="Lựa chọn các trường tìm kiếm">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" tabindex="-1">
                                    <span class="caret"></span>
                                    <span class="sr-only">Toggle Dropdown</span>
                                </button>
                                <ul class="dropdown-menu dropdown-select dropdown-menu-left">
                                    <li><a><label><input type="checkbox" value="cus1_.lastName" />Tên NPP</label></a></li>
                                    <li><a><label><input type="checkbox" value="cus1_.userName" />Username NPP</label></a></li>
                                    <li class="divider"></li>
                                    <li><a><label><input class="select-all" type="checkbox" />Tất Cả</label></a></li>
                                </ul>
                            </div>
                            <input type="text" class="form-control" name="searchString" placeholder="Nhập từ khóa ..." />
                            <div class="input-group-btn">
                                <button type="submit" class="btn btn-default">                                
                                    <span class="fa fa-search"></span>
                                </button>
                            </div>
                        </div>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                        <c:if test="${PAGINATION.exportable}">
                            <li>
                                <a class="btn btn-default btn-sm" href='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.EXPORT}/"/>'>
                                    Xuất Excel<i class="fa fa-download"></i>
                                </a>
                            </li>
                        </c:if>
                        <li>
                            <a class="btn btn-default btn-sm btn-send-ajax" controller='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CALC_SALARY}/"/>'>
                                Kết toán<i class="fa fa-dollar"></i>
                            </a>
                        </li>
                    </ul>
                    <div class="navbar-form navbar-right">
                        <div class="form-group">
                            <label class="control-label">Ngày: </label>
                            <select class="form-control change-date" controller="<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CHANGE_DAY}/"/>">
                                <option value="-1">-- Tất cả --</option>
                                <option value="1">Trước ngày 15</option>
                                <option value="2">Sau ngày 15</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label">Tháng: </label>
                            <select class="form-control change-date" controller="<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CHANGE_MONTH}/"/>">
                                <option value="-1">-- Tất cả --</option>
                                <c:forEach begin="1" end="12" var="month">
                                    <option value="${month}" ${PAGINATION.month==month?'selected':''}>${month}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label">Năm: </label>
                            <select class="form-control change-date" controller="<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CHANGE_YEAR}/"/>">
                                <option value="-1">-- Tất cả --</option>
                                <c:forEach items="${f:findAllHistoryAwardYear()}" var="year">
                                    <option value="${year}" ${PAGINATION.year==year?'selected':''}>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </div><!-- end panel heading -->
    <div class="ajax-content panel-body">
        <c:import url="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.GO_TO}/1"/>
    </div>
</div><!-- end panel-->