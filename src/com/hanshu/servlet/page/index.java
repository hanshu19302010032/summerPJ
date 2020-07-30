package com.hanshu.servlet.page;

import com.hanshu.bean.Image;
import com.hanshu.bean.User;
import com.hanshu.service.imageServ;
import com.hanshu.service.userServ;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@WebServlet(urlPatterns = {"/index"})
public class indexServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();
            userServ userServ = new userServ(connection, request);

            User user = userServ.tryAutoLogin();

            imageServ imageServ = new imageServ(connection);
            List<Image> popularImageList = imageServ.getMostPopularNImages(3);
            List<Image> freshImageList = imageServ.getMostFreshNImages(3);

            request.setAttribute("user", user);
            request.setAttribute("popularImageList", popularImageList);
            request.setAttribute("freshImageList", freshImageList);

            request.getRequestDispatcher("indexjsp").forward(request, response);
        } catch (Exception ignored) {
        }finally {
            DbUtils.closeQuietly(connection);
        }


    }
}
