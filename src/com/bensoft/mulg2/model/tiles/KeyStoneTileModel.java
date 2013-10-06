/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.model.tiles;

import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.view.tiles.TwoStateTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public class KeyStoneTileModel extends StoneTileModel<TwoStateTileView> {

    public KeyStoneTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new TwoStateTileView(8));
    }

    @Override
    protected void ballEnter(Ball ball) {
        super.ballEnter(ball);
        view.getSprite().setCurrentTileIndex(1);
        ball.addItem(new Ball.Item(Ball.Item.Type.KEY, 0));
    }
}
