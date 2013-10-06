package com.bensoft.mulg2.model.tiles;

import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.view.tiles.MovableRaisedBlockTileView;

import static com.bensoft.mulg2.Constants.*;


/**
 *
 * @author Ben Wolsieffer
 */
public class HeavyMovableRaisedBlockTileModel extends MovableRaisedBlockTileModel {

    public HeavyMovableRaisedBlockTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, HEAVY_MOVABLE_TILE_MOVEMENT_VELOCITY, new MovableRaisedBlockTileView(7, new StandardStoneTileModel(level, x, y, 0)));
    }
}
