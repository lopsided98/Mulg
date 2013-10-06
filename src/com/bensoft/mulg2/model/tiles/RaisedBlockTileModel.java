/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.model.tiles;

import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.view.tiles.TileView;

/**
 *
 * @author Ben Wolsieffer
 * @param <V>
 */
public abstract class RaisedBlockTileModel<V extends TileView<?>> extends
        TileModel<V> {

    public RaisedBlockTileModel(Level level, float x, float y, int data, V view) {
        super(level, x, y, data, view);
    }

}
