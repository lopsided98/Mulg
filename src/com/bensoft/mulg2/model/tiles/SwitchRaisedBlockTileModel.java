package com.bensoft.mulg2.model.tiles;

import com.badlogic.gdx.physics.box2d.Contact;
import com.bensoft.mulg2.game.Channel;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.model.Ball;
import com.bensoft.mulg2.view.tiles.TwoStateTileView;

/**
 *
 * @author Ben Wolsieffer
 */
public class SwitchRaisedBlockTileModel extends RaisedBlockTileModel<TwoStateTileView> {

    private final Channel channel;

    public SwitchRaisedBlockTileModel(Level level, float x, float y, int data) {
        super(level, x, y, data, new TwoStateTileView(0));
        channel = level.getChannel(data & 0xf);
    }

    private boolean switchOn = false;

    @Override
    protected void onBallTouch(Ball ball, Contact contact) {
        super.onBallTouch(ball, contact);
        switchOn = !switchOn;
        channel.toggleActivated();
        view.getSprite().setCurrentTileIndex(switchOn ? 1 : 0);
    }

}
