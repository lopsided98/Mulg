/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.textures;

import android.content.Context;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.region.TextureRegion;

/**
 *
 * @author Ben Wolsieffer
 */
public class MainMenuTextureBundle extends TextureBundle {

    private TextureRegion titleTexture;
    private TextureRegion playButtonTexture;

    public MainMenuTextureBundle(Context context, FontManager fontManager, TextureManager textureManager) {
        super(context, fontManager, textureManager, 400, 100);
        titleTexture = addRegion("title.gif", 0, 0, TextureOptions.BILINEAR);
        playButtonTexture = addRegion("play.gif", 300, 0, TextureOptions.BILINEAR);
    }

    public TextureRegion getTitleTexture() {
        return titleTexture;
    }

    public TextureRegion getPlayButtonTexture() {
        return playButtonTexture;
    }
}
