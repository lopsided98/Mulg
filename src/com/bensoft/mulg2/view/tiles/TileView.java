/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.view.tiles;

import com.bensoft.mulg2.view.View;

import org.andengine.entity.sprite.Sprite;

/**
 *
 * @author Ben Wolsieffer
 * @param <S>
 */
public abstract class TileView<S extends Sprite> extends View<S> {

    protected int imageNum;

    public TileView(int imageNum) {
        this.imageNum = imageNum;
    }


}
