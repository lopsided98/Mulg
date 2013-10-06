package com.bensoft.mulg2.game.level;

import java.util.Iterator;

import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.bensoft.mulg2.AccelerationData;
import com.bensoft.mulg2.Constants;
import com.bensoft.mulg2.game.Channel;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.model.Model;
import com.bensoft.mulg2.model.tiles.TileModel;

/**
 * 
 * @author Ben Wolsieffer
 */
public abstract class Level {

    PhysicsWorld physicsWorld;
    Ball ball;
    protected String name;
    protected int width;
    protected int height;
    protected TileModel<?>[][] tiles;
    private boolean loaded;
    private final Channel[] channels = new Channel[32];
    private float friction = 0.0f;
    private Vector2 startingPosition;

    public Level() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public void setStartingPosition(Vector2 startingPosition) {
        this.startingPosition = startingPosition;
        if (ball != null) {
            ball.setPosition(startingPosition.x, startingPosition.y);
        }
    }

    public Vector2 getStartingPosition() {
        return startingPosition;
    }

    public Channel getChannel(int num) {
        return channels[num];
    }

    public TileModel<?> getTileAt(int x, int y) {
        checkLoaded();
        if (x < width && y < height && x >= 0 && y >= 0) {
            return tiles[x][y];
        }
        throw new IndexOutOfBoundsException("Width or height is out of bounds.");
    }

    public void setTileAt(int x, int y, TileModel<?> tile) {
        checkLoaded();
        if (x < width && y < height && x >= 0 && y >= 0) {
            tiles[x][y] = tile;
        } else {
            throw new IndexOutOfBoundsException("Width or height is out of bounds.");
        }
    }

    public void setFriction(float friction) {
        if (friction != this.friction) {
            this.friction = friction;
            ball.getBody().setLinearDamping(friction);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean checkLoaded() {
        if (!loaded) {
            throw new IllegalStateException("Level not yet loaded.");
        }
        return true;
    }

    public void load() {
        for (int i = 0; i < channels.length; i++) {
            channels[i] = new Channel();
        }
        final Vector2 gravity = new Vector2();
        physicsWorld = new FixedStepPhysicsWorld(Constants.MAX_PHYSICS_FPS, gravity, false) {
            // physicsWorld = new PhysicsWorld(gravity, false) {
            @Override
            public void onUpdate(final float secondsElapsed) {
                gravity.x = AccelerationData.getX();
                gravity.y = AccelerationData.getY();
                physicsWorld.setGravity(gravity);
                super.onUpdate(secondsElapsed);
                ball.onUpdate(secondsElapsed);
                for (TileModel<?>[] row : tiles) {
                    for (TileModel<?> tile : row) {
                        tile.onUpdate(secondsElapsed);
                    }
                }
            }
        };

        physicsWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Model<?> elem1 = (Model<?>) contact.getFixtureA().getBody()
                        .getUserData();
                Model<?> elem2 = (Model<?>) contact.getFixtureB().getBody()
                        .getUserData();
                elem1.beginContact(contact, elem2);
                elem2.beginContact(contact, elem1);
            }

            @Override
            public void endContact(Contact contact) {
                Model<?> elem1 = (Model<?>) contact.getFixtureA().getBody()
                        .getUserData();
                Model<?> elem2 = (Model<?>) contact.getFixtureB().getBody()
                        .getUserData();
                elem1.endContact(contact, elem2);
                elem2.endContact(contact, elem1);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                Model<?> elem1 = (Model<?>) contact.getFixtureA().getBody()
                        .getUserData();
                Model<?> elem2 = (Model<?>) contact.getFixtureB().getBody()
                        .getUserData();
                elem1.preSolve(contact, oldManifold, elem2);
                elem2.preSolve(contact, oldManifold, elem1);
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                Model<?> elem1 = (Model<?>) contact.getFixtureA().getBody()
                        .getUserData();
                Model<?> elem2 = (Model<?>) contact.getFixtureB().getBody()
                        .getUserData();
                elem1.postSolve(contact, impulse, elem2);
                elem2.postSolve(contact, impulse, elem1);
            }
        });

        ball = new Ball(this, 1, 7);

        loaded = true;
    }

    public void unload() {
        if (loaded) {
            tiles = null;
            Iterator<Body> it = physicsWorld.getBodies();
            while (true)
            {
                if (!it.hasNext())
                {
                    physicsWorld.clearForces();
                    physicsWorld.clearPhysicsConnectors();
                    physicsWorld.reset();
                    physicsWorld.dispose();
                    physicsWorld = null;
                    System.gc();
                    loaded = false;
                    return;
                }
                try
                {
                    physicsWorld.destroyBody(it.next());
                } catch (Exception localException)
                {
                    Debug.e(localException);
                }
            }
        }
    }

    public PhysicsWorld getPhysicsWorld() {
        return physicsWorld;
    }

    public Ball getBall() {
        checkLoaded();
        return ball;
    }

}
