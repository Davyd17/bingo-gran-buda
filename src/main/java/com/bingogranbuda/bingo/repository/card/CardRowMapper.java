package com.bingogranbuda.bingo.repository.card;

import com.bingogranbuda.bingo.model.Card;
import com.bingogranbuda.bingo.model.status.CardStatus;
import com.bingogranbuda.bingo.util.repository.SqlUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRowMapper implements RowMapper<Card> {

    private CardStatus cardStatusFromString(String status){
        try{
            return CardStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e){
            return CardStatus.UNDEFINED;
        }
    }

    @Override
    public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Card(
                rs.getInt("id"),
                SqlUtil.sqlArrayToList(rs, "numbers"),
                cardStatusFromString(rs.getString("status")),
                rs.getInt("user_id"),
                rs.getInt("game_id"),
                SqlUtil.sqlArrayToList(rs, "selected_numbers"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
