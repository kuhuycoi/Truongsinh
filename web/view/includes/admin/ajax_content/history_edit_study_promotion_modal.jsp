<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h4 class="modal-title">Cập nhật quỹ khuyến học</h4>
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                </div>
            </div>                           
           <form id="form-insert" confirm="true" class="form-insert form-horizontal" novalidate method="POST" action="${pageContext.servletContext.contextPath}/Admin/History/StudyPromotion/Edit">
                <div class="modal-body">
                    <input type="hidden" value="${STUDY_PROMOTION.id}" name="id"/>
                    <div class="form-group">
                        <label for="name" required class="control-label col-sm-3">Tên chương trình</label>
                        <label class="col-md-9">${STUDY_PROMOTION.name}</label>
                    </div>
                    <div class="form-group">
                        <label for="idStart" required class="control-label col-sm-3">ID start</label>
                        <label class="col-md-9">${STUDY_PROMOTION.customerStart.id}</label>
                    </div>
                    <div class="form-group">
                        <label for="idEnd" required class="control-label col-sm-3">ID end</label>                        
                        <label class="col-md-9">${STUDY_PROMOTION.customerEnd.id}</label>
                    </div>
                    <div class="form-group">
                        <label for="totalMoney" required class="control-label col-sm-3">Tổng tiền</label>                                             
                        <label class="col-md-9">${STUDY_PROMOTION.moneyperone}</label>
                    </div>
                    <div class="form-group">
                        <label for="moneypercircle" required class="control-label col-sm-3">Tiền mỗi chu kỳ</label>
                        <div class="col-sm-9">
                            <input type="number" min="0" value="${STUDY_PROMOTION.moneypercircle}" required id="moneypercircle" name="moneypercircle" pattern="^[\d]+$" data-original-title-pattern="Chỉ chấp nhận các ký tự số" class="form-control">
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