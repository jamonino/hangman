
package com.albares.game.api;

import com.albares.hangman.utils.Db;
import com.albares.hangman.utils.JWTUtils;
import com.albares.hangman.utils.Parameters;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/play")
public class PlayService {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{token}/{word}")
    public String playMatch(@PathParam("token") String token,@PathParam("word") String word){
        
        try{
            Db myDb = new Db();
            
            User player = JWTUtils.checkJWTandGetUser(token);

            if(player.getId().equals(-1)) return "token no valido";

            myDb.connect();
            if(player.getId().equals(Parameters.match.getTurn())){
                word = word.toUpperCase();
                if(word.length()>1){
                    if(Parameters.match.getRandomWord().equals(word)){
                        player.increment(myDb, 100);
                        Parameters.match.generateNewMatch();
                        myDb.disconnect();
                        return word;
                    }else{
                        player.decrement(myDb, 50);
                    }
                }else{
                    player.increment(myDb, Parameters.match.checkChar(word.charAt(0)) * 20);
                }
                
                Parameters.match.nextTurn(myDb);
                
            }else{
                player.decrement(myDb, 100);
            }
            myDb.disconnect();
            return Parameters.match.getMask();
        }catch(Exception e){
            return "error";
        }
    }    
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTurn(){ 
        return String.valueOf(Parameters.match.getTurn());
    }    
}



