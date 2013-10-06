/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.model.tiles;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.bensoft.mulg2.game.ChannelActivationEvent;
import com.bensoft.mulg2.game.ChannelActivationListener;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Model;
import com.bensoft.mulg2.view.tiles.AnimatedTileView;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import static com.bensoft.mulg2.Constants.*;


/**
 *
 * @author Ben Wolsieffer
 */
public abstract class DoorStoneTileModel extends StoneTileModel<AnimatedTileView> {

    private boolean open = false;

    public DoorStoneTileModel(Level level, float x, float y, int data, final AnimatedTileView view) {
        super(level, x, y, data, view);
        int channelNum = data & 0xf;
        level.getChannel(channelNum).addActivationListener(new ChannelActivationListener() {

            @Override
            public void onActivated(ChannelActivationEvent cae) {
                view.getSprite().animate(TILE_ANIMATION_FRAME_TIME, false);
                open = true;
            }

            @Override
            public void onDeactivated(ChannelActivationEvent cae) {
                view.getSprite().animate(new long[]{TILE_ANIMATION_FRAME_TIME, TILE_ANIMATION_FRAME_TIME, TILE_ANIMATION_FRAME_TIME, TILE_ANIMATION_FRAME_TIME}, new int[]{3, 2, 1, 0}, false);
                open = false;
            }

        });
    }

    @Override
    protected FixtureDef createFixtureDef() {
        return PhysicsFactory.createFixtureDef(SOLID_TILE_DENSITY, SOLID_TILE_ELASTICITY, SOLID_TILE_FRICTION);
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold, Model<?> otherElem) {
        if (open) {
            contact.setEnabled(false);
        }
        super.preSolve(contact, manifold, otherElem);
    }

}
