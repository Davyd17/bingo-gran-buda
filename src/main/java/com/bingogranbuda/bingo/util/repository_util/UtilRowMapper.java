package com.bingogranbuda.bingo.util.repository_util;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class UtilRowMapper {

    public static List<Integer> sqlArrayAsList(ResultSet rs, String columnLabel) throws SQLException {

        Array sqlArray = rs.getArray(columnLabel);
        Integer[] integerArray = (Integer[]) sqlArray.getArray();

        return Arrays.asList(integerArray);
    }
}
