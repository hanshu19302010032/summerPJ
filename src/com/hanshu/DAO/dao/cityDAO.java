package com.hanshu.DAO.city;

import com.hanshu.bean.City;
import com.hanshu.DAO.GenericDao;

import java.sql.Connection;
import java.util.List;

public class cityDAO extends GenericDao<City> implements city {
    private Connection connection;

    public cityDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<City> getCities(String iso) {
        String sql = "select * from geocities where Country_RegionCodeISO=? order by AsciiName";
        try {
            return this.queryForList(connection, sql, iso);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean countryCityMatch(int cityCode, String iso) {
        String sql = "select * from geocities where GeoNameID=? and Country_RegionCodeISO=?";
        try {
            List<City> cityList = this.queryForList(connection, sql, cityCode, iso);
            return cityList.size() != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
