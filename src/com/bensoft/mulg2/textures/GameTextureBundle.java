package com.bensoft.mulg2.textures;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.graphics.Typeface;

/**
 * 
 * @author Ben Wolsieffer
 */
public class GameTextureBundle extends TextureBundle {

    private final TextureRegion ballRegion;
    private final TextureRegion[] tileTextures;
    private final TiledTextureRegion[] tiledTextures;
    private final TiledTextureRegion[] tiledwTextures;
    private final Font defaultFont;
    private final TextureRegion inventoryNoteTexture;

    private static String int2String(int i) {
        String s = String.valueOf(i);
        if (i < 100) {
            s = "0" + s;
        }
        if (i < 10) {
            s = "0" + s;
        }
        return s;
    }

    public GameTextureBundle(Context context, FontManager fontManager, TextureManager textureManager) {
        super(context, fontManager, textureManager, 2604, 32);
        tileTextures = new TextureRegion[14];
        tiledTextures = new TiledTextureRegion[9];
        tiledwTextures = new TiledTextureRegion[3];
        int x = 0;
        boolean top = true;
        for (int i = 0; i < tileTextures.length; i++) {
            String name = "tile" + int2String(i) + ".gif";
            tileTextures[i] = addRegion(name, x, top ? 0 : 16);
            if (!top) {
                x += 16;
            }
            top = !top;
        }
        if (!top) {
            x += 16;
        }
        for (int i = 0; i < tiledTextures.length; i++) {
            String name = "tiled" + int2String(i) + ".gif";
            tiledTextures[i] = addTiledRegion(name, x, 0, 2, 1);
            x += 16;
        }
        for (int i = 0; i < tiledwTextures.length; i++) {
            String name = "tiledw" + int2String(i) + ".gif";
            tiledwTextures[i] = addTiledRegion(name, x, 0, 2, 2);
            x += 32;
        }
        ballRegion = addRegion("tile001.gif", x, 0);
        inventoryNoteTexture = addRegion("inv_note.gif", x, 12);

        defaultFont = addFont(Typeface.DEFAULT, 12f, false, Color.WHITE_ABGR_PACKED_INT);
    }

    public TextureRegion getBallTexture() {
        return ballRegion;
    }

    public TextureRegion getTileTexture(int tile) {
        if (tile >= 0 && tile < tileTextures.length) {
            Debug.i("Tile: " + tile + "; returning " + tileTextures[tile]);
            return tileTextures[tile];
        } else {
            Debug.i("Tile: " + tile + "; returning null");
            return null;
        }
    }

    public TiledTextureRegion getTiledTexture(int tile) {
        if (tile >= 0 && tile < tiledTextures.length) {
            Debug.i("Tile: " + tile + "; returning " + tileTextures[tile]);
            return tiledTextures[tile];
        } else {
            Debug.i("Tile: " + tile + "; returning null");
            return null;
        }
    }

    public TiledTextureRegion getWideTiledTexture(int tile) {
        if (tile >= 0 && tile < tiledwTextures.length) {
            Debug.i("Tile: " + tile + "; returning " + tileTextures[tile]);
            return tiledwTextures[tile];
        } else {
            Debug.i("Tile: " + tile + "; returning null");
            return null;
        }
    }

    public Font getDefaultFont() {
        return defaultFont;
    }

    public TextureRegion getInventoryNoteTexture() {
        return inventoryNoteTexture;
    }
}
