package com.bensoft.mulg2.model.tiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.model.Model;
import com.bensoft.mulg2.view.View;
import com.bensoft.mulg2.view.tiles.MovableRaisedBlockTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public abstract class MovableRaisedBlockTileModel extends RaisedBlockTileModel<MovableRaisedBlockTileView> {

    private final float velocityThreshhold;
    private TileModel<?> coveredTile;
    private boolean fixed = false;

    public MovableRaisedBlockTileModel(Level level, float x, float y, int data, float velocityThreshhold, MovableRaisedBlockTileView view) {
        super(level, x, y, data, view);
        this.velocityThreshhold = velocityThreshhold;
        // Set the original covered tile, I could change it to make it more elegant
        coveredTile = view.getCoveredTile();
        // Deactivate the tile this tile is covering
        coveredTile.getBody().setActive(false);
    }
    private Vector2 moveToPos = null;
    private TileModel<?> oldCoveredTile;

    @Override
    public void preSolve(Contact contact, Manifold manifold, Model<?> otherElem) {
        super.preSolve(contact, manifold, otherElem);
        // If the tile is fixed and not moving, allow the ball to pass
        if (fixed && moveToPos == null) {
            contact.setEnabled(false);
        }
    }

    @Override
    protected void onBallTouch(Ball ball, Contact contact) {
        super.onBallTouch(ball, contact);
        // Only process touches if the body is not fixed
        if (!fixed) {
            // Temporary to store new position
            Vector2 pos = getPosition().cpy();

            // Ball's velocity
            Vector2 ballVelocity = ball.getBody().getLinearVelocity();

            // The contact normal between the ball and the tile
            Vector2 contactNormal = contact.getWorldManifold().getNormal();

            // Set to true if x or y should move, at least one of them should
            // always be false
            boolean moveX = false;
            boolean moveY = false;
            // Decide on the new position for the tile
            if (contactNormal.x == 0) {
                moveY = true;
                if (contactNormal.y > 0) {
                    pos.sub(0, 1);
                } else {
                    pos.add(0, 1);
                }
            } else if (contactNormal.y == 0) {
                moveX = true;
                if (contactNormal.x > 0) {
                    pos.sub(1, 0);
                } else {
                    pos.add(1, 0);
                }
            }

            // Make sure the ball's velocity is fast enough for the tile to be
            // moved
            if (moveX) {
                // Check x velocity
                if (Math.abs(ballVelocity.x) > velocityThreshhold) {
                    moveToPos = pos;
                }
            } else if (moveY) {
                // Check y velocity
                if (Math.abs(ballVelocity.y) > velocityThreshhold) {
                    moveToPos = pos;
                }
            }
            if (moveToPos != null) {
                // Get the tile that could possibly be moved on to
                TileModel<?> newCoveredTile = level.getTileAt(
                        (int) moveToPos.x, (int) moveToPos.y);
                // Decide if the tile the we are about to move on to is one of
                // the right types
                if (newCoveredTile instanceof StoneTileModel) {
                    // If its a normal stone tile, check if its a pit that can be filled
                    if (newCoveredTile instanceof StandardStoneTileModel) {
                        StandardStoneTileModel sst = (StandardStoneTileModel) newCoveredTile;
                        if (sst.isPit()) {
                            // If its a pit, fix the tile in place
                            view.getMainSprite().setCurrentTileIndex(1);
                            fixed = true;
                        }
                    }
                } else if (newCoveredTile instanceof MovableRaisedBlockTileModel) {
                    // If the tile is another movable tile, check to see if it
                    // is fixed and can be covered
                    MovableRaisedBlockTileModel mrbtm = (MovableRaisedBlockTileModel) newCoveredTile;
                    if (!mrbtm.isFixed()) {
                        // If its not fixed we can not move
                        moveToPos = null;
                    }
                } else {
                    // If its any other tile, we can't move
                    moveToPos = null;
                }
                if (moveToPos != null) {
                    // Remember the tile we are moving off of so it can be used
                    // in onUpdate()
                    oldCoveredTile = coveredTile;

                    // Set the new covered tile as the current one
                    coveredTile = newCoveredTile;
                }
            }
        }
    }
    private boolean contactEnded = false;

    @Override
    protected void onEndBallTouch(Ball ball) {
        super.onEndBallTouch(ball);
        // This prevent the tile from moving until the ball has bounced away
        if (moveToPos != null) {
            contactEnded = true;
        }
    }

    @Override
    public void onUpdate(float secondsElapsed) {
        super.onUpdate(secondsElapsed);

        // Check to see if we are trying to move and make sure the ball has
        // already bounced off
        if (moveToPos != null && contactEnded) {
            Vector2 currPos = getPosition();

            View<?> oldView = oldCoveredTile.getView();

            // Make the tile we were covering visible again
            if (oldView instanceof MovableRaisedBlockTileView) {
                // Hackish, but it works, check for a movable tile and make sure
                // only to make the main sprite visible, not the covered one
                MovableRaisedBlockTileView mrbtv = (MovableRaisedBlockTileView) oldView;
                mrbtv.getMainSprite().setVisible(true);
            } else {
                // Otherwise do it normally
                oldView.getSprite().setVisible(true);
            }
            // Reset old covered tile


            // Activate the tile that we are moving off of
            oldCoveredTile.getBody().setActive(true);



            // Update the level so it know we are no longer occuping the
            // space we are moving from
            level.setTileAt((int) currPos.x, (int) currPos.y, oldCoveredTile);

            oldCoveredTile = null;

            // Actually move the tile
            getBody().setTransform(moveToPos, 0);

            // Deactivate the tile we are about to cover
            coveredTile.getBody().setActive(false);

            // Update the level so that it knows that we moved
            level.setTileAt((int) moveToPos.x, (int) moveToPos.y, this);

            View<?> newView = coveredTile.getView();

            // Same concept as above
            if (newView instanceof MovableRaisedBlockTileView) {
                MovableRaisedBlockTileView mrbtv = (MovableRaisedBlockTileView) newView;
                mrbtv.getMainSprite().setVisible(false);
            } else {
                newView.getSprite().setVisible(false);
            }
            // Reset variables
            moveToPos = null;

            contactEnded = false;
        }
    }

    /**
     * Returns true if the movable tile is fixed in place.
     *
     * @return the fixed state of this tile
     */
    public boolean isFixed() {
        return fixed;
    }
}