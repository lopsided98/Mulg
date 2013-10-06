package com.bensoft.mulg2.view.tiles;

import com.bensoft.mulg2.textures.GameTextureBundle;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import static com.bensoft.mulg2.Constants.*;


/**
 *
 * @author Ben Wolsieffer
 */
public class TwoStateTileView extends TileView<TiledSprite> {

    private int initialIndex = 0;

    public TwoStateTileView(int imageNum) {
        super(imageNum);
    }

    public TwoStateTileView(int imageNum, int initalIndex) {
        super(imageNum);
        this.initialIndex = initalIndex;
    }

    @Override
    public TiledSprite createSprite(float x, float y, GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        TiledSprite ts = new TiledSprite(x, y, gtb.getTiledTexture(imageNum), vbo);
        ts.setCurrentTileIndex(initialIndex);
        ts.setZIndex(TILE_Z_INDEX);
        return ts;
    }
}
