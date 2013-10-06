package com.bensoft.mulg2.model.tiles;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.bensoft.mulg2.game.Channel;
import com.bensoft.mulg2.game.ChannelActivationEvent;
import com.bensoft.mulg2.game.ChannelActivationListener;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.screen.ScreenManager;
import com.bensoft.mulg2.view.tiles.TwoStateTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public class StandardStoneTileModel extends StoneTileModel<TwoStateTileView> {

    public static final int STARTING_POINT_DATA = 255;
    public static final int PIT_DATA = 512;

    private boolean pit = false;

    @Override
    protected FixtureDef createFixtureDef() {
        return PhysicsFactory.createFixtureDef(0f, 0f, 0f, true);
    }

    public StandardStoneTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new TwoStateTileView(6, ((data & PIT_DATA) == PIT_DATA) ? 1 : 0));
        if ((data & STARTING_POINT_DATA) == STARTING_POINT_DATA) {
            level.setStartingPosition(new Vector2(x, y));
        } else {
            pit = (data & PIT_DATA) == PIT_DATA;
        }
        Channel channel = level.getChannel(data & 0xf);
        channel.addActivationListener(new ChannelActivationListener() {

            @Override
            public void onActivated(ChannelActivationEvent cae) {
                togglePit();
            }

            @Override
            public void onDeactivated(ChannelActivationEvent cae) {
                togglePit();
            }

        });
    }

    private void togglePit() {
        pit = !pit;
        if (pit) {
            view.getSprite().setCurrentTileIndex(1);
        } else {
            view.getSprite().setCurrentTileIndex(0);
        }
    }

    public boolean isPit() {
        return pit;
    }

    @Override
    protected void ballEnter(Ball ball) {
        super.ballEnter(ball);
        if (pit) {
            ScreenManager.getGameScreen().kill();
        }
    }

}
