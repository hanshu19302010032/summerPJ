package com.hanshu.listener;

import com.hanshu.config.Config;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.beans.PropertyVetoException;

@WebListener()
public class MyListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    public MyListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ComboPooledDataSource comboPooledDataSource = null;
        try {
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass(Config.driverClassName);
            comboPooledDataSource.setUser(Config.username);
            comboPooledDataSource.setPassword(Config.password);
            comboPooledDataSource.setJdbcUrl(Config.jdbcURL);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dataSource", comboPooledDataSource);


    }

    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("dataSource", null);
    }

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {

    }


    public void attributeAdded(HttpSessionBindingEvent sbe) {

    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {

    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {

    }
}
