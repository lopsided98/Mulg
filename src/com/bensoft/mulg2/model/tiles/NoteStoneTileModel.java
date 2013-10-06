/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.model.tiles;

import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.screen.GameScreen;
import com.bensoft.mulg2.screen.ScreenManager;
import com.bensoft.mulg2.view.tiles.TwoStateTileView;

/**
 * 
 * @author Ben Wolsieffer
 */
public class NoteStoneTileModel extends StoneTileModel<TwoStateTileView> {

    public NoteStoneTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new TwoStateTileView(1));
    }

    private boolean pickedUp = false;

    @Override
    protected void ballEnter(Ball ball) {
        if (!pickedUp) {
            view.getSprite().setCurrentTileIndex(1);
            GameScreen gs = ScreenManager.getGameScreen();
            gs.pickUpNote(data);
            pickedUp = true;
        }
    }

}
