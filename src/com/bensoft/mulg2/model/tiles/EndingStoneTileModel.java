package com.bensoft.mulg2.model.tiles;

import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.screen.ScreenManager;
import com.bensoft.mulg2.view.tiles.SimpleTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public class EndingStoneTileModel extends StoneTileModel<SimpleTileView> {

    public EndingStoneTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new SimpleTileView(5));
    }

    @Override
    protected void ballEnter(Ball ball) {
        super.ballEnter(ball);

        ScreenManager.getGameScreen().nextLevel();
    }
}
