/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bensoft.mulg2.model.tiles;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Model;
import com.bensoft.mulg2.view.tiles.TwoStateTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public abstract class TiltingRampTileModel extends TileModel<TwoStateTileView>{
    protected boolean allowThrough = true;
    public TiltingRampTileModel(Level level, float x, float y, int data, TwoStateTileView view) {
        super(level, x, y, data, view);
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold, Model<?> otherElem) {
        super.preSolve(contact, manifold, otherElem);
        contact.setEnabled(!allowThrough);
    }
}
