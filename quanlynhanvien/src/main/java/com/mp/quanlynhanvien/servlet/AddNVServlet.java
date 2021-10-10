
package com.mp.quanlynhanvien.servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


import com.mp.quanlynhanvien.beans.UserAccount;
import com.mp.quanlynhanvien.beans.PhongBan;
import com.mp.quanlynhanvien.beans.ViTri;
import com.mp.quanlynhanvien.beans.TrangThai;
import com.mp.quanlynhanvien.utils.DBUtils;
import com.mp.quanlynhanvien.utils.StorageUtils;
import com.mp.quanlynhanvien.utils.AutoFillUtils;

/**
 *
 * @author Ms Phuong
 */
public class AddNVServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        //ktra dang nhap
        HttpSession session = request.getSession();
        UserAccount loginedUser = StorageUtils.getLoginedUser(session);
        String errorString = null;
        
        errorString = request.getParameter("errorString");
        
        if (loginedUser == null){
            errorString = "Ban chua dang nhap.";
            request.setAttribute("errorString", errorString);
            //response.sendRedirect(request.getContextPath()+"/login");
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        //ktra quyen: chi co admin
        if (loginedUser.getTenVT().equals("Admin") == false ){
            errorString = "Quyen truy cap that bai.";
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
        String maNV = AutoFillUtils.getMaNV(conn);
        
        //luu thong tin vao request
        request.setAttribute("maNV", maNV);
        request.setAttribute("listPB",listPB);
        request.setAttribute("listVT",listVT);
        request.setAttribute("listTT",listTT);
        request.setAttribute("errorString",errorString);
        //chuyen sang trang addThanhTichView
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/addNVView.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String maNV = request.getParameter("maNV");
        String password = request.getParameter("password");
        String hoten = request.getParameter("hoten");
        String cmnd = request.getParameter("cmnd")+"";
        String email = request.getParameter("email");
        String gioitinh = request.getParameter("gioitinh");
        String diachi = request.getParameter("diachi");
        String tenPB = request.getParameter("tenPB");
        String tenVT = request.getParameter("tenVT");
        String ngayBD = request.getParameter("ngayBD");
        String ghichu = request.getParameter("ghichu");
        
        String hinhanh = null;
        String tenTT = "Hoat dong";
        
        String errorString = null;
        Connection conn = StorageUtils.getStoredConnection(request);
        
        /*
        //ktra 
        
        if (maNV.equals("") || password.equals("") || hoten.equals("") || gioitinh.equals("") ||
                diachi.equals("") || ngayBD.equals("")){
            errorString = "Can dien day du thong tin";
            response.sendRedirect(request.getContextPath()+"/addNV?errorString="+errorString);
            return;
        }
        */
        
        UserAccount user = new UserAccount(maNV,password,hoten,cmnd,email,gioitinh,diachi,hinhanh,tenPB, tenVT,
                                            ngayBD,tenTT,ghichu);
        
        
        
        try{
            DBUtils.insertNhanVien(conn, user);
            
        } catch (SQLException e){
            e.printStackTrace();
            errorString = "Loi du lieu.";
        }
        if (errorString != null){
            
            response.sendRedirect(request.getContextPath()+"/addNV?errorString="+errorString);
            return;
        }
        //neu khong co loi
        request.setAttribute("errorString", errorString);
        response.sendRedirect(request.getContextPath()+"/thongTinNV?maNV="+maNV);

    }
}
