package com.bensoft.mulg2.model.tiles;

import static com.bensoft.mulg2.Constants.TILTING_RAMP_FORCE;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.model.Model;
import com.bensoft.mulg2.view.tiles.TwoStateTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public class VerticalTiltingRampTileModel extends TiltingRampTileModel {

    public static final int BOTTOM_OPEN_DATA = 256;

    private boolean topOpen = true;

    public VerticalTiltingRampTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new TwoStateTileView(3, (data & BOTTOM_OPEN_DATA) != BOTTOM_OPEN_DATA ? 0 : 1));
        topOpen = (data & BOTTOM_OPEN_DATA) != BOTTOM_OPEN_DATA;
    }
    @Override
    protected void onBallTouch(Ball ball, Contact contact) {
        super.onBallTouch(ball, contact);
        float ballYVel = ball.getBody().getLinearVelocity().y;
        if(topOpen){
            if (ballYVel >= 0) {
                allowThrough = false;
            } else {
                allowThrough = true;
            }
        } else {
            if (ballYVel <= 0) {
                allowThrough = false;
            } else {
                allowThrough = true;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold, Model<?> otherElem) {
        super.preSolve(contact, manifold, otherElem);
        if (contact.isTouching() && otherElem instanceof Ball) {
            Ball ball = (Ball) otherElem;
            float y = getPosition().y;
            float ballY = ball.getPosition().y;
            if (topOpen) {
                if (allowThrough) {
                    if (ballY < y) {
                        topOpen = false;
                        view.getSprite().setCurrentTileIndex(1);
                    }

                }
            } else {
                if (allowThrough) {
                    if (ballY > y) {
                        topOpen = true;
                        view.getSprite().setCurrentTileIndex(0);
                    }
                }
            }
            if (allowThrough) {
                ball.getBody().applyForce(new Vector2(0, topOpen ? TILTING_RAMP_FORCE : -TILTING_RAMP_FORCE), ball.getBody().getWorldCenter());
            }
        }
    }

}
