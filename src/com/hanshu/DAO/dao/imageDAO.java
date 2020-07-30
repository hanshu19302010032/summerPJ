package com.hanshu.DAO.image;

import com.hanshu.bean.Image;
import com.hanshu.bean.User;
import com.hanshu.DAO.GenericDao;
import com.hanshu.others.Action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class imageDAO extends GenericDao<Image> implements image {
    private Connection connection;

    public imageDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Image> getMostPopularNImages(int n) {
        try {
            String sql = "select travelimagefavor.ImageID,Title,Description,Latitude,Longitude," +
                    "CityCode,Country_RegionCodeISO,travelimage.UID,Content,PATH,DateReleased from travelimagefavor inner join travelimage on travelimage.ImageID=travelimagefavor.ImageID group by ImageID order by count(travelimagefavor.ImageID) desc limit ?";
            return this.queryForList(connection, sql, n);
        } catch (Exception exception) {
            return null;
        }
    }


    @Override
    public List<Image> getMostFreshNImages(int n) {
        try {
            String sql = "select UserName,ImageID, Title, Description, Latitude, Longitude, CityCode, Country_RegionCodeISO, travelimage.UID, PATH, Content, DateReleased from travelimage inner join traveluser t on travelimage.UID = t.UID order by DateReleased desc limit ?";
            return this.queryForList(connection, sql, n);


        } catch (Exception exception) {
            return null;

        }

    }


    @Override
    public List<Image> search(String howToSearch, String howToOrder, String input) {
        String sql = null;
        if (howToOrder.equals("popularity")) {
            if (howToSearch.equals("title")) {
                sql = "select travelimage.ImageID,\n" +
                        "       count(travelimagefavor.ImageID) as favorCount,\n" +
                        "       Title,\n" +
                        "       Description,\n" +
                        "       Longitude,\n" +
                        "       Latitude,\n" +
                        "       CityCode,\n" +
                        "       Country_RegionCodeISO,\n" +
                        "       travelimage.UID,\n" +
                        "       PATH,\n" +
                        "       Content,\n" +
                        "       DateReleased\n" +
                        "from travelimagefavor\n" +
                        "         right join travelimage on travelimage.ImageID = travelimagefavor.ImageID where Title regexp ?\n" +
                        "group by ImageID\n" +
                        "order by favorCount desc";

            } else if (howToSearch.equals("content")) {
                sql = "select travelimage.ImageID,\n" +
                        "       count(travelimagefavor.ImageID) as favorCount,\n" +
                        "       Title,\n" +
                        "       Description,\n" +
                        "       Longitude,\n" +
                        "       Latitude,\n" +
                        "       CityCode,\n" +
                        "       Country_RegionCodeISO,\n" +
                        "       travelimage.UID,\n" +
                        "       PATH,\n" +
                        "       Content,\n" +
                        "       DateReleased\n" +
                        "from travelimagefavor\n" +
                        "         right join travelimage on travelimage.ImageID = travelimagefavor.ImageID where Content regexp ?\n" +
                        "group by ImageID\n" +
                        "order by favorCount desc";

            }

        } else if (howToOrder.equals("time")) {
            if (howToSearch.equals("title")) {
                sql = "select travelimage.ImageID,\n" +
                        "       count(travelimagefavor.ImageID) as favorCount,\n" +
                        "       Title,\n" +
                        "       Description,\n" +
                        "       Longitude,\n" +
                        "       Latitude,\n" +
                        "       CityCode,\n" +
                        "       Country_RegionCodeISO,\n" +
                        "       travelimage.UID,\n" +
                        "       PATH,\n" +
                        "       Content,\n" +
                        "       DateReleased\n" +
                        "from travelimagefavor\n" +
                        "         right join travelimage on travelimage.ImageID = travelimagefavor.ImageID where Title regexp ?\n" +
                        "group by ImageID\n" +
                        "order by DateReleased desc";

            } else if (howToSearch.equals("content")) {
                sql = "select travelimage.ImageID,\n" +
                        "       count(travelimagefavor.ImageID) as favorCount,\n" +
                        "       Title,\n" +
                        "       Description,\n" +
                        "       Longitude,\n" +
                        "       Latitude,\n" +
                        "       CityCode,\n" +
                        "       Country_RegionCodeISO,\n" +
                        "       travelimage.UID,\n" +
                        "       PATH,\n" +
                        "       Content,\n" +
                        "       DateReleased\n" +
                        "from travelimagefavor\n" +
                        "         right join travelimage on travelimage.ImageID = travelimagefavor.ImageID where Content regexp ?\n" +
                        "group by ImageID\n" +
                        "order by DateReleased desc";

            }
        }
        try {
            return this.queryForList(this.connection, sql, input);
        } catch (SQLException sqlException) {
            return null;
        }

    }


    @Override
    public List<Image> getMyPhotos(User user) {
        String sql = "select * from travelimage where uid=?";
        try {
            return this.queryForList(this.connection, sql, user.getUid());
        } catch (SQLException sqlException) {
            return null;
        }
    }

    @Override
    public List<Image> getFavor(User user) {
        String sql = "select travelimage.ImageID,Title,Description,Longitude,Latitude,CityCode,Country_RegionCodeISO,travelimage.UID,PATH,Content,DateReleased from travelimagefavor inner join travelimage  on travelimagefavor.ImageID = travelimage.ImageID    where travelimagefavor.UID=?";
        try {
            return this.queryForList(connection, sql, user.getUid());
        } catch (SQLException sqlException) {
            return null;

        }
    }

    @Override
    public Image getImage(int imageID) {
        String sql = "select\n" +
                "travelimage.ImageID,count(travelimagefavor.ImageID) as favorCount,Title,Description,\n" +
                "       travelimage.Latitude,travelimage.Longitude,\n" +
                "       CityCode,AsciiName,travelimage.Country_RegionCodeISO,geocountries_regions.Country_RegionName,PATH,Content,DateReleased,UserName\n" +
                "from\n" +
                "(travelimagefavor right join travelimage on travelimage.ImageID=travelimagefavor.ImageID)\n" +
                "left join geocities on CityCode=geocities.GeoNameID\n" +
                "left join geocountries_regions on travelimage.Country_RegionCodeISO=geocountries_regions.ISO\n" +
                "left join traveluser on travelimage.UID=traveluser.UID\n" +
                "where travelimage.ImageID=?\n" +
                "group by ImageID";
        try {
            return this.queryForOne(connection, sql, imageID);
        } catch (SQLException sqlException) {
            return null;
        }


    }

    @Override
    public boolean imageExists(int imageID) {
        return this.getImage(imageID) == null;
    }

    @Override
    public Action insertImage(User user, Image image) {
        if (user == null) {
            return new Action(false, "please login");
        }
        String sql = "insert into travelimage (Title, Description, Latitude, Longitude, CityCode, Country_RegionCodeISO, UID, PATH, Content,\n" +
                "                         DateReleased)\n" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n";
        try {
            int rowsAffected = this.update(connection, sql,
                    image.getTitle(), image.getDescription(), image.getLatitude(), image.getLongitude(), image.getCityCode()
                    , image.getCountry_RegionCodeISO(), image.getUid(), image.getPath(), image.getContent(), new Timestamp(System.currentTimeMillis()));
            if (rowsAffected > 0) {
                return new Action(true, "success");
            } else {
                return new Action(false, "fail");
            }

        } catch (Exception e) {
            return new Action(false, "fail");
        }

    }

    @Override
    public Action deleteImage(int imageID) {
        try {
            String sql = "delete from travelimage where ImageID=?";
            int rowsAffected = this.update(connection, sql, imageID);
            if (rowsAffected > 0) {
                return new Action(true, "删除成功");
            }
            return new Action(false, "删除失败");

        } catch (Exception e) {
            return new Action(false, "删除失败");
        }
    }

    @Override
    public Image getImage(User user, int imageID) {
        try {
            int uid = user.getUid();
            String sql = "select imageid, title, description, travelimage.latitude, travelimage.longitude, citycode, travelimage.country_regioncodeiso, uid, path, content, datereleased,AsciiName from travelimage inner join geocities on geocities.GeoNameID=travelimage.CityCode  where uid=? and imageID=?";
            return this.queryForOne(connection, sql, uid, imageID);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Action modifyImage(User user, int imageID, Image image) {
        try {
            String sql = "update travelimage set Title=?,Description=?,CityCode=?,Country_RegionCodeISO=?,PATH=?,Content=?,DateReleased=? where UID=? and ImageID=?";
            int rowsAffected = this.update(this.connection, sql,
                    image.getTitle(), image.getDescription(), image.getCityCode(), image.getCountry_RegionCodeISO(), image.getPath(), image.getContent(), image.getDateReleased(), user.getUid(), imageID
            );
            if (rowsAffected > 0) {
                return new Action(true, "修改成功");
            } else {
                return new Action(false, "修改失败");
            }
        } catch (Exception e) {
            return new Action(false, "修改失败");
        }
    }

    @Override
    public boolean imageExists(String fileName) {
        String sql = "select * from travelimage where PATH regexp ?";
        try {
            List<Image> imageList = this.queryForList(connection, sql, fileName);
            return imageList.size() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean imageExists(User user, int imageID) {
        try {
            int uid = user.getUid();
            String sql = "select * from travelimage where uid=? and imageID=?";
            List<Image> imageList = this.queryForList(connection, sql, uid, imageID);
            return imageList.size() > 0;

        } catch (Exception e) {
            return false;
        }
    }


}
