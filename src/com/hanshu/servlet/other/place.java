package com.hanshu.servlet.other;

import com.alibaba.fastjson.JSON;
import com.hanshu.bean.City;
import com.hanshu.bean.Country;
import com.hanshu.DAO.city.city;
import com.hanshu.DAO.city.cityDAO;
import com.hanshu.DAO.country.country;
import com.hanshu.DAO.country.countryDAO;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet("/CountryCityServlet")
public class
placeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String method = request.getParameter("method");
            if (method.equals("erjiliandong")) {
                erjiliandong(request, response);
                return;
            }
            if (method.equals("getCountryOptions")) {
                getCountryOptions(response);
            }
        } catch (Exception ignored) {
        }
    }

    private void erjiliandong(HttpServletRequest request, HttpServletResponse response) {
        DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String iso = request.getParameter("iso");
            city cityDao = new cityDAO(connection);
            List<City> cityList = cityDao.getCities(iso);

            PrintWriter out = response.getWriter();
            out.println(JSON.toJSON(cityList));

        } catch (Exception ignored) {
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private void getCountryOptions(HttpServletResponse response) {
        Connection connection = null;
        try {
            PrintWriter out = response.getWriter();
            DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();
            country countryDao = new countryDAO(connection);
            List<Country> countryList = countryDao.getALL();
            out.println(JSON.toJSON(countryList));
        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }
    }


}
