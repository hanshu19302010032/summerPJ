package com.hanshu.servlet.page;

import com.hanshu.bean.User;
import com.hanshu.service.userServ;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
        Connection connection;
        try {
            connection = dataSource.getConnection();
            userServ userServ = new userServ(connection, request);
            User user = userServ.tryAutoLogin();
            if (user == null) {
                request.getRequestDispatcher("/loginjsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("/index").forward(request, response);
            DbUtils.close(connection);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


    }
}
