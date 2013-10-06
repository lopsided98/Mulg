package com.bensoft.mulg2.model.tiles;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.andengine.util.debug.Debug;

import com.bensoft.mulg2.game.level.Level;

/**
 * 
 * @author Ben Wolsieffer
 */
public final class TileModelRegistry {

    @SuppressWarnings("unchecked")
    private static final Class<? extends TileModel<?>>[] standardTiles = new Class[147];
    @SuppressWarnings("unchecked")
    private static final Class<? extends TileModel<?>>[] specialTiles = new Class[15];

    static {
        registerTile(0, BlankTileModel.class);
        registerTile(4, StandardStoneTileModel.class);
        registerTile(5, EndingStoneTileModel.class);
        registerTile(6, StandardRaisedBlockTileModel.class);
        registerTile(7, NoteStoneTileModel.class);
        registerTile(9, SwitchRaisedBlockTileModel.class);
        registerTile(11, HorizontalDoorStoneTileModel.class);
        registerTile(15, VerticalDoorStoneTileModel.class);
        registerTile(19, HorizontalTiltingRampTileModel.class);
        registerTile(21, VerticalTiltingRampTileModel.class);
        registerTile(27, DimpleStoneTileModel.class);
        registerTile(28, BumpStoneTileModel.class);
        registerTile(35, KeyStoneTileModel.class);
        registerTile(38, HeavyMovableRaisedBlockTileModel.class);
        registerTile(40, BouncerRaisedBlockTile.class);
        registerTile(43, IceTileModel.class);
        registerTile(44, LeftOneWayStoneTileModel.class);
        registerTile(45, RightOneWayStoneTileModel.class);
        registerTile(46, UpOneWayStoneTileModel.class);
        registerTile(47, DownOneWayStoneTileModel.class);
        registerTile(1005, ReverserStoneTileModel.class);
        registerTile(1006, UnReverserStoneTileModel.class);
    }

    public static void load() {
    }

    private TileModelRegistry() {
    }

    public static void registerTile(int tileNum, Class<? extends TileModel<?>> tile) {
        if (tileNum >= 0 && tileNum <= 146) {
            standardTiles[tileNum] = tile;
        } else if (tileNum >= 1000 && tileNum <= 1014) {
            specialTiles[tileNum - 1000] = tile;
        }
    }

    public static TileModel<?> createFromNum(int tileNum, int data, Level level, float x, float y) {
        Class<? extends TileModel<?>> tileClass = null;
        try {
            // Convert the tile
            switch (tileNum) {
            // Pit tile
            case 0x03: {
                tileNum++; // Convert to stone tile
                data |= StandardStoneTileModel.PIT_DATA; // Make it a pit
            }
            break;
            // Stone tile
            case 0x04: {
                if (data == 0x20) {
                    tileNum = 1001;
                }
                if (data == 0x40) {
                    tileNum = 1005;
                }
                if (data == 0x80) {
                    tileNum = 1006;
                }
            }
            break;
            case 0x17: {
                if ((data & 0x80) != 0) {
                    data = 1;
                }
            }
            break;
            case 22: {
                tileNum = 21; // Vertical ramp
                data |= VerticalTiltingRampTileModel.BOTTOM_OPEN_DATA;
            }
            break;
            case 20: {
                tileNum = 19; // Horizontal ramp
                data |= HorizontalTiltingRampTileModel.RIGHT_OPEN;
            }
            break;
            }

            if (tileNum >= 0 && tileNum <= 146) {
                tileClass = standardTiles[tileNum];

            } else if (tileNum >= 1000 && tileNum <= 1014) {
                tileClass = specialTiles[tileNum - 1000];
            }
            if (tileClass != null) {
                Constructor<? extends TileModel<?>> c = tileClass
                        .getConstructor(
                        Level.class, float.class, float.class, int.class);
                TileModel<?> t = c.newInstance(level, x, y, data);
                Debug.i(t.toString());
                return t;
            }
        } catch (InstantiationException ex) {
            Debug.e("Failed to create " + tileClass.getSimpleName() + " in "
                    + level.getName() + " at position x = " + x + ", y = " + y
                    + ".");
        } catch (IllegalAccessException ex) {
            Debug.e("Failed to create tile: " + tileClass.getSimpleName()
                    + " due to an access violation.");
        } catch (NoSuchMethodException ex) {
            Debug.e("Tile: " + tileClass.getSimpleName()
                    + " is not a valid Tile.");
        } catch (IllegalArgumentException ex) {
            Debug.e("Tile: " + tileClass.getSimpleName()
                    + " is not a valid Tile.");
        } catch (InvocationTargetException ex) {
            Debug.e("Creation of tile: " + tileClass.getSimpleName()
                    + " failed with " + ex.getCause());
        }
        Debug.i("Could create tile num: " + tileNum + ", returning BlankTile.");
        return new BlankTileModel(level, x, y, data);
    }
}
