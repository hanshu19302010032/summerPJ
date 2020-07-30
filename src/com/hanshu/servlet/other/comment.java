package com.hanshu.servlet.other;

import com.alibaba.fastjson.JSON;
import com.hanshu.bean.User;
import com.hanshu.others.Action;
import com.hanshu.others.Result;
import com.hanshu.service.commentServ;
import com.hanshu.service.userServ;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/CommentServlet")
public class commentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("method").equals("getComment")) {
                getComment(request, response);
            }
        } catch (Exception ignored) {

        }

    }

    private void getComment(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();
            PrintWriter out = response.getWriter();

            userServ userServ = new userServ(connection, request);

            User me = userServ.tryAutoLogin();

            if (me == null) {
                out.println(JSON.toJSON(new Action(false, "please login")));
                return;
            }

            int imageID = Integer.parseInt(request.getParameter("imageID"));
            int requestedPage = Integer.parseInt(request.getParameter("requestedPage"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            String howToOrder = request.getParameter("howToOrder");

            commentServ commentServ = new commentServ(connection);

            Result searchResult = commentServ.getComments(imageID, me, requestedPage, pageSize, howToOrder, request);

            out.println(JSON.toJSON(searchResult));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DbUtils.closeQuietly(connection);
        }
    }
}
