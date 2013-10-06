/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bensoft.mulg2.model.tiles;

import static com.bensoft.mulg2.Constants.ICE_TILE_SURFACE_FRICTION;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.view.tiles.SimpleTileView;


/**
 *
 * @author Ben Wolsieffer
 */
public class IceTileModel extends TileModel<SimpleTileView>{

    public IceTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new SimpleTileView(11));
        enableBallEnter();
    }

    @Override
    protected void ballEnter(Ball ball) {
        super.ballEnter(ball);
        level.setFriction(ICE_TILE_SURFACE_FRICTION);
    }

    @Override
    protected FixtureDef createFixtureDef() {
        return PhysicsFactory.createFixtureDef(0f, 0f, 0f, true);
    }




}
