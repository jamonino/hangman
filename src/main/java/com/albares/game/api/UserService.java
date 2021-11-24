
package com.albares.game.api;

import com.albares.hangman.utils.Db;
import com.albares.hangman.utils.Parameters;
import com.albares.hangman.utils.JWTUtils;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/user")
public class UserService {
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(User newUser) throws SQLException{
        Db myDb = new Db();
            
        myDb.connect();
            
        if(newUser.getScore()!=0 ){
            newUser.setScore(-Math.abs(newUser.getScore()));
            Parameters.match.addUser(myDb,newUser);
            return "Hackeo detectado... tu token es: "+
                    JWTUtils.generateToken(newUser.getId());
        }
        Parameters.match.addUser(myDb, newUser);
        myDb.disconnect();
        
        return JWTUtils.generateToken(newUser.getId());
    }    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List getUsers() throws SQLException{ 
        Db myDb = new Db();
            
        myDb.connect();
        List<User> users = Parameters.match.getUsers(myDb);
        myDb.disconnect();
        return users;
        
    }    
}







