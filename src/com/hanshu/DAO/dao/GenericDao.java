package com.hanshu.DAO;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class GenericDao<T> {
    private QueryRunner queryRunner;
    private Class cls = null;

    public GenericDao() {
        queryRunner = new QueryRunner();
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArgs = parameterizedType.getActualTypeArguments();
            if (typeArgs[0] instanceof Class) {
                cls = (Class) typeArgs[0];
            }
        }


    }


    public int update(Connection conn, String sql, Object... objs) throws SQLException {

        return queryRunner.update(conn, sql, objs);
    }


    protected T queryForOne(Connection conn, String sql, Object... objects) throws SQLException {
        T t;
        t = (T) queryRunner.query(conn, sql, new BeanHandler<>(cls), objects);
        return t;
    }


    public List<T> queryForList(Connection conn, String sql, Object... objects) throws SQLException {
        List<T> list;
        list = (List<T>) queryRunner.query(conn, sql, new BeanListHandler<>(cls), objects);
        return list;
    }


}
