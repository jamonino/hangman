
package com.albares.game.api;

import com.albares.hangman.utils.Db;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private String name;
    private Integer score = 0;
    private Integer id;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }
    
    public void increment(Db myDb, Integer quantity) throws SQLException {

        PreparedStatement ps = myDb.prepareStatement(
                    "UPDATE users SET score = score + ? WHERE id = ?;"
            );
        ps.setInt(1, quantity);
        ps.setInt(2, this.getId());
        ps.executeUpdate();
    }

    public void decrement(Db myDb, Integer quantity) throws SQLException {
        PreparedStatement ps = myDb.prepareStatement(
                    "UPDATE users SET score = score - ? WHERE id = ?;"
            );
        ps.setInt(1, quantity);
        ps.setInt(2, this.getId());
        ps.executeUpdate();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
