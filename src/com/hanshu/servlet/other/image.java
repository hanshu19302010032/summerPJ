package com.hanshu.servlet.other;

import com.alibaba.fastjson.JSON;
import com.hanshu.bean.Image;
import com.hanshu.bean.User;
import com.hanshu.others.Action;
import com.hanshu.others.Result;
import com.hanshu.service.imageServ;
import com.hanshu.service.userServ;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("method").equals("pureSearch")) {
            pureSearch(request, response);
            return;
        }
        if (request.getParameter("method").equals("myphoto")) {
            myPhoto(request, response);
            return;
        }
        if (request.getParameter("method").equals("myfavor")) {
            myfavor(request, response);
            return;
        }
        if (request.getParameter("method").equals("getImageInfo")) {
            getImageInfo(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }


    private void pureSearch(HttpServletRequest request, HttpServletResponse response) {
        String howToSearch = request.getParameter("howToSearch");
        String howToOrder = request.getParameter("howToOrder");
        String input = request.getParameter("input");
        int requestedPage = Integer.parseInt(request.getParameter("requestedPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            imageServ imageServ = new imageServ(connection);
            Result searchResult = imageServ.search(howToSearch, howToOrder, input, requestedPage, pageSize);

            Object s = JSON.toJSON(searchResult);

            PrintWriter out = response.getWriter();
            out.println(s);

        } catch (SQLException | IOException ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }


    }


    private void myPhoto(HttpServletRequest request, HttpServletResponse response) {
        DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            userServ userServ = new userServ(connection, request);
            User user = userServ.tryAutoLogin();
            if (user == null) return;

            int requestedPage = Integer.parseInt(request.getParameter("requestedPage"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            imageServ imageServ = new imageServ(connection);

            Result searchResult = imageServ.getMyPhotos(user, requestedPage, pageSize);

            PrintWriter out = response.getWriter();
            out.println(JSON.toJSON(searchResult));


        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }

    }


    public void myfavor(HttpServletRequest request, HttpServletResponse response) {
        DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            userServ userServ = new userServ(connection, request);

            User user = userServ.tryAutoLogin();
            if (user == null) return;

            int requestedPage = Integer.parseInt(request.getParameter("requestedPage"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            imageServ imageServ = new imageServ(connection);

            Result searchResult = imageServ.getFavor(user, requestedPage, pageSize);

            PrintWriter out = response.getWriter();

            out.println(JSON.toJSON(searchResult));

        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }

    }

    private void getImageInfo(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            PrintWriter out = response.getWriter();
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();
            int imageID = Integer.parseInt(request.getParameter("imageID"));

            userServ userServ = new userServ(connection, request);
            imageServ imageServ = new imageServ(connection);

            User user = userServ.tryAutoLogin();
            Image image = imageServ.getImage(user, imageID);

            if (image == null) {
                out.println(JSON.toJSON(new Action(false, "you don't have this photo,thus fail to modify")));
                return;
            }
            out.println(JSON.toJSON(image));

        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }
    }


}
