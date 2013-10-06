/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.view.tiles;

import com.bensoft.mulg2.textures.GameTextureBundle;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import static com.bensoft.mulg2.Constants.*;


/**
 *
 * @author Ben Wolsieffer
 */
public class AnimatedTileView extends TileView<AnimatedSprite> {

    public AnimatedTileView(int imageNum) {
        super(imageNum);
    }

    @Override
    public AnimatedSprite createSprite(float x, float y, GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        AnimatedSprite as =  new AnimatedSprite(x, y, gtb.getWideTiledTexture(imageNum), vbo);
        as.setZIndex(TILE_Z_INDEX);
        return as;
    }

}
