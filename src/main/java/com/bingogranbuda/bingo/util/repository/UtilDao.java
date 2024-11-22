package com.bingogranbuda.bingo.util.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UtilDao {

    public static List<Integer> sqlArrayToList(ResultSet rs, String columnLabel) throws SQLException {

        Array sqlArray = rs.getArray(columnLabel);
        Integer[] integerArray = (Integer[]) sqlArray.getArray();

        return Arrays.asList(integerArray);
    }

    public static Array listToSqlArray(JdbcTemplate jdbcTemplate, List<Integer> numbers) {

        Array sqlArray = null;

            try(Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()){

                sqlArray = connection.createArrayOf("INTEGER", numbers.toArray());

            } catch(SQLException e){
                e.printStackTrace();
            }

            return sqlArray;

    }
}
