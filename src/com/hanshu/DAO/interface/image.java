package com.hanshu.DAO.image;

import com.hanshu.bean.Image;
import com.hanshu.bean.User;
import com.hanshu.others.Action;

import java.util.List;

public interface image {
     List<Image> getMostPopularNImages(int n);

     List<Image> getMostFreshNImages(int n);

     List<Image> search(String howToSearch, String howToOrder, String input);

     List<Image> getMyPhotos(User user);

     List<Image> getFavor(User user);

     Image getImage(int imageID);

     boolean imageExists(int imageID);

     boolean imageExists(String fileName);

     boolean imageExists(User user,int imageID);

     Action insertImage(User user, Image image);

     Action deleteImage(int imageID);

     Image getImage(User user,int imageID);

     Action modifyImage(User user, int imageID, Image image);


}


