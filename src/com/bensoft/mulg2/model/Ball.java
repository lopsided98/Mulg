package com.bensoft.mulg2.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.bensoft.mulg2.game.level.Level;
import com.bensoft.mulg2.view.BallView;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import static com.bensoft.mulg2.Constants.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ben Wolsieffer
 */
public class Ball extends Model<BallView> {

    public static class Item {

        private Type type;
        private int data;

        public enum Type {

            COIN_1,
            COIN_5,
            KEY,
            NOTE
        }

        public Item(Type type, int data) {
            this.type = type;
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public Type getType() {
            return type;
        }
    }
    private ArrayList<Item> inventory = new ArrayList<Item>(5);
    private List<Item> inventoryWrapper = Collections.unmodifiableList(inventory);

    public Ball(Level level, float x, float y) {
        super(level, x, y, new BallView());

    }

    @Override
    protected FixtureDef createFixtureDef() {
        return PhysicsFactory.createFixtureDef(BALL_DENSITY, BALL_ELASTICITY, BALL_FRICTION);
    }

    @Override
    protected Body createBody(float x, float y, FixtureDef fixtureDef) {
        Body b = PhysicsFactory.createCircleBody(level.getPhysicsWorld(), x * PIXELS_PER_METER, y * PIXELS_PER_METER, BALL_RADIUS_PIXELS, BodyDef.BodyType.DynamicBody, fixtureDef, PIXELS_PER_METER);
        b.setFixedRotation(true);
        return b;
    }

    public boolean addItem(Item item) {
        if (inventory.size() < 5) {
            inventory.add(item);
            return true;
        } else {
            return false;
        }
    }

    public List<Item> getItems() {
        return inventoryWrapper;
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }
}
