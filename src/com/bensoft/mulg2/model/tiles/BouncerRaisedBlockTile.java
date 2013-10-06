package com.bensoft.mulg2.model.tiles;

import static com.bensoft.mulg2.Constants.BOUNCER_TILE_FLASH_TIME;
import static com.bensoft.mulg2.Constants.BOUNCER_TILE_FORCE;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.view.tiles.TwoStateTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public class BouncerRaisedBlockTile extends RaisedBlockTileModel<TwoStateTileView> {

    public BouncerRaisedBlockTile(Level level, float x, float y, int data) {
        super(level, x, y, data, new TwoStateTileView(5));
    }
    private float onTimeElapsed = -1f;

    @Override
    protected void onBallTouch(Ball ball, Contact contact) {
        super.onBallTouch(ball, contact);
        onTimeElapsed = 0;
        view.getSprite().setCurrentTileIndex(1);
        Vector2 pushDirection = getBody().getWorldCenter().sub(ball.getBody().getWorldCenter());

        Vector2 push = pushDirection.nor().mul(-BOUNCER_TILE_FORCE);

        ball.getBody().applyLinearImpulse(push, ball.getBody().getWorldCenter());
    }

    @Override
    public void onUpdate(float secondsElapsed) {
        super.onUpdate(secondsElapsed);
        if (onTimeElapsed != -1f) {
            if (onTimeElapsed >= BOUNCER_TILE_FLASH_TIME) {
                view.getSprite().setCurrentTileIndex(0);
                onTimeElapsed = -1f;
            } else {
                onTimeElapsed += secondsElapsed;
            }
        }
    }

}
