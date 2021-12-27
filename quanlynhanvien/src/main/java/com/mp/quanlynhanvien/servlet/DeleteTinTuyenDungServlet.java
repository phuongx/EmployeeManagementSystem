/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mp.quanlynhanvien.servlet;

import com.mp.quanlynhanvien.beans.PhongBan;
import com.mp.quanlynhanvien.beans.TinTuyenDung;
import com.mp.quanlynhanvien.beans.TrangThai;
import com.mp.quanlynhanvien.beans.UserAccount;
import com.mp.quanlynhanvien.beans.ViTri;
import com.mp.quanlynhanvien.utils.AutoFillUtils;
import com.mp.quanlynhanvien.utils.DBUtils;
import com.mp.quanlynhanvien.utils.StorageUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ms Phuong
 */
public class DeleteTinTuyenDungServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        //ktra dang nhap
        HttpSession session = request.getSession();
        UserAccount loginedUser = StorageUtils.getLoginedUser(session);
        String errorString = null;
        if (loginedUser == null){
            errorString = "Bạn chưa đăng nhập..";
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        //ktra quyen: chi co admin
        int quyenUser = StorageUtils.getQuyenUser(session);
        if (quyenUser == 2 ){
            errorString = "Quyền truy cập thất bại..";
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/errorView.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        //xu ly
        Connection conn = StorageUtils.getStoredConnection(request);
        List<PhongBan> listPB = AutoFillUtils.getListPB(conn);
        List<ViTri> listVT = AutoFillUtils.getListVT(conn);
        List<TrangThai> listTT = AutoFillUtils.getListTT(conn);
        TinTuyenDung tin = new TinTuyenDung();
        String id = request.getParameter("id");
        int maTin = Integer.parseInt(id);
        try {
            tin = DBUtils.getTinTuyenDung(conn, maTin);
            if (tin != null){
                DBUtils.deleteTinTuyenDung(conn, tin);
            }
        } catch (SQLException e){
            e.printStackTrace();
            errorString = "Yêu cầu không hợp lệ";
        }
        
        //luu thong tin vao request
        request.setAttribute("listPB",listPB);
        request.setAttribute("listVT",listVT);
        request.setAttribute("listTT",listTT);
        request.setAttribute("tin", tin);
        request.setAttribute("errorString", errorString);
        
        request.getRequestDispatcher("/WEB-INF/views/qlyTinTuyenDungView.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
