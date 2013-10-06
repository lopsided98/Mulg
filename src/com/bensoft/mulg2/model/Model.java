package com.bensoft.mulg2.model;

import static com.bensoft.mulg2.Constants.PIXELS_PER_METER;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.textures.GameTextureBundle;
import com.bensoft.mulg2.view.View;

/**
 * @author Ben Wolsieffer
 * @param <V>
 */
public abstract class Model<V extends View<?>> {

    protected final Level level;
    private final Body body;
    private Sprite[] sprites;
    protected final V view;

    public Model(Level level, float x, float y, V view) {
        this.level = level;
        body = createBody(x, level.getHeight() - y, createFixtureDef());
        body.setUserData(this);
        this.view = view;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setPosition(float x, float y) {
        body.setTransform(x, level.getHeight() - y, 0);
    }

    public Body getBody() {
        return body;
    }

    public V getView() {
        return view;
    }

    public Sprite[] getSprites(GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        if (sprites == null) {
            Vector2 pos = getPosition();
            sprites = view.generateSprites(pos.x * PIXELS_PER_METER, pos.y * PIXELS_PER_METER, gtb, vbo);
            for (Sprite s : sprites) {
                level.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(s, body, true, true, PIXELS_PER_METER));
            }
        }
        return sprites;
    }

    public Sprite getSprite(GameTextureBundle gtb, VertexBufferObjectManager vbo) {
        return getSprites(gtb, vbo)[0];
    }

    protected abstract FixtureDef createFixtureDef();

    protected abstract Body createBody(float x, float y, FixtureDef fixtureDef);

    public void beginContact(Contact contact, Model<?> otherElem) {
    }

    public void preSolve(Contact contact, Manifold manifold, Model<?> otherElem) {
    }

    public void postSolve(Contact contact, ContactImpulse impulse, Model<?> otherElem) {
    }

    public void endContact(Contact contact, Model<?> otherElem) {
    }

    public void onUpdate(float secondsElapsed) {
    }

}
