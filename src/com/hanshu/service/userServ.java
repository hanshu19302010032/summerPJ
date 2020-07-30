package com.hanshu.service;

import com.hanshu.bean.*;
import com.hanshu.DAO.ImageFavor.favor;
import com.hanshu.DAO.ImageFavor.favorDAO;
import com.hanshu.DAO.comment.comment;
import com.hanshu.DAO.comment.commentDAO;
import com.hanshu.DAO.commentFavor.commentFavor;
import com.hanshu.DAO.commentFavor.commentFavorDAO;
import com.hanshu.DAO.image.image;
import com.hanshu.DAO.image.imageDAO;
import com.hanshu.DAO.user.user;
import com.hanshu.DAO.user.userDAO;
import com.hanshu.others.Action;
import com.hanshu.others.Result;
import com.hanshu.utils.MD5Utils;
import com.hanshu.utils.MyUtils;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class userServ {
    private HttpSession httpSession;
    private final Connection connection;
    private final HttpServletRequest request;

    public userServ(Connection connection, HttpServletRequest request) {
        this.request = request;
        this.httpSession = request.getSession();
        this.connection = connection;
    }


    public Action register(String username, String email, String password1, String password2, String captchaInput) {
        try {
            if (!MyUtils.cleanXSS(username).equals(username)
                    || !MyUtils.cleanXSS(email).equals(email)
                    || !MyUtils.cleanXSS(password1).equals(password1)
                    || !MyUtils.cleanXSS(password2).equals(password2)
            ) {
                return new Action(false, "register failed");
            }
            if (!captchaInput.equals((String) httpSession.getAttribute("captcha"))) {
                return new Action(false, "wrong code");
            }

            if (!(username.length() >= 4 && username.length() <= 15)) {
                return new Action(false, "the length of username must be 4-15");
            }
            if (!password1.equals(password2)) {
                return new Action(false, "two different passwords");
            }
            if (!email.matches("([A-Za-z0-9_\\-.])+@([A-Za-z0-9_\\-.])+\\.([A-Za-z]{2,4})")) {
                return new Action(false, "wrong email address");
            }


            Connection connection = this.connection;

            userDAO userDao = new userDAO(connection);

            int salt = new SecureRandom().nextInt();
            String saltedPassword = MD5Utils.MD5((String) password1 + salt);

            request.changeSessionId();

            Action result = userDao.insertAUser(new User(username, email, saltedPassword, 1,
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    salt + "", httpSession.getId()
            ));

            DbUtils.close(connection);
            if (result.isSuccess()) {
                httpSession.setAttribute("username", username);
            }
            return result;
        } catch (Exception e) {
            return new Action(false, "register failed");
        }


    }

    public Action tryLogin(String username, String password, String captchaInput) {
        try {
            if (!captchaInput.equals((String) httpSession.getAttribute("captcha"))) {
                return new Action(false, "wrong code");
            }

            Connection connection = this.connection;
            userDAO userDao = new userDAO(connection);

            request.changeSessionId();

            Action result = userDao.tryLogin(username, password, request.getSession().getId());
            if (result.isSuccess()) {
                httpSession.setAttribute("username", username);
            }

            return result;
        } catch (Exception e) {
            return new Action(false, "login failed");
        }

    }


    public User tryAutoLogin() {
        try {
            Connection connection = this.connection;
            userDAO userDao = new userDAO(connection);
            return userDao.tryAutoLogin((String) httpSession.getAttribute("username"), httpSession.getId());

        } catch (Exception exception) {
            return null;
        }


    }


    public boolean hasFavoredTheImage(User user, int imageID) {
        try {
            favor imageFavorDao = new favorDAO(this.connection);
            return imageFavorDao.hasLikedTheImage(user, imageID);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasTheImage(User user, int imageID) {
        try {
            image imageDao = new imageDAO(connection);
            return imageDao.imageExists(user, imageID);

        } catch (Exception e) {
            return false;
        }

    }


    public Action favorImage(User user, int imageID) {
        if (user == null) return new Action(false, "please login");
        image imageDao = new imageDAO(connection);
        if (imageDao.imageExists(imageID)) return new Action(false, "the photo does not exist");
        try {
            boolean hasFavoredTheImage = this.hasFavoredTheImage(user, imageID);
            if (hasFavoredTheImage) return new Action(false, "have collected");

            favor imageFavorDao = new favorDAO(connection);

            boolean success = imageFavorDao.likeImage(user, imageID);
            if (success) return new Action(true, "collect succeeded");
            return new Action(false, "collect failed");

        } catch (Exception e) {
            return new Action(false, "collect failed");
        }
    }


    public Action unfavorImage(User user, int imageID) {
        if (user == null) return new Action(false, "please login");
        image imageDao = new imageDAO(connection);
        if (imageDao.imageExists(imageID)) return new Action(false, "the photo does not exist");
        try {
            boolean hasFavoredTheImage = this.hasFavoredTheImage(user, imageID);
            if (!hasFavoredTheImage) return new Action(false, "have not collected");

            favor imageFavorDao = new favorDAO(connection);

            boolean success = imageFavorDao.unlikeImage(user, imageID);
            if (success) return new Action(true, "abolish succeeded");
            return new Action(false, "abolish failed");

        } catch (Exception e) {
            return new Action(false, "abolish failed");
        }
    }

    public Action insertImageToDB(User user, Image image) {
        com.hanshu.DAO.image.image imageDao = new imageDAO(connection);
        if (user == null) return new Action(false, "please log in");
        return imageDao.insertImage(user, image);
    }

    public Action deleteImageFromDB(User user, int imageID) {
        image imageDao = new imageDAO(connection);
        if (!imageDao.imageExists(user, imageID)) return new Action(false, "you don't have it");
        if (user == null) return new Action(false, "please login");
        return imageDao.deleteImage(imageID);
    }

    public Action modifyImageInDB(User user, int imageID, Image image) {
        com.hanshu.DAO.image.image imageDao = new imageDAO(connection);
        if (!imageDao.imageExists(user, imageID)) return new Action(false, "you don't have it");
        if (user == null) return new Action(false, "please login");

        return imageDao.modifyImage(user, imageID, image);

    }

    public Result searchUserToAddFriend(String username, int requestedPage, int pageSize) {

        user userDao = new userDAO(this.connection);
        List<User> userList = userDao.searchUserToAddFriend(username);

        return getSearchResult(userList, requestedPage, pageSize);

    }

    public Result searchMyFriend(int myuid, int requestedPage, int pageSize) {
        user userDao = new userDAO(this.connection);
        List<User> myFriendList = userDao.getMyFriendList(myuid);

        return getSearchResult(myFriendList, requestedPage, pageSize);

    }

    public Action favorComment(int uid, int commentID) {
        try {
            commentFavor commentFavorDao = new commentFavorDAO(connection);
            comment commentDao = new commentDAO(connection);

            Comment comment = commentDao.getComment(commentID);
            if (comment == null) return new Action(false, "the comment does not exist");

            boolean hasFavored = commentFavorDao.favorExists(uid, commentID);
            if (hasFavored) return new Action(false, "you have favored");

            boolean success = commentFavorDao.addCommentFavor(new CommentFavor(commentID, uid));

            if (success) {
                return new Action(true, "favor succeeded");
            } else {
                return new Action(false, "favor failed");
            }


        } catch (Exception e) {
            return new Action(false, "favor failed");
        }

    }

    public Action cancelFavorComment(int uid, int commentID) {
        try {
            commentFavor commentFavorDao = new commentFavorDAO(connection);
            comment commentDao = new commentDAO(connection);

            Comment comment = commentDao.getComment(commentID);
            if (comment == null) return new Action(false, "the comment does not exist");

            if (comment.getUid() != uid) return new Action(false, "you can't abolish other's favor");

            boolean hasFavored = commentFavorDao.favorExists(uid, commentID);
            if (!hasFavored) return new Action(false, "you haven't favored yet");

            boolean success = commentFavorDao.deleteCommentFavor(uid, commentID);

            if (success) {
                return new Action(true, "abolish succeeded");
            } else {
                return new Action(false, "abolish failed");
            }


        } catch (Exception e) {
            return new Action(false, "abolish failed");
        }
    }

    public Action setCanBeSeenFavor(int uid, int canBeSeenFavor) {
        if (canBeSeenFavor != 0 && canBeSeenFavor != 1) {
            return new Action(false, "wrong parameter");
        }

        user userDao = new userDAO(connection);

        boolean success = userDao.setCanBeSeenFavor(uid, canBeSeenFavor);

        if (success) {
            return new Action(true, "set up succeeded");
        } else {
            return new Action(false, "set up failed");
        }

    }


    private Result getSearchResult(List<User> originalUserList, int requestedPage, int pageSize) {

        ArrayList<User> subUserList = new ArrayList<>();

        Result searchResult = new Result();

        int maxPage = (int) Math.ceil((double) originalUserList.size() / pageSize);
        searchResult.setMaxPage(maxPage);

        requestedPage = Math.max(requestedPage, 1);
        requestedPage = Math.min(maxPage, requestedPage);

        searchResult.setRespondedPage(requestedPage);

        int start = pageSize * (requestedPage - 1);
        int end = Math.min(start + pageSize, originalUserList.size());
        for (int i = start; i < end; i++) {
            subUserList.add(originalUserList.get(i));
        }

        searchResult.setUserList(subUserList);

        return searchResult;

    }


}
