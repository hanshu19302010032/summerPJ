package com.hanshu.servlet.other;

import com.alibaba.fastjson.JSON;
import com.hanshu.bean.*;
import com.hanshu.DAO.ImageFavor.favor;
import com.hanshu.DAO.ImageFavor.favorDAO;
import com.hanshu.DAO.friendRecord.record;
import com.hanshu.DAO.friendRecord.recordDAO;
import com.hanshu.DAO.message.message;
import com.hanshu.DAO.message.messageDAO;
import com.hanshu.DAO.user.user;
import com.hanshu.DAO.user.userDAO;
import com.hanshu.exception.MatchException;
import com.hanshu.exception.IncompleteException;
import com.hanshu.exception.TypeException;
import com.hanshu.others.Action;
import com.hanshu.others.Result;
import com.hanshu.others.Upload;
import com.hanshu.service.*;
import com.hanshu.utils.MyUtils;
import com.hanshu.utils.PicReduce;
import org.apache.commons.dbutils.DbUtils;


import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MultipartConfig()
@WebServlet("/UserServlet")


public class userServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("method").equals("register")) {

                register(request, response);

                return;
            }
            if (request.getParameter("method").equals("login")) {

                login(request, response);

                return;
            }
            if (request.getParameter("method").equals("unfavor")) {
                unfavor(request, response);
                return;

            }
            if (request.getParameter("method").equals("upload")) {
                upload(request, response);
                return;
            }
            if (request.getParameter("method").equals("delete")) {
                delete(request, response);
                return;
            }
            if (request.getParameter("method").equals("modify")) {
                modify(request, response);
                return;
            }
            if (request.getParameter("method").equals("getUid")) {
                getUid(request, response);
                return;
            }
            if (request.getParameter("method").equals("searchFriendToAdd")) {
                searchFriendToAdd(request, response);
                return;
            }
            if (request.getParameter("method").equals("addfriend")) {
                addfriend(request, response);
                return;
            }
            if (request.getParameter("method").equals("getFriendRequests")) {
                getFriendRequests(request, response);
                return;
            }
            if (request.getParameter("method").equals("refuseRequest")) {
                refuseRequest(request, response);
                return;
            }
            if (request.getParameter("method").equals("acceptRequest")) {
                acceptRequest(request, response);
                return;
            }
            if (request.getParameter("method").equals("getSysMessage")) {
                getSysMessage(request, response);
                return;
            }
            if (request.getParameter("method").equals("readMessage")) {
                readMessage(request, response);
                return;
            }
            if (request.getParameter("method").equals("getMyFriend")) {
                getMyFriend(request, response);
                return;
            }
            if (request.getParameter("method").equals("deleteFriend")) {
                deleteFriend(request, response);
                return;
            }
            if (request.getParameter("method").equals("othersfavor")) {
                othersfavor(request, response);
                return;
            }
            if (request.getParameter("method").equals("comment")) {
                comment(request, response);
                return;
            }
            if (request.getParameter("method").equals("favorComment")) {
                favorComment(request, response);
                return;
            }
            if (request.getParameter("method").equals("cancelFavorComment")) {
                cancelFavorComment(request, response);
                return;
            }
            if (request.getParameter("method").equals("setCanBeSeenFavor")) {
                setCanBeSeenFavor(request, response);
            }


        } catch (Exception ignored) {

        }


    }


    private void register(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();
            userServ userServ = new userServ(connection, request);
            Action registerResult = userServ.register(
                    request.getParameter("username"), request.getParameter("email"), request.getParameter("password1"),
                    request.getParameter("password2"), request.getParameter("captcha")
            );
            DbUtils.close(connection);


            PrintWriter out = response.getWriter();
            out.println(JSON.toJSON(registerResult));
        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }


    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();
            userServ userServ = new userServ(connection, request);
            Action loginResult = userServ.tryLogin(request.getParameter("username"), request.getParameter("password"), request.getParameter("captcha"));

            PrintWriter out = response.getWriter();
            Object o = JSON.toJSON(loginResult);
            out.println(o);
        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }


    }


    private void unfavor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            userServ userServ = new userServ(connection, httpServletRequest);
            User user = userServ.tryAutoLogin();
            Action actionResult = userServ.unfavorImage(user, Integer.parseInt(httpServletRequest.getParameter("imageID")));

            PrintWriter out = httpServletResponse.getWriter();
            out.println(JSON.toJSON(actionResult));

        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }

    }


    private void upload(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            PrintWriter out = response.getWriter();
            DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();


            userServ userServ = new userServ(connection, request);
            User user = userServ.tryAutoLogin();
            if (user == null) {
                out.println(JSON.toJSON(new Action(false, "please login again!")));
                return;

            }


            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String content = request.getParameter("content");
            String country = request.getParameter("country");
            String city = request.getParameter("city");
            Part photo = request.getPart("photo");

            Upload uploadPhotoInfo = new Upload(title, description, city, country, content, photo);

            uploadPhotoInfo.cleanXSS();

            try {
                uploadPhotoInfo.checkComplete();
            } catch (IncompleteException incompleteException) {
                out.println(JSON.toJSON(new Action(false, incompleteException.getMessage())));
                return;
            }


            try {
                uploadPhotoInfo.checkCountryAndCity(connection);
            } catch (MatchException matchException) {
                out.println(JSON.toJSON(new Action(false, matchException.getMessage())));
                return;
            }


            try {
                uploadPhotoInfo.checkType();
            } catch (TypeException typeException) {
                out.println(JSON.toJSON(new Action(false, typeException.getMessage())));
                return;
            }

            uploadPhotoInfo.generateFileName(connection);

            imageServ imageServ = new imageServ(connection);

            Image image = new Image(
                    uploadPhotoInfo.getTitle(), uploadPhotoInfo.getDescription(), 0, 0, Integer.parseInt(uploadPhotoInfo.getCity()),
                    uploadPhotoInfo.getCountry(), user.getUid(), uploadPhotoInfo.getFilename(), uploadPhotoInfo.getContent(), new Timestamp(System.currentTimeMillis())

            );


            connection.setAutoCommit(false);

            try {
                Action insertResult = userServ.insertImageToDB(user, image);

                String largeFilePlace = getServletContext().getRealPath("/photos/large/") + uploadPhotoInfo.getFilename();
                String mediumFilePlace = getServletContext().getRealPath("/photos/medium/") + uploadPhotoInfo.getFilename();
                String smallFilePlace = getServletContext().getRealPath("/photos/small/") + uploadPhotoInfo.getFilename();

                uploadPhotoInfo.getPhoto().write(largeFilePlace);

                PicReduce.saveMinPhoto(largeFilePlace, mediumFilePlace, 800, 1);
                PicReduce.saveMinPhoto(largeFilePlace, smallFilePlace, 200, 1);

                if (!insertResult.isSuccess()) {
                    File largeFile = new File(largeFilePlace);
                    File mediumFile = new File(mediumFilePlace);
                    File smallFile = new File(smallFilePlace);
                    largeFile.delete();
                    mediumFile.delete();
                    smallFile.delete();
                    out.println(JSON.toJSON(new Action(false, "upload failed")));
                    return;
                }
            } catch (Exception exception) {
                connection.rollback();
                File largeFile = new File(getServletContext().getRealPath("/photos/large/") + uploadPhotoInfo.getFilename());
                File mediumFile = new File(getServletContext().getRealPath("/photos/medium/") + uploadPhotoInfo.getFilename());
                File smallFile = new File(getServletContext().getRealPath("/photos/small/") + uploadPhotoInfo.getFilename());
                largeFile.delete();
                mediumFile.delete();
                smallFile.delete();
                out.println(JSON.toJSON(new Action(false, "upload failed")));
                return;
            }

            connection.commit();
            connection.setAutoCommit(true);
            out.println(JSON.toJSON(new Action(true, "upload succeeded")));


        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }


    }


    private void delete(HttpServletRequest request, HttpServletResponse response) {
        DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
        Connection connection = null;
        try {
            PrintWriter out = response.getWriter();
            connection = dataSource.getConnection();
            userServ userServ = new userServ(connection, request);
            imageServ imageServ = new imageServ(connection);
            favor imageFavorDao = new favorDAO(connection);

            int imageID = Integer.parseInt(request.getParameter("imageID"));

            Image image = imageServ.getImage(imageID);

            User user = userServ.tryAutoLogin();

            if (user == null) {
                out.println(JSON.toJSON(new Action(false, "please log in!")));
            }

            connection.setAutoCommit(false);

            Action deleteResult = userServ.deleteImageFromDB(user, imageID);
            boolean deleteFavorResult = imageFavorDao.deleteAllImageFavor(imageID);


            if (!deleteResult.isSuccess()) {
                out.println(JSON.toJSON(deleteResult));
                return;
            }

            String filename = image.getPath();

            String largeFilePlace = getServletContext().getRealPath("/photos/large/") + filename;
            String mediumFilePlace = getServletContext().getRealPath("/photos/medium/") + filename;
            String smallFilePlace = getServletContext().getRealPath("/photos/small/") + filename;
            File largeFile = new File(largeFilePlace);
            File mediumFile = new File(mediumFilePlace);
            File smallFile = new File(smallFilePlace);

            largeFile.delete();
            mediumFile.delete();
            smallFile.delete();

            boolean deleteLargeResult =
                    !largeFile.exists();
            boolean deleteMediumResult = !largeFile.exists();
            boolean deleteSmallResult = !largeFile.exists();

            if (deleteLargeResult && deleteMediumResult && deleteSmallResult && deleteResult.isSuccess() && deleteFavorResult) {
                connection.commit();
                out.println(JSON.toJSON(new Action(true, "delete succeeded")));
                connection.setAutoCommit(true);

            } else {
                connection.rollback();
                out.println(JSON.toJSON(new Action(false, "delete failed")));
                connection.setAutoCommit(true);
            }

        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        } finally {
            DbUtils.closeQuietly(connection);
        }

    }


    private void modify(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            PrintWriter out = response.getWriter();
            DataSource dataSource = (DataSource) this.getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();


            userServ userServ = new userServ(connection, request);
            imageServ imageServ = new imageServ(connection);
            User user = userServ.tryAutoLogin();
            int imageID = Integer.parseInt(request.getParameter("imageID"));


            if (user == null) {
                out.println(JSON.toJSON(new Action(false, "please log in!")));
                return;
            }

            if (!userServ.hasTheImage(user, imageID)) {
                out.println(JSON.toJSON(new Action(false, "you don't have this photo,thus fail to modify")));
                return;
            }


            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String content = request.getParameter("content");
            String country = request.getParameter("country");
            String city = request.getParameter("city");
            Part photo = request.getPart("photo");

            Upload uploadPhotoInfo = new Upload(title, description, city, country, content, photo);

            uploadPhotoInfo.cleanXSS();

            try {
                uploadPhotoInfo.checkComplete();
            } catch (IncompleteException incompleteException) {
                out.println(JSON.toJSON(new Action(false, "you don't have this photo,thus fail to modify")));
                return;
            }

            try {
                uploadPhotoInfo.checkCountryAndCity(connection);
            } catch (MatchException matchException) {
                out.println(JSON.toJSON(new Action(false, matchException.getMessage())));
                return;
            }

            boolean photoChanged = !(uploadPhotoInfo.getPhoto().getSubmittedFileName() == null);

            if (photoChanged) {
                try {
                    uploadPhotoInfo.checkType();
                } catch (TypeException typeException) {
                    out.println(JSON.toJSON(new Action(false, typeException.getMessage())));
                    return;
                }

                Image originalImage = imageServ.getImage(imageID);

                String originalFilename = originalImage.getPath();


                originalImage.setTitle(uploadPhotoInfo.getTitle());
                originalImage.setDescription(uploadPhotoInfo.getTitle());
                originalImage.setContent(uploadPhotoInfo.getContent());
                originalImage.setCountry_RegionCodeISO(uploadPhotoInfo.getCountry());
                originalImage.setCityCode(Integer.parseInt(uploadPhotoInfo.getCity()));
                originalImage.setPath(uploadPhotoInfo.generateFileName(connection));
                originalImage.setDateReleased(new Timestamp(System.currentTimeMillis()));

                connection.setAutoCommit(false);


                Action modifyResult = userServ.modifyImageInDB(user, imageID, originalImage);

                if (!modifyResult.isSuccess()) {
                    out.println(JSON.toJSON(modifyResult));
                    connection.setAutoCommit(true);
                    return;
                }


                String largeFilePlace = getServletContext().getRealPath("/photos/large/") + uploadPhotoInfo.getFilename();
                String originalLargeFilePlace = getServletContext().getRealPath("/photos/large/") + originalFilename;

                String mediumFilePlace = getServletContext().getRealPath("/photos/medium/") + uploadPhotoInfo.getFilename();
                String originalMediumFilePlace = getServletContext().getRealPath("/photos/medium/") + originalFilename;

                String smallFilePlace = getServletContext().getRealPath("/photos/small/") + uploadPhotoInfo.getFilename();
                String originalSmallFilePlace = getServletContext().getRealPath("/photos/small/") + originalFilename;

                try {
                    uploadPhotoInfo.getPhoto().write(largeFilePlace);
                    PicReduce.saveMinPhoto(largeFilePlace, mediumFilePlace, 800, 1);
                    PicReduce.saveMinPhoto(largeFilePlace, smallFilePlace, 200, 1);

                    File originalLargeFile = new File(originalLargeFilePlace);
                    File originalMediumFile = new File(originalMediumFilePlace);
                    File originalSmallFile = new File(originalSmallFilePlace);

                    originalLargeFile.delete();
                    originalMediumFile.delete();
                    originalSmallFile.delete();

                    connection.commit();
                    out.println(JSON.toJSON(new Action(true, "modify succeeded!")));
                } catch (Exception e) {
                    new File(largeFilePlace).delete();
                    new File(smallFilePlace).delete();
                    new File(mediumFilePlace).delete();
                    connection.rollback();
                    out.println(JSON.toJSON(new Action(false, "modify failed")));
                } finally {
                    connection.setAutoCommit(true);
                }


            } else {
                Image originalImage = imageServ.getImage(imageID);
                originalImage.setTitle(uploadPhotoInfo.getTitle());
                originalImage.setDescription(uploadPhotoInfo.getTitle());
                originalImage.setContent(uploadPhotoInfo.getContent());
                originalImage.setCountry_RegionCodeISO(uploadPhotoInfo.getCountry());
                originalImage.setCityCode(Integer.parseInt(uploadPhotoInfo.getCity()));
                originalImage.setDateReleased(new Timestamp(System.currentTimeMillis()));

                Action modifyResult = userServ.modifyImageInDB(user, imageID, originalImage);

                out.println(JSON.toJSON(modifyResult));
            }


        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        } finally {
            DbUtils.closeQuietly(connection);
        }

    }

    private void getUid(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();

            userServ userServ = new userServ(connection, request);

            User user = userServ.tryAutoLogin();

            if (user == null) return;

            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put("uid", user.getUid());

            PrintWriter out = response.getWriter();

            out.println(JSON.toJSON(hashMap));

        } catch (Exception ignored) {
        } finally {
            DbUtils.closeQuietly(connection);
        }

    }

    private void searchFriendToAdd(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();

            userServ userServ = new userServ(connection, request);

            User user = userServ.tryAutoLogin();

            if (user == null) return;

            String username = request.getParameter("username");
            int requestedPage = Integer.parseInt(request.getParameter("requestedPage"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));

            Result userSearchResult = userServ.searchUserToAddFriend(
                    username, requestedPage, pageSize
            );

            PrintWriter out = response.getWriter();

            out.println(JSON.toJSON(userSearchResult));

        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }

    }


    private void addfriend(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            PrintWriter out = response.getWriter();
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();

            userServ userServ = new userServ(connection, request);
            user userDao = new userDAO(connection);

            User user = userServ.tryAutoLogin();

            if (user == null) {
                out.println(JSON.toJSON(new Action(false, "please login")));
                return;
            }

            String targetUsername = request.getParameter("username");

            User targetUser = userDao.getUser(targetUsername);

            if (targetUser == null) {
                out.println(JSON.toJSON(new Action(false, "the user does not exist")));
                return;
            }

            int myUID = user.getUid();
            int targetUID = targetUser.getUid();

            requestServ requestServ = new requestServ(connection);

            Action addRequestResult = requestServ.addRequest(new Request(myUID, targetUID));

            out.println(JSON.toJSON(addRequestResult));


        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }


    }

    private void getFriendRequests(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            PrintWriter out = response.getWriter();
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();

            userServ userServ = new userServ(connection, request);

            User user = userServ.tryAutoLogin();

            if (user == null) {
                out.println(JSON.toJSON(new Action(false, "please login")));
                return;
            }

            int uid = user.getUid();

            requestServ requestServ = new requestServ(connection);

            List<Request> friendRequestList = requestServ.getFriendRequestsIReceived(uid);

            out.println(JSON.toJSON(friendRequestList));

        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }

    }

    private void refuseRequest(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            PrintWriter out = response.getWriter();
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();

            userServ userServ = new userServ(connection, request);
            requestServ requestServ = new requestServ(connection);


            User user = userServ.tryAutoLogin();
            int requestID = Integer.parseInt(request.getParameter("requestID"));
            if (user == null) {
                out.println(JSON.toJSON(new Action(false, "please login")));
                return;
            }


            Action refuseResult = requestServ.refuseRequest(user, requestID);


            out.println(JSON.toJSON(refuseResult));

        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }

    }

    private void acceptRequest(HttpServletRequest request, HttpServletResponse response) {
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

            int requestID = Integer.parseInt(request.getParameter("requestID"));

            requestServ requestServ = new requestServ(connection);

            Action acceptResult = requestServ.acceptRequest(me, requestID);

            out.println(JSON.toJSON(acceptResult));

        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }

    }

    private void getSysMessage(HttpServletRequest request, HttpServletResponse response) {
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

            message sysMessageDao = new messageDAO(connection);

            List<Message> sysMessageList = sysMessageDao.getMyMessage(me.getUid());

            out.println(JSON.toJSON(sysMessageList));
        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }

    }

    private void readMessage(HttpServletRequest request, HttpServletResponse response) {
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

            messageServ messageServ = new messageServ(connection);

            int messageID = Integer.parseInt(request.getParameter("messageID"));

            messageServ.deleteMessage(me.getUid(), messageID);

        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private void getMyFriend(HttpServletRequest request, HttpServletResponse response) {
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

            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            int requestedPage = Integer.parseInt(request.getParameter("requestedPage"));

            Result searchResult = userServ.searchMyFriend(me.getUid(), requestedPage, pageSize);

            out.println(JSON.toJSON(searchResult));

        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private void deleteFriend(HttpServletRequest request, HttpServletResponse response) {
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

            recordServ recordServ = new recordServ(connection);

            int targetUid = Integer.parseInt(request.getParameter("targetUid"));

            boolean deleteFriendResult = recordServ.deleteFriend(me.getUid(), targetUid);

            if (deleteFriendResult) {
                message sysMessageDao = new messageDAO(connection);
                sysMessageDao.addSysMessage(new Message(targetUid, me.getUsername() + "deleted"));

                out.println(JSON.toJSON(new Action(true, "delete succeeded")));
            } else {
                out.println(JSON.toJSON(new Action(false, "delete failed")));
            }


        } catch (Exception ignored) {

        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private void othersfavor(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            connection = dataSource.getConnection();
            PrintWriter out = response.getWriter();

            userServ userServ = new userServ(connection, request);
            user userDao = new userDAO(connection);
            record friendRecordDao = new recordDAO(connection);

            User me = userServ.tryAutoLogin();

            if (me == null) {
                out.println(JSON.toJSON(new Action(false, "please login")));
                return;
            }

            String othersusername = request.getParameter("username");
            User other = userDao.getUser(othersusername);

            if (other == null) {
                out.println(JSON.toJSON(new Action(false, "the user does not exist")));
                return;
            }
            if (other.getCanBeSeenFavors() != 1) {
                out.println(JSON.toJSON(new Action(false, "you can not see!")));
                return;
            }


            Record friendRecord = friendRecordDao.getFriendRecord(other.getUid(), me.getUid());
            if (friendRecord == null) {
                out.println(JSON.toJSON(new Action(false, "you can not see!")));
                return;
            }

            imageServ imageServ = new imageServ(connection);

            int requestedPage = Integer.parseInt(request.getParameter("requestedPage"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));

            Result searchResult = imageServ.getFavor(other, requestedPage, pageSize);

            out.println(JSON.toJSON(searchResult));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private void comment(HttpServletRequest request, HttpServletResponse response) {
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
            String text = MyUtils.cleanXSS(request.getParameter("comment"));

            Comment newComment = new Comment(imageID, me.getUid(), text, new Timestamp(System.currentTimeMillis()));

            commentServ commentServ = new commentServ(connection);

            Action commentResult = commentServ.addComment(newComment);

            out.println(JSON.toJSON(commentResult));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private void favorComment(HttpServletRequest request, HttpServletResponse response) {
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

            int commentID = Integer.parseInt(request.getParameter("commentID"));


            Action favorCommentResult = userServ.favorComment(me.getUid(), commentID);

            out.println(JSON.toJSON(favorCommentResult));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }

    }

    private void cancelFavorComment(HttpServletRequest request, HttpServletResponse response) {
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

            int commentID = Integer.parseInt(request.getParameter("commentID"));


            Action favorCommentResult = userServ.cancelFavorComment(me.getUid(), commentID);

            out.println(JSON.toJSON(favorCommentResult));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }

    }

    private void setCanBeSeenFavor(HttpServletRequest request, HttpServletResponse response) {
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

            int canBeSeenFavor = Integer.parseInt(request.getParameter("canBeSeenFavor"));

            Action sendResult = userServ.setCanBeSeenFavor(me.getUid(), canBeSeenFavor);

            out.println(JSON.toJSON(sendResult));


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

}
