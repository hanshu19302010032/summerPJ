package com.hanshu.servlet.page;

import com.alibaba.fastjson.JSON;
import com.hanshu.bean.Image;
import com.hanshu.bean.User;
import com.hanshu.others.Action;
import com.hanshu.others.Record;
import com.hanshu.others.Browse;
import com.hanshu.service.imageServ;
import com.hanshu.service.userServ;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

@WebServlet("/details")
public class introductionServlet extends HttpServlet {
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

            request.setAttribute("user", user);

            int imageID = Integer.parseInt(request.getParameter("imageID"));
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            Action actionResult = null;
            if (action.equals("favor")) {
                actionResult = userServ.favorImage(user, imageID);
            } else if (action.equals("unfavor")) {
                actionResult = userServ.unfavorImage(user, imageID);
            }

            request.setAttribute("actionResult", actionResult);

            boolean hasFavoredImage = userServ.hasFavoredTheImage(user, imageID);
            request.setAttribute("hasFavoredImage", hasFavoredImage);

            imageServ imageServ = new imageServ(connection);
            Image image = imageServ.getImage(imageID);

            addRecordToCookie(request, response, image, user);


            request.setAttribute("image", image);

            request.getRequestDispatcher("detailsjsp").forward(request, response);


        } catch (Exception ignored) {
        } finally {
            DbUtils.closeQuietly(connection);
        }


    }

    private void addRecordToCookie(HttpServletRequest request, HttpServletResponse response, Image image, User user) throws UnsupportedEncodingException {
        if (user != null && image != null) {
            Cookie[] cookies = request.getCookies();

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(user.getUid() + "")) {
                    Record browseRecord = JSON.parseObject(URLDecoder.decode(cookie.getValue()), Record.class);
                    browseRecord.addRecord(new Browse(image.getTitle(), image.getImageID()));

                    String jsonString = URLEncoder.encode(JSON.toJSONString(browseRecord));
                    jsonString = jsonString.replaceAll("\\+", "%20");

                    if (jsonString.getBytes(StandardCharsets.UTF_8).length > 4096) {
                        return;
                    }

                    cookie.setValue(jsonString);

                    cookie.setPath(cookie.getPath());
                    response.addCookie(cookie);
                    return;
                }
            }

            Cookie browseRecordCookie = new Cookie(user.getUid() + "", "");
            Record browseRecord = new Record();
            browseRecord.addRecord(new Browse(image.getTitle(), image.getImageID()));

            String jsonString = JSON.toJSONString(browseRecord);

            jsonString = URLEncoder.encode(jsonString);
            jsonString = jsonString.replaceAll("\\+", "%20");

            if (jsonString.getBytes(StandardCharsets.UTF_8).length > 4096) {
                return;
            }
            browseRecordCookie.setValue(jsonString);
            response.addCookie(browseRecordCookie);
        }

    }

}
