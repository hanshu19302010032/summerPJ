package com.hanshu.DAO.country;

import com.hanshu.bean.Country;

import java.util.List;

public interface country {
    public abstract List<Country> getALL();

    public abstract boolean countryExist(String iso);
}
