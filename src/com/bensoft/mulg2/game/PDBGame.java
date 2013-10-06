/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.game;

import android.content.Context;

import com.bensoft.mulg2.game.level.PDBLevel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Ben Wolsieffer
 */
public class PDBGame extends Game {

    private static final int LEVL_PDB_TYPE = 0x4c65766c;
    private static final int LEVF_PDB_TYPE_1 = 0x4c657646;
    private static final int LEV_PDB_TYPE_2 = 0x4c65764E;
    public static final int MULG_PDB_TYPE = 0x4d756c67;
    private final String pdbFileName;
    private final Context context;

    public PDBGame(Context ctx, String pdbFileName) throws FileNotFoundException, IOException {
        super();
        context = ctx;
        this.pdbFileName = pdbFileName;
        load();
    }

    private String getString(ByteBuffer content, int max) {
        // Read a string from content
        String s = "";
        byte c;
        int read = 0;
        for (int i = 0; (i < max) && ((c = content.get()) != 0); i++) {
            s += (char) c;
            read++;
        }
        content.position(content.position() + (max - read) - 1);
        return s;
    }

    private String getString(ByteBuffer content) {
        // Read a string from content
        String s = "";
        byte c;
        while ((c = content.get()) != 0) {
            s += (char) c;
        }
        return s;
    }

