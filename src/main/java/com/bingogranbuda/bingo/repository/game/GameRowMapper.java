package com.bingogranbuda.bingo.repository.game;

import com.bingogranbuda.bingo.model.Game;
import com.bingogranbuda.bingo.model.status.GameStatus;
import com.bingogranbuda.bingo.util.repository.SqlUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameRowMapper implements RowMapper<Game> {

    private GameStatus gameStatusFromString(String status){

        try{
            return GameStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e){
            return GameStatus.WAITING;
        }
    }

    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Game(
                rs.getInt("id"),
                gameStatusFromString(rs.getString("status")),
                SqlUtil.sqlArrayToList(rs, "generated_ballots")
        );
    }
}
