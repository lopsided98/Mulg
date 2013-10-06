package com.bensoft.mulg2.model.tiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.model.Model;
import com.bensoft.mulg2.view.tiles.SimpleTileView;

import static com.bensoft.mulg2.Constants.*;

/**
 *
 * @author Ben Wolsieffer
 */
public class DimpleStoneTileModel extends StoneTileModel<SimpleTileView> {

    public DimpleStoneTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new SimpleTileView(3));
    }
    private boolean push;

    @Override
    protected void onBallTouch(Ball ball, Contact contact) {
        super.onBallTouch(ball, contact);
        push = true;
    }

    @Override
    protected void onEndBallTouch(Ball ball) {
        super.onEndBallTouch(ball);
        push = false;
    }

    @Override
    public void endContact(Contact contact, Model<?> otherElem) {
        super.endContact(contact, otherElem);
        push = false;
    }

    @Override
    public void onUpdate(float secondsElapsed) {
        if (push) {
            Body ballBody = level.getBall().getBody();
            Vector2 ballVec = ballBody.getWorldCenter();
            Vector2 thisVec = getBody().getWorldCenter();
            Vector2 pushDirection = thisVec.sub(ballVec).nor();

            Vector2 pushVec = pushDirection.mul(thisVec.dst(ballVec)).mul(DIMPLE_TILE_FORCE_MULTIPLIER);
            ballBody.applyForce(pushVec, ballBody.getWorldCenter());
        }
    }

}
