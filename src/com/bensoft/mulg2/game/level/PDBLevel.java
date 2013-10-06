package com.bensoft.mulg2.game.level;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.andengine.util.debug.Debug;

import android.content.Context;

import com.bensoft.mulg2.model.tiles.TileModel;
import com.bensoft.mulg2.model.tiles.TileModelRegistry;

public class PDBLevel extends Level {

    public static final int MAX_WIDTH = 37;
    public static final int MAX_HEIGHT = 33;
    private final String pdbFileName;
    private final int length;
    private final int offset;
    private final Context context;

    public PDBLevel(Context context, String pdbFileName, int offset, int length, int width, int height, String name) {
        super();
        this.context = context;
        this.pdbFileName = pdbFileName;
        this.width = width;
        this.height = height;
        this.offset = offset;
        this.length = length;
        this.name = name;
    }

    private void loadTiles() throws FileNotFoundException, IOException {
        byte[] chunkArr = new byte[length];
        InputStream in = context.getAssets().open(pdbFileName);
        in.skip(offset);
        in.read(chunkArr);
        in.close();
        ByteBuffer chunk = ByteBuffer.wrap(chunkArr);
        tiles = new TileModel[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int place = chunk.getShort();

                TileModel<?> t = TileModelRegistry.createFromNum(place & 0xff, (place >> 8) & 0xff, this, x, y);
                tiles[x][y] = t;
            }
        }
    }

    @Override
    public void load() {
        super.load();
        try {
            loadTiles();
        } catch (IOException ex) {
            Debug.e(ex);
        }
    }

    /**
     * Returns the name of the level.
     *
     * @return the name of the level
     */
    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the cell at the specified x and y coordinates.
     *
     * @param x the 0 based column index
     * @param y the 0 based row index
     * @return
     */
    @Override
    public TileModel<?> getTileAt(int x, int y) {
        if (tiles == null) {
            try {
                loadTiles();
            } catch (FileNotFoundException ex) {
                System.err.println("Unable to load .pdb file");
            } catch (IOException ex) {
                System.err.println("Unable to read .pdb file");
            }
        }
        return super.getTileAt(x, y);
    }
}
