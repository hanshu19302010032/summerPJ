package com.hanshu.servlet.page;

import com.hanshu.bean.User;
import com.hanshu.service.userServ;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;

@WebServlet("/myfriend")
public class friendListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();

            userServ userServ = new userServ(connection, request);

            User user = userServ.tryAutoLogin();


            if (user == null) {
                request.getRequestDispatcher("login").forward(request, response);
                return;
            }

            request.setAttribute("user", user);

            request.getRequestDispatcher("myfriendjsp").forward(request, response);


        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }

    }
}
