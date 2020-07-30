package com.hanshu.DAO.country;

import com.hanshu.bean.Country;
import com.hanshu.DAO.GenericDao;

import java.sql.Connection;
import java.util.List;

public class countryDAO extends GenericDao<Country> implements country {
    private Connection connection;

    public countryDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Country> getALL() {
        String sql = "select * from geocountries_regions order by Country_RegionName";
        try {
            return this.queryForList(this.connection, sql);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean countryExist(String iso) {
        String sql = "select * from geocountries_regions where ISO=?";
        try {
            List<Country> countryList = this.queryForList(connection, sql, iso);
            return countryList.size() != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
