
package com.albares.hangman.utils;

import com.albares.game.api.Match;


public final class Parameters {

    public Parameters() {
    }
    
    public static final String PROJECT_NAME = "Hangman";
    public static Match match = new Match();

    public static final String DB_URL = "jdbc:postgresql://localhost:5432/hangman_db";
    public static final String DB_USER = "hangman_user";
    public static final String DB_PASS = Secrets.DB_PASS;
    
    
}
