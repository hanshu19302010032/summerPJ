package com.hanshu.DAO.city;

import com.hanshu.bean.City;

import java.util.List;

public interface city {
    List<City> getCities(String iso);

    boolean countryCityMatch(int cityCode, String iso);
}
