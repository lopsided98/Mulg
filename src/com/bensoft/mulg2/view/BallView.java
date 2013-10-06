/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.view;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.bensoft.mulg2.textures.GameTextureBundle;

/**
 *
 * @author Ben Wolsieffer
 */
public class BallView extends View<Sprite> {

    @Override
    public Sprite createSprite(float x, float y, GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        Sprite s =  new Sprite(x, y, gtb.getBallTexture(), vbo);
        s.setZIndex(20);

        return s;
    }

}