    private void load() throws FileNotFoundException, IOException {
        InputStream in = context.getAssets().open(pdbFileName);
        // find out file size
        long fileSize = in.available();
        // and fail if larger than 64k
        if (fileSize > 65536) {
            throw new IOException("Cannot open .pdb files larger than 64k");
        }
        // prepare a byte-array to read to.
        byte[] contentArr = new byte[(int) fileSize];
        int total = in.read(contentArr, 0, (int) fileSize);
        ByteBuffer content = ByteBuffer.wrap(contentArr);
        content.order(ByteOrder.BIG_ENDIAN);
        // and read

        // and close input stream
        in.close();
        // now create a new game that will be returned.
        // read the game header : name, properties, etc :
        name = getString(content, 32);
        // read attributes (0x0000)
        content.getShort();
        // read version (0x0001 / 0x0002)
        content.getShort();
        creationDate = content.getInt();
        modificationDate = content.getInt();
        // read last backup date
        content.getInt();
        modificationCount = content.getInt();
        // read app info (0x0000)
        content.getInt();
        // read sort info (0x0000)
        content.getInt();
        // Check that it's a Levl / LevF database
        int pdbType = content.getInt();
        if ((pdbType != LEVL_PDB_TYPE/*Levl*/) && (pdbType != LEVF_PDB_TYPE_1/*LevF*/) && (pdbType != LEV_PDB_TYPE_2/*LevF*/)) {
            throw new IOException("Not a Mulg II / IIf or IIn game file");
        }
        // Check that it's a Mulg database
        int appName = content.getInt();
        if (appName != MULG_PDB_TYPE/*Mulg*/) {
            throw new IOException("Not a Mulg game file");
        }
        // read two Ints ???
        content.getInt();
        content.getInt();
        // Number of chunks
        int chunksCount = content.getShort();
        int[] offsets = new int[chunksCount];
        int levelCount = chunksCount - 2; // -2 for the game info chunk and the author chunk
        levels = new PDBLevel[levelCount];
        for (int i = 0; i < chunksCount; i++) {
            offsets[i] = content.getInt();
            content.getInt();
        }
        // now parse one chunk at a time.
        for (int chk = 0; chk < chunksCount; chk++) {
            int length;
            if (chk == chunksCount - 1) {
                length = total - offsets[chk];
            } else {
                length = offsets[chk + 1] - offsets[chk];
            }
            content.position(offsets[chk]);
            ByteBuffer chunk = content.slice();
            chunk.limit(length);
            //System.arraycopy(content, offsets[chk], chunk, 0, length);
            switch (chk) {
                case 0:
                    // the first chunk is the game info chunk
                    // read a short ???
                    chunk.getShort();
                    // read the debug flag
                    if (chunk.getShort() == 0xffff) {
                        debug = true;
                        // remove the "(D)" from the name
                        if (name.endsWith(" (D)")) {
                            name = name.substring(0, name.indexOf(" (D)"));
                        }
                    }
                    break;
                case 1:
                    // the second chunk is the author & notes chunk
                    int[] noffsets = new int[MAX_NOTES];
                    // This chunk is a bit more complicated, with offsets all around
                    int ofs = chunk.getShort();
                    int noteCount = 0;
                    // the terminator string is "20", for some strange reason.
                    while (ofs != 0x3230/*20*/) {
                        noffsets[noteCount] = ofs;
                        ofs = chunk.getShort();
                        noteCount++;
                    }
                    // take care of the notes
                    // subtract 1 because the first note is the author's name
                    noteCount--;
                    notes = new String[noteCount];
                    for (int i = 0; i < noteCount; i++) {
                        String s = getString(chunk);
                        switch (i) {
                            case 0:
                                // first note is the author name
                                s = s.substring(2);
                                author = s;
                                break;
                            default:
                                // all other notes are real notes
                                notes[i - 1] = s;
                        }
                    }
                    break;
                default:
                    // all other chunks are level chunks
                    // create a new level

                    // and set it's properties
                    String levelName = getString(chunk, 32);
                    byte width = chunk.get();
                    byte height = chunk.get();
                    PDBLevel level = new PDBLevel(context, pdbFileName, offsets[chk] + chunk.position(), length, width, height, levelName);
//                    // and read the cells
//                    for (int y = 0; y < level.height; y++) {
//                        for (int x = 0; x < level.width; x++) {
//                            int place = getChunkShort();
//                            level.cells[x][y] = new Cell(place & 0xff, place >> 8).toMulgEd();
//                        }
//                    }
                    levels[chk - 2] = level;
            }
        }
        // A lot of garbage was created. make sure it's cleaned now,
        // when the user is willing to wait.
        System.gc();
    }
//    private static void writeString(ByteBuffer content, String s, int max) {
//        if (s.length() > max) {
//            s = s.substring(0, max);
//        }
//        content.put(s.getBytes());
//        byte[] filler = new byte[max-s.length()];
//        Arrays.fill(filler, (byte)0);
//        content.put(filler);
//    }
//
////    protected void writeChunk(int size) {
////        System.arraycopy(chunk, 0, content, index, size);
////        index += size;
////    }
//
//    /**
//     * Saves this game to a .pdb file.
//     *
//     * @param f The file path to save to.
//     * @throws IOException
//     */
//    public void saveToFile(String filename) throws IOException {
//        saveToFile(new File(name));
//    }
//
//    /**
//     * Saves this game to a .pdb file.
//     *
//     * @param f The file to save to.
//     * @throws IOException
//     */
//    public void saveToFile(File f) throws IOException {
//        // create the buffers
//        FileOutputStream out = new FileOutputStream(f);
//        ByteBuffer content = ByteBuffer.allocate(65536);
//        //content = new byte[65536];
//        // add the "(D)" if debug
//        if (debug) {
//            writeString(content, name + " (D)", 32);
//        } else {
//            writeString(content, name, 32);
//        }
//        // write PDB header
//        // attributes
//        content.putShort((short)0);
//        // version: 00 02
//        content.putShort((short)2);
//        content.putInt(creationDate);
//        content.putInt(modificationDate);
//        // lastbackup
//        content.putInt(0);
//        content.putInt(modificationCount);
//        // app info
//        content.putInt(0);
//        // sort info
//        content.putInt(0);
//
////		writeInt(0x4c657646);// "levF"
//        content.putInt(0x4c65764E); // "levN"
//        // "Mulg"
//        content.putInt(0x4d756c67);
//        // two Ints ???
//        content.putInt(0);
//        content.putInt(0);
//        // write chunks one by one
//        content.putShort((short)(levels.length + 2));
//        int offsetsIndex = index;
//        int chunksIndex = index + (8 * (levelCount + 2));
//        for (int chk = 0; chk < (levelCount + 2); chk++) {
//            index = offsetsIndex + (8 * chk);
//            writeInt(chunksIndex);
//            // ???
//            writeInt(0x60000000);
//            index = 0;
//            switch (chk) {
//                case 0:
//                    // first chunk if the game info.
//                    writeChunkShort(0);
//                    // write debug flag
//                    if (debug) {
//                        writeChunkShort(0xffff);
//                    } else {
//                        writeChunkShort(0);
//                    }
//                    // other stuff is only FFs ...
//                    for (int i = 0; i < 16; i++) {
//                        writeChunkInt(0xffffffff);
//                    }
//                    break;
//                case 1:
//                    // second chunk is author name and notes
//                    String s;
//                    int notesIndex = (noteCount + 1) * 2;
//                    // write notes one by one
//                    for (int nt = 0; nt < noteCount + 1; nt++) {
//                        index = nt * 2;
//                        writeChunkShort(notesIndex);
//                        switch (nt) {
//                            case 0:
//                                // first note if author name
//                                // add the string terminator of the offsets (0x3230)
//                                s = "20" + author;
//                                index = notesIndex;
//                                writeChunkString(s, s.length() + 1);
//                                break;
//                            default:
//                                // other notes are real notes.
//                                s = notes[nt - 1];
//                                index = notesIndex;
//                                writeChunkString(s, s.length() + 1);
//                        }
//                        notesIndex = index;
//                    }
//                    break;
//                default:
//                    // Other chunks are levels.
//                    PDBLevel level = levels[chk - 2];
//                    // write the level properties
//                    writeChunkString(level.name, 32);
//                    int dim = (((int) level.width) & 0xff) << 8;
//                    dim |= ((int) level.height) & 0xff;
//                    writeChunkShort(dim);
//                    // write the level tiles
//                    for (int y = 0; y < level.height; y++) {
//                        for (int x = 0; x < level.width; x++) {
//                            int place = (((int) level.cells[x][y].toFile().tile) & 0xff);
//                            place |= (((int) level.cells[x][y].toFile().data) & 0xff) << 8;
//                            writeChunkShort(place);
//                        }
//                    }
//            }
//            int chunkSize = index;
//            index = chunksIndex;
//            writeChunk(chunkSize);
//            chunksIndex += chunkSize;
//        }
//
//        int total = index;
//        // prepare the output string
//        FileOutputStream out = new FileOutputStream(f);
//        // write to file
//        out.write(content, 0, total);
//        out.flush();
//        out.close();
//        // A lot of garbase was created. Make sure it's collected now,
//        // while the user is willing to wait.
//        System.gc();
//    }
}
