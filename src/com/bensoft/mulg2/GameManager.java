package com.bensoft.mulg2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.andengine.util.debug.Debug;

import android.content.Context;

import com.bensoft.mulg2.game.Game;
import com.bensoft.mulg2.game.PDBGame;

/**
 * 
 * @author Ben Wolsieffer
 */
public class GameManager {

    private static final ArrayList<Game> games = new ArrayList<Game>();
    private static boolean loaded = false;
    private static Game selectedGame;

    public static void loadGames(Context ctx) {
        if (!loaded) {
            try {
                String[] files = ctx.getAssets().list("games");
                for (String file : files) {
                    if (file.endsWith(".pdb")) {
                        try {
                            PDBGame game = new PDBGame(ctx, "games/" + file);
                            Debug.i("Loading game: " + game.getName());
                            games.add(game);
                        } catch (FileNotFoundException ex) {
                            Debug.e("Unable to find game file, I hope this never happens.");
                        } catch (IOException ex) {
                            Debug.w("Unable to read game file: " + file, ex);
                        }
                    }
                }
            } catch (IOException ex) {
                Debug.e("Unable to find game files.", ex);
            }
            selectedGame = games.get(0);
            loaded = true;
        }
    }

    public static List<Game> getGames() {
        return Collections.unmodifiableList(games);
    }

    public static Game getSelectedGame() {
        return selectedGame;
    }

    public static void setSelectedGame(Game selectedGame) {
        GameManager.selectedGame = selectedGame;
    }
}
