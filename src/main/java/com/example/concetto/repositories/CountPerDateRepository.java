package com.example.concetto.repositories;

import com.example.concetto.models.CountPerDate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.List;

import static javax.swing.UIManager.getString;

@Repository
public class CountPerDateRepository  {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    String query = "select DATE_FORMAT(next_review_date, \"%M %d %Y\") as review_date, count(*) as count from concept where datediff( curdate(),next_review_date) > -7 AND datediff( curdate(),next_review_date) <= 0 AND user_id = ? AND done = 1 " +
            "GROUP BY DATE_FORMAT(next_review_date, \"%M %d %Y\") ORDER BY  next_review_date ASC";

    public List<CountPerDate> findCountOfNextSevenDueConceptsByUserId(Long userId){
        List<CountPerDate> fetchedList = jdbcTemplate.query(query, new Object[]{userId}, new RowMapper<CountPerDate>() {
            @Override
            public CountPerDate mapRow(ResultSet resultSet, int i) throws SQLException {
                CountPerDate countPerDate = new CountPerDate();
                String dateAsString = resultSet.getString(1);
                countPerDate.setReviewDate(new SimpleDateFormat("MMM dd yyyy").parse(dateAsString, new ParsePosition(0)));

                countPerDate.setCount(resultSet.getLong(2));
                return countPerDate;
            }
        });

        return fetchedList;
    }

}
