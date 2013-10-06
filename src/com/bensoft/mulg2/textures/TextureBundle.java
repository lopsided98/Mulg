/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.textures;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.graphics.Typeface;

/**
 *
 * @author Ben Wolsieffer
 */
public class TextureBundle {

    private final Context context;
    private final HashMap<TextureOptions, BitmapTextureAtlas> bitmapTextureAtlases;
    private final ArrayList<Font> fonts;
    private boolean loaded = false;
    private final TextureManager textureManager;
    private final FontManager fontManager;
    private final int width;
    private final int height;

    public TextureBundle(Context context, FontManager fontManager, TextureManager textureManager, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.textureManager = textureManager;
        this.fontManager = fontManager;
        bitmapTextureAtlases = new HashMap<TextureOptions, BitmapTextureAtlas>(1);
        fonts = new ArrayList<Font>();
    }

    private BitmapTextureAtlas getBitmapTextureAtlas(TextureOptions to) {
        BitmapTextureAtlas bta = bitmapTextureAtlases.get(to);
        if (bta == null) {
            bta = new BitmapTextureAtlas(textureManager, width, height);
            bitmapTextureAtlases.put(to, bta);
        }
        return bta;
    }

    protected TextureRegion addRegion(String path, int x, int y, TextureOptions to) {
        return BitmapTextureAtlasTextureRegionFactory.createFromAsset(getBitmapTextureAtlas(to), context, path, x, y);
    }

    protected TextureRegion addRegion(String path, int x, int y) {
        return BitmapTextureAtlasTextureRegionFactory.createFromAsset(getBitmapTextureAtlas(TextureOptions.DEFAULT), context, path, x, y);
    }

    protected TiledTextureRegion addTiledRegion(String path, int x, int y, int r, int c, TextureOptions to) {
        return BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(getBitmapTextureAtlas(to), context, path, x, y, c, r);
    }

    protected TiledTextureRegion addTiledRegion(String path, int x, int y, int r, int c) {
        return BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(getBitmapTextureAtlas(TextureOptions.DEFAULT), context, path, x, y, c, r);
    }

    protected Font addFont(Typeface tFace, float size, boolean antiA, int color) {
        Font f = FontFactory.create(fontManager, textureManager, 256, 256, tFace, size, antiA, color);
        fonts.add(f);
        return f;
    }

    public void load() {
        if (!loaded) {
            for (BitmapTextureAtlas bta : bitmapTextureAtlases.values()) {
                bta.load();
            }
            for (Font f : fonts) {
                f.load();
            }
            loaded = true;
        }
    }

    public void unload() {
        if (loaded) {
            for (BitmapTextureAtlas bta : bitmapTextureAtlases.values()) {
                bta.unload();
            }
            for (Font f : fonts) {
                f.unload();
            }
            loaded = false;
        }
    }
}
