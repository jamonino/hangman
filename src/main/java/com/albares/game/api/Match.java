package com.albares.game.api;

import com.albares.hangman.utils.Db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;


public class Match{
    
    private Integer turn = 1;
    private Integer randomNumber;
    private final String[] words = {"AHORCADO","FELIZ","CIEZA"};
    
    private String mask;


    public Match() {
    }
    
    
    public int addUser(Db myDb,User user) throws SQLException{
        PreparedStatement ps = myDb.prepareStatement(
                    "INSERT INTO users (name, score) VALUES (?, ?) returning id;"
            );
        ps.setString(1, user.getName());
        ps.setInt(2, user.getScore());
        ResultSet rs = myDb.executeQuery(ps);
        rs.next();
        user.setId(rs.getInt(1));
        return user.getId();
    }

    public List getUsers(Db myDb) throws SQLException {
        PreparedStatement ps = myDb.prepareStatement(
                    "SELECT name,score FROM users;"
            );
        
        ResultSet rs = myDb.executeQuery(ps);
        
        List<User> users = new ArrayList();
        
        while(rs.next()){
            User user = new User();
            user.setName(rs.getString(1));
            user.setScore(rs.getInt(2));
            users.add(user);
        }
        return users;
    }


    public Integer getTurn() {
        return turn;
    }
    
    public void generateNewMatch(){
        this.randomNumber = (int) (Math.random() * this.words.length);
        this.setMask(StringUtils.repeat("_", this.getRandomWord().length()));
    }

    public Integer getRandomNumber() {
        return randomNumber;
    }
    
    public String getRandomWord() {
        return this.words[this.randomNumber];
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }
    
    public Integer checkChar(char letter){
        Integer coincidences = 0;
        StringBuilder newMask = new StringBuilder(this.mask);
        for (int i = 0; i < this.mask.length(); i++) {
            if(this.mask.charAt(i) == '_' && this.getRandomWord().charAt(i) == letter ){
                newMask.setCharAt(i, letter);
                coincidences++;
            }
        }
        this.mask = newMask.toString();
        return coincidences;
    }
    
    
    public void nextTurn(Db myDb) throws SQLException{
        String s = "SELECT MIN(id) FROM users "
                            + "UNION ALL"
                            + "SELECT MIN(id) FROM users WHERE id>?;";
        PreparedStatement ps = myDb.prepareStatement(
                    s
            );
        ps.setInt(1, this.turn);
        ResultSet rs = myDb.executeQuery(ps);
        
        while(rs.next()){
            this.turn = rs.getInt(1)==0?this.turn:rs.getInt(1);
        }
    }
}
