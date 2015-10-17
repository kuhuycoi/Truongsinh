<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel">
    <div class="panel-heading">
        <a data-toggle="collapse" href="#collapseOne">
            Thông tin hệ thống            
        </a>
    </div>
    <div class="panel-body collapse in" id="collapseOne">
        <ul>
            <li><a class="btn-change-content" controller="<c:url value="/Customer/MyAccount" />">Thông tin tài khoản</a></li> 
            <li><a class="btn-change-content" controller="<c:url value="/Customer/ChangePassword" />">Thay đổi mật khẩu</a></li> 
            <li><a class="btn-open-diagram" controller="<c:url value="/Customer/TreeCustomer" />">Cây phả hệ chỉ định</a></li> 
            <li><a class="btn-change-content" controller="<c:url value='/Customer/CustomerForCustomer'/>">Danh sách đã giới thiệu</a></li>
            <li><a class="btn-change-content" controller="<c:url value='/Customer/CustomerForCustomer'/>">Hộp thư đến</a></li>
            <li><a class="btn-change-content" controller="<c:url value='/Customer/CustomerForCustomer'/>">Gửi thư</a></li>
        </ul>
    </div>
</div>
<div class="panel">
    <div class="panel-heading">
        <a data-toggle="collapse" href="#collapseTwo">
            Lịch sử giao dịch
            
        </a>
    </div>
    <div class="panel-body collapse in" id="collapseTwo">
        <ul>
            <li><a class="btn-change-content" controller="<c:url value="/History/CustomerRankCustomer/ViewInsert" />">Kích hoạt mã số học viên</a></li>
            <!--<li><a class="btn-change-content" controller="<c:url value="/History/CustomerRankCustomer" />">Lịch sử nạp PV</a></li>-->
            <li><a class="btn-change-content" controller="<c:url value="/History/UsedPinSys" />">Lịch sử nạp mã PIN</a></li>
        </ul>
    </div>
</div>
<div class="panel">
    <div class="panel-heading">
        <a data-toggle="collapse" href="#collapseTwo">
            Mua thu nhập            
        </a>
    </div>
    <div class="panel-body collapse in" id="collapseTwo">
        <ul>
            <li><a class="btn-change-content external" href="#">Thu nhập tĩnh </a></li>
            <li><a class="btn-change-content external" href="#">Thu nhập động</a></li>
        </ul>
    </div>
</div>
<div class="panel">
    <div class="panel-heading">
        <a data-toggle="collapse" href="#collapseThree">
            Thống kê thưởng            
        </a>
    </div>
    <div class="panel-body collapse in" id="collapseThree">
        <ul>
            <li><a class="btn-change-content" controller="<c:url value="/Report/Award/1" />">Hoa hồng hoàn vốn siêu tốc</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/Award/2" />">Hoa hồng tập huấn</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/Award/3" />">Hoa hồng tăng trưởng</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/Award/4" />">Hoa hồng trực tiếp</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/Award/13" />">Hoa hồng gián tiếp cặp</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/Award/14" />">Quỹ khuyến học</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/Salary" />">Lương theo tháng</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/AwardTotal" />">Tổng thu nhập theo tháng</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/Trian" />">Kết quả hoàn phí</a></li>
        </ul>
    </div>
</div>
<div class="panel">
    <div class="panel-heading">
        <a data-toggle="collapse" href="#collapseThree">
            Thống kê thu nhập            
        </a>
    </div>
    <div class="panel-body collapse in" id="collapseThree">
        <ul>
            <li><a class="btn-change-content" controller="<c:url value="/Report/AwardG/1" />">Thống kê học viên G1</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/AwardG/2" />">Thống kê học viên G1 -> G2</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/AwardG/3" />">Thống kê học viên G2</a></li>
        </ul>
    </div>
</div>
<div class="panel">
    <div class="panel-heading">
        <a data-toggle="collapse" href="#collapseThree">
            Thanh toán hoa hồng            
        </a>
    </div>
    <div class="panel-body collapse in" id="collapseThree">
        <ul>
            <li><a class="btn-change-content" controller="<c:url value="/Payment/ViewInsert" />">Yêu cầu thanh toán</a></li>
            <li><a class="btn-change-content" controller="<c:url value="/Report/Award/4" />">Lich sử thanh toán</a></li>
        </ul>
    </div>
</div>
