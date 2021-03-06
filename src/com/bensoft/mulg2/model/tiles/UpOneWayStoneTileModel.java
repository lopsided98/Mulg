/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.model.tiles;

import com.badlogic.gdx.physics.box2d.Contact;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.view.tiles.SimpleTileView;
/**
 *
 * @author Ben Wolsieffer
 */
public class UpOneWayStoneTileModel extends OneWayStoneTileModel {

    public UpOneWayStoneTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new SimpleTileView(7));
    }
    @Override
    protected void onBallTouch(Ball ball, Contact contact) {
        super.onBallTouch(ball, contact);
        if (ball.getBody().getLinearVelocity().y <= 0) {
            allowThrough = false;
        } else {
            allowThrough = true;
        }
    }
    // @Override
    // public void preSolve(Contact contact, Manifold manifold, Model<?>
    // otherElem) {
    // if (contact.isTouching() && otherElem instanceof Ball) {
    // Ball ball = (Ball) otherElem;
    // float y = getPosition().y;
    // float ballY = ball.getPosition().y;
    // if ((ballY - BALL_RADIUS_METERS <= y + HALF_WIDTH_METERS) &&
    // ball.getBody().getLinearVelocity().y >= 0) {
    // contact.setEnabled(false);
    // } else {
    // contact.setEnabled(true);
    // }
    // }
    // }

}
