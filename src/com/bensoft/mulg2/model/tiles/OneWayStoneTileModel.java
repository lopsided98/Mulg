/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bensoft.mulg2.model.tiles;

import static com.bensoft.mulg2.Constants.SOLID_TILE_DENSITY;
import static com.bensoft.mulg2.Constants.SOLID_TILE_ELASTICITY;
import static com.bensoft.mulg2.Constants.SOLID_TILE_FRICTION;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.model.Model;
import com.bensoft.mulg2.view.tiles.SimpleTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public abstract class OneWayStoneTileModel extends StoneTileModel<SimpleTileView> {
    protected boolean allowThrough = true;
    public OneWayStoneTileModel(Level level, float x, float y, int data, SimpleTileView view) {
        super(level, x, y, data, view);
        enableBallExit();
    }

    @Override
    protected FixtureDef createFixtureDef() {
        return PhysicsFactory.createFixtureDef(SOLID_TILE_DENSITY, SOLID_TILE_ELASTICITY, SOLID_TILE_FRICTION);
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold, Model<?> otherElem) {
        super.preSolve(contact, manifold, otherElem);
        contact.setEnabled(!allowThrough);
    }

    @Override
    protected void ballExit(Ball ball) {
        super.ballExit(ball);
        allowThrough = true;
    }
}
