<%-- 
    Document   : timTheoTen
    Created on : Oct 18, 2021, 9:18:43 PM
    Author     : Ms Phuong
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form name="frm2">
        <table class="table table-striped" >
            <tr class="text-center">
                <th>Mã nhân viên</th>                
                <th>Họ tên</th>
                <th>Email</th>
                <th>Giới tính</th>
                <th>Phòng ban</th>
                <th>Vị trí</th>
                
                <th>Xem chi tiết</th>
                <c:if test="${not empty role}">
                <th>Chỉnh sửa</th>
                <th>Thêm thành tích</th>
                <th>Thêm vi phạm</th>
                </c:if>
                <c:if test="${loginedUser.tenVT=='Admin'}">
                <th>Thôi việc</th>  
                </c:if>
                
            </tr>
        <c:forEach items="${list}" var="values">
            <tr>
                <input type="hidden" name="maNV${values.maNV}" value="${values.maNV}">
                <td>${values.maNV}</td>
                <td>${values.hoten}</td>
                <td>${values.email}</td>
                <td>${values.gioitinh}</td>
                <td>${values.tenPB}</td>
                <td>${values.tenVT}</td>
                
                <td class="text-center"><a href="thongTinNV?maNV=${values.maNV}">Xem</a></td>
                <c:if test="${values.tenTT!='Thoi viec'}">
                    <c:if test="${not empty role}">
                    <td class="text-center"><a href="editTTNV?maNV=${values.maNV}">Sửa</a></td>
                    <td class="text-center"><a href="addThanhTich?maNV=${values.maNV}">Thêm</a></td>
                    <td class="text-center"><a href="addViPham?maNV=${values.maNV}">Thêm</a></td>  
                    </c:if>
                    <c:if test="${loginedUser.tenVT=='Admin'}">
                    <td class="text-center"><input type="button" class="btn btn-danger" value="Đánh dấu" onclick="check_confirm(maNV${values.maNV}.value)"></td>  
                    </c:if>
                </c:if>
                <c:if test="${values.tenTT=='Thoi viec'}">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
        </c:forEach>
        </table>
    </form>
    </body>
</html>