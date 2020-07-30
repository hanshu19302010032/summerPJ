package com.hanshu.others;

import com.hanshu.DAO.city.cityDAO;
import com.hanshu.DAO.country.countryDAO;
import com.hanshu.DAO.image.image;
import com.hanshu.DAO.image.imageDAO;
import com.hanshu.exception.MatchException;
import com.hanshu.exception.IncompleteException;
import com.hanshu.exception.TypeException;
import com.hanshu.utils.MD5Utils;
import com.hanshu.utils.MyUtils;

import javax.servlet.http.Part;
import java.security.SecureRandom;
import java.sql.Connection;

public class Upload {
    private String title;
    private String description;
    private String city;
    private String country;
    private String content;
    private Part photo;
    private String filename;

    public String getFilename() {
        return filename;
    }

    public Upload(String title, String description, String city, String country, String content, Part photo) {
        this.title = title;
        this.description = description;
        this.city = city;
        this.country = country;
        this.content = content;
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Part getPhoto() {
        return photo;
    }

    public void setPhoto(Part photo) {
        this.photo = photo;
    }


    public void cleanXSS() {
        this.city = MyUtils.cleanXSS(this.city);
        this.country = MyUtils.cleanXSS(this.country);
        this.content = MyUtils.cleanXSS(this.content);
        this.description = MyUtils.cleanXSS(this.description);
        this.title = MyUtils.cleanXSS(this.title);
    }

    public void checkCountryAndCity(Connection connection) throws MatchException {

        com.hanshu.DAO.country.country countryDao = new countryDAO(connection);
        com.hanshu.DAO.city.city cityDao = new cityDAO(connection);
        if (!countryDao.countryExist(this.country)) {
            throw new MatchException("the country does not exist");
        }

        if (!cityDao.countryCityMatch(Integer.parseInt(this.city), this.country)) {
            throw new MatchException("wrong city with the country");
        }


    }

    public void checkComplete() throws IncompleteException {

        if (this.title == null || this.title.equals("")) {
            throw new IncompleteException("no title");
        }
        if (this.description == null || this.description.equals("")) {
            throw new IncompleteException("no description");
        }
        if (this.content == null || this.content.equals("")) {
            throw new IncompleteException("no content");
        }
        if (this.country == null || this.country.equals("")) {
            throw new IncompleteException("no country");
        }
        if (this.city == null || this.city.equals("")) {
            throw new IncompleteException("no city");
        }


    }

    public void checkType() throws TypeException {
        String type = this.photo.getContentType();
        if (!type.equalsIgnoreCase("image/gif") &&
                !type.equalsIgnoreCase("image/png") &&
                !type.equalsIgnoreCase("image/jpeg")) {
            throw new TypeException("try a jpeg or sth similar");
        }

    }

    public String generateFileName(Connection connection) {
        String extName = MyUtils.getExtName(this.photo.getSubmittedFileName());
        image imageDao = new imageDAO(connection);
        String filename;
        do {
            filename = MD5Utils.MD5(new SecureRandom().nextLong() + "") + "." + extName;
        } while (imageDao.imageExists(filename));
        this.filename = filename;
        return filename;

    }


}
