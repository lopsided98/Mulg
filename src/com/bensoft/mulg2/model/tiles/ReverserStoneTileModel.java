package com.bensoft.mulg2.model.tiles;

import com.bensoft.mulg2.AccelerationData;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.view.tiles.SimpleTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public class ReverserStoneTileModel extends StoneTileModel<SimpleTileView> {

    public ReverserStoneTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new SimpleTileView(12));
    }

    @Override
    protected void ballEnter(Ball ball) {
        AccelerationData.reverse();
    }
}
