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

@WebServlet("/upload")
public class uploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            userServ userServ = new userServ(connection, request);
            User user = userServ.tryAutoLogin();
            if (user == null) {
                request.getRequestDispatcher("login").forward(request, response);
                return;
            }
            request.setAttribute("user", user);


            request.getRequestDispatcher("uploadjsp").forward(request, response);

        } catch (Exception ignored) {
        } finally {
            DbUtils.closeQuietly(connection);
        }

    }
}
