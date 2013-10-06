package com.bensoft.mulg2.view;

import java.lang.reflect.Array;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.bensoft.mulg2.textures.GameTextureBundle;

/**
 *
 * @author Ben Wolsieffer
 * @param <S>
 */
public abstract class View<S extends Sprite> {

    private S[] sprites;

    public final S[] generateSprites(float x, float y, GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        return sprites = createSprites(x, y, gtb, vbo);
    }

    public abstract S createSprite(float x, float y, GameTextureBundle gtb, VertexBufferObjectManager vbo);

    public S[] createSprites(float x, float y, GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        S sprite = createSprite(x, y, gtb, vbo);
        @SuppressWarnings("unchecked")
        S[] spriteArr = (S[]) Array.newInstance(sprite.getClass(), 1);
        spriteArr[0] = sprite;
        return spriteArr;
    }

    public S[] getSprites() {
        return sprites;
    }

    public S getSprite() {
        return sprites[0];
    }
}
