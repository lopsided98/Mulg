/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bensoft.mulg2.model.tiles;

import com.bensoft.mulg2.AccelerationData;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.view.tiles.SimpleTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public class UnReverserStoneTileModel extends StoneTileModel<SimpleTileView>{

    public UnReverserStoneTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new SimpleTileView(13));
    }

    @Override
    protected void ballEnter(Ball ball) {
        super.ballEnter(ball);
        AccelerationData.unReverse();
    }

}
