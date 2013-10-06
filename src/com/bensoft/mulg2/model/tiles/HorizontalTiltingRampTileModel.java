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
public class HorizontalTiltingRampTileModel extends TiltingRampTileModel {

    public static final int RIGHT_OPEN = 0x100;
    private boolean leftOpen = true;

    public HorizontalTiltingRampTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new TwoStateTileView(4, (data & RIGHT_OPEN) != RIGHT_OPEN ? 0 : 1));
        leftOpen = (data & RIGHT_OPEN) != RIGHT_OPEN;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold, Model<?> otherElem) {
        // TODO fix high speed breaking of ramp tile
        super.preSolve(contact, manifold, otherElem);
        if (contact.isTouching() && otherElem instanceof Ball) {
            Ball ball = (Ball) otherElem;
            float x = getPosition().x;
            float ballX = ball.getPosition().x;
            if (leftOpen) {
                if (allowThrough) {
                    if (ballX > x) {
                        leftOpen = false;
                        view.getSprite().setCurrentTileIndex(1);
                    }
                }
            } else {
                if (allowThrough) {
                    if (ballX < x) {
                        leftOpen = true;
                        view.getSprite().setCurrentTileIndex(0);
                    }
                }
            }
            if (allowThrough) {
                contact.setEnabled(false);
                ball.getBody().applyForce(new Vector2(leftOpen ? -TILTING_RAMP_FORCE : TILTING_RAMP_FORCE, 0), ball.getBody().getWorldCenter());
            }
        }
    }
}