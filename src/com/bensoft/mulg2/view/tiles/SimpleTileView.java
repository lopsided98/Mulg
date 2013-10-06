/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.view.tiles;

import com.bensoft.mulg2.textures.GameTextureBundle;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import static com.bensoft.mulg2.Constants.*;

/**
 *
 * @author Ben Wolsieffer
 */
public class SimpleTileView extends TileView<Sprite> {

    public SimpleTileView(int imageNum) {
        super(imageNum);
    }

    @Override
    public Sprite createSprite(float x, float y, GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        Sprite s =  new Sprite(x, y, gtb.getTileTexture(imageNum), vbo);
        s.setZIndex(TILE_Z_INDEX);
        return s;
    }


}
