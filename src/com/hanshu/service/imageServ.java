package com.hanshu.service;

import com.hanshu.bean.Image;
import com.hanshu.bean.User;
import com.hanshu.DAO.image.image;
import com.hanshu.DAO.image.imageDAO;
import com.hanshu.others.Result;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class imageServ {
    private final Connection connection;

    public imageServ(Connection connection) {
        this.connection = connection;

    }
    public List<Image> getMostPopularNImages(int n) {
        try {
            Connection connection = this.connection;
            imageDAO imageDao = new imageDAO(connection);
            return imageDao.getMostPopularNImages(n);

        } catch (Exception exception) {
            return null;
        }


    }
    public List<Image> getMostFreshNImages(int n) {
        try {
            Connection connection = this.connection;
            imageDAO imageDao = new imageDAO(connection);
            return imageDao.getMostFreshNImages(n);

        } catch (Exception exception) {
            return null;
        }
    }

    public Result search(String howToSearch, String howToOrder, String input, int requestedPage, int pageSize) {
        try {
            imageDAO imageDao = new imageDAO(connection);
            List<Image> imageList = imageDao.search(howToSearch, howToOrder, input);

            return this.getSearchResult(imageList, requestedPage, pageSize);

        } catch (Exception e) {
            return null;
        }

    }


    public Result getMyPhotos(User user, int requestedPage, int pageSize) {
        try {

            imageDAO imageDao = new imageDAO(connection);

            List<Image> imageList = imageDao.getMyPhotos(user);

            return this.getSearchResult(imageList, requestedPage, pageSize);

        } catch (Exception e) {
            return null;
        }
    }


    public Result getFavor(User user, int requestedPage, int pageSize) {
        try {
            image imageDao = new imageDAO(connection);

            List<Image> imageList = imageDao.getFavor(user);

            return this.getSearchResult(imageList, requestedPage, pageSize);

        } catch (Exception e) {
            return null;
        }
    }


    private Result getSearchResult(List<Image> originalImageList, int requestedPage, int pageSize) {
        ArrayList<Image> subImageList = new ArrayList<>();
        Result searchResult = new Result();


        int maxPage = (int) Math.ceil((double) originalImageList.size() / pageSize);
        searchResult.setMaxPage(maxPage);

        requestedPage = Math.max(requestedPage, 1);
        requestedPage = Math.min(maxPage, requestedPage);

        searchResult.setRespondedPage(requestedPage);


        int start = pageSize * (requestedPage - 1);
        int end = Math.min(start + pageSize, originalImageList.size());
        for (int i = start; i < end; i++) {
            subImageList.add(originalImageList.get(i));
        }

        searchResult.setImageList(subImageList);

        return searchResult;

    }


    public Image getImage(int imageID) {
        image imageDao = new imageDAO(connection);
        return imageDao.getImage(imageID);
    }

    public Image getImage(User user, int imageID) {
        image imageDao = new imageDAO(connection);
        if (user == null) return null;
        return imageDao.getImage(user, imageID);
    }


}
