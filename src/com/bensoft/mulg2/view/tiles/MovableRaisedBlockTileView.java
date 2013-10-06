package com.bensoft.mulg2.view.tiles;

import com.bensoft.mulg2.model.tiles.StandardStoneTileModel;
import com.bensoft.mulg2.textures.GameTextureBundle;

import java.util.Arrays;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import static com.bensoft.mulg2.Constants.*;


/**
 *
 * @author Ben Wolsieffer
 */
public class MovableRaisedBlockTileView extends SimpleTileView {

    private StandardStoneTileModel coveredTile;
    private TiledSprite mainSprite;

    public MovableRaisedBlockTileView(int imageNum, StandardStoneTileModel coveredTile) {
        super(imageNum);
        this.coveredTile = coveredTile;
    }

    @Override
    public Sprite createSprite(float x, float y, GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        return mainSprite = new TiledSprite(x, y, gtb.getTiledTexture(imageNum), vbo);
    }

    @Override
    public Sprite[] createSprites(float x, float y, GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        Sprite thisS = createSprite(x, y, gtb, vbo);
        thisS.setZIndex(MOVABLE_TILE_Z_INDEX);
        Sprite[] coveredSprites = coveredTile.getSprites(gtb, vbo);
        Sprite[] out = Arrays.copyOf(coveredSprites, coveredSprites.length + 1);
        out[out.length - 1] = thisS;
        coveredTile = null;
        return out;
    }

    public TiledSprite getMainSprite() {
        return mainSprite;
    }

    public StandardStoneTileModel getCoveredTile() {
        return coveredTile;
    }
}
