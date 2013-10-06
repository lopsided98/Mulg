/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.textures;

import android.content.Context;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

/**
 *
 * @author Ben Wolsieffer
 */
public class TextureBundleManager {

    private static GameTextureBundle gameTextureBundle;
    private static MainMenuTextureBundle mainMenuTextureBundle;

    public static void init(FontManager fontManager, TextureManager textureManager, Context ctx, String fontBasePath, String textureBasePath) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(textureBasePath);
        FontFactory.setAssetBasePath(fontBasePath);
        gameTextureBundle = new GameTextureBundle(ctx, fontManager, textureManager);
        mainMenuTextureBundle = new MainMenuTextureBundle(ctx, fontManager, textureManager);
    }

    public static GameTextureBundle getGameTextureBundle() {
        return gameTextureBundle;
    }

    public static MainMenuTextureBundle getMainMenuTextureBundle() {
        return mainMenuTextureBundle;
    }
    
    
}
