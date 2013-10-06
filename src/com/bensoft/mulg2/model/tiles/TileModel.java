package com.bensoft.mulg2.model.tiles;

import static com.bensoft.mulg2.Constants.PIXELS_PER_METER;
import static com.bensoft.mulg2.Constants.SOLID_TILE_DENSITY;
import static com.bensoft.mulg2.Constants.SOLID_TILE_ELASTICITY;
import static com.bensoft.mulg2.Constants.SOLID_TILE_FRICTION;
import static com.bensoft.mulg2.Constants.TILE_WIDTH_METERS;
import static com.bensoft.mulg2.Constants.TILE_WIDTH_PIXELS;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.bensoft.mulg2.Constants;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.model.Model;
import com.bensoft.mulg2.view.tiles.TileView;

/**
 * 
 * @author Ben Wolsieffer
 * @param <V>
 * 
 */
public abstract class TileModel<V extends TileView<?>> extends Model<V> {

    protected int data;
    private final float x, y;

    private boolean enableBallEnter,
    enableBallExit;

    public TileModel(Level level, float x, float y, int data, V view) {
        super(level, x, y, view);
        this.x = x;
        this.y = y;
        this.data = data;
    }

    protected void enableBallEnter() {
        enableBallEnter = true;
    }

    protected void enableBallExit() {
        enableBallExit = true;
    }

    public int getData() {
        return data;
    }

    @Override
    protected Body createBody(float x, float y, FixtureDef fixtureDef) {
        return PhysicsFactory.createBoxBody(level.getPhysicsWorld(), x
                * PIXELS_PER_METER, y * PIXELS_PER_METER, TILE_WIDTH_PIXELS,
                TILE_WIDTH_PIXELS, BodyDef.BodyType.StaticBody, fixtureDef,
                Constants.PIXELS_PER_METER);
    }

    @Override
    protected FixtureDef createFixtureDef() {
        return PhysicsFactory.createFixtureDef(SOLID_TILE_DENSITY,
                SOLID_TILE_ELASTICITY, SOLID_TILE_FRICTION);
    }

    private boolean touching = false;

    @Override
    public void beginContact(Contact contact, Model<?> otherElem) {
        if (otherElem instanceof Ball) {

            ball = (Ball) otherElem;
            this.contact = contact;
            // Debug.i("beginContact: ball:" + ball);
            // Debug.i("beginContact: contact: " + contact);
            // Debug.i("beginContact: this: " + this);
            if (!touching && contact.isTouching()) {
                onBallTouch(ball, contact);
                touching = true;
            }

        }
    }

    @Override
    public void endContact(Contact contact, Model<?> otherElem) {

        if (touching) {
            onEndBallTouch(ball);
        }
        // Debug.i("endContact: Setting contact to null");
        this.contact = null;
        // Debug.i("endContact: Contact set to null");
        // Debug.i("endContact: Setting ball to null");
        ball = null;
        // Debug.i("endContact: Ball set to null");
        // Debug.i("endContact: this: " + this);

        touching = false;
        entered = false;
    }

    protected void ballEnter(Ball ball) {
    }

    protected void ballExit(Ball ball) {
    }

    protected void onBallTouch(Ball ball, Contact contact) {
    }

    protected void onEndBallTouch(Ball ball) {
    }

    protected static final float HALF_WIDTH_METERS = TILE_WIDTH_METERS / 2;
    private boolean entered = false;
    private Contact contact = null;
    private Ball ball;

    @Override
    public void onUpdate(float secondsElapsed) {
        if (contact != null) {
            if (contact.isTouching()) {
                if (!touching) {
                    onBallTouch(ball, contact);
                    touching = true;
                }
                if (enableBallEnter || enableBallExit) {
                    Vector2 pos = getPosition();
                    float x = pos.x;
                    float y = pos.y;
                    Vector2 ballPos = ball.getPosition();
                    float ballX = ballPos.x;
                    float ballY = ballPos.y;
                    // Debug.i("ballX:" + ballX);
                    // Debug.i("ballY:" + ballY);
                    // Debug.i("X: " + x);
                    // Debug.i("Y: " + y);
                    if (!entered) {

                        if (enableBallEnter && Math.abs(ballX - x) < HALF_WIDTH_METERS && Math.abs(ballY - y) < HALF_WIDTH_METERS) {
                            entered = true;
                            ballEnter(ball);
                        }
                    } else {
                        if (enableBallExit && Math.abs(ballX - x) > HALF_WIDTH_METERS && Math.abs(ballY - y) > HALF_WIDTH_METERS) {
                            entered = false;
                            ballExit(ball);
                        }
                    }
                }
            } else {
                entered = false;
                if (touching) {
                    onEndBallTouch(ball);
                }
            }
        } else {
            entered = false;
        }
    }

    public int getTileX() {
        return (int) x;
    }

    public int getTileY() {
        return (int) y;
    }
}
