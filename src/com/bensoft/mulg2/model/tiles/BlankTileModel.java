/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.model.tiles;

import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.view.tiles.SimpleTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public class BlankTileModel extends TileModel<SimpleTileView> {

    public BlankTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new SimpleTileView(0));
    }
}
