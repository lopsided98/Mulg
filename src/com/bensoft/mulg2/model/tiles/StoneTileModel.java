package com.bensoft.mulg2.model.tiles;

import static com.bensoft.mulg2.Constants.STONE_TILE_SURFACE_FRICTION;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.view.tiles.TileView;

/**
 *
 * @author Ben Wolsieffer
 * @param <V>
 */
public abstract class StoneTileModel<V extends TileView<?>> extends TileModel<V> {

    @Override
    protected FixtureDef createFixtureDef() {
        return PhysicsFactory.createFixtureDef(0f, 0f, 0f, true);
    }

    public StoneTileModel(Level level, float x, float y, int data, V view) {
        super(level, x, y, data, view);
        enableBallEnter();
    }

    @Override
    protected void ballEnter(Ball ball) {
        super.ballEnter(ball);
        level.setFriction(STONE_TILE_SURFACE_FRICTION);
    }
}
