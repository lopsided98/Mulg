package com.bensoft.mulg2.game;

/*
 This file is part of MulgEd - The Mulg game and level editor.
 Copyright (C) 1999-2003 Ilan Tayary, a.k.a. [NoCt]Yonatanz
 Website: http://mulged.sourceforge.net
 Contact: yonatanz at actcom.co.il (I do not like SPAM)

 MulgEd is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 MulgEd is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with MulgEd, in a file named COPYING; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
import java.util.Date;

import com.bensoft.mulg2.game.level.Level;

/**
 * This class represents the whole game database file. A game includes the
 * levels, notes, and other information. This class implements loading / saving,
 * manipulation, export / import and other stuff.
 */
public class Game {

    public static final int MAX_LEVELS = 256;
    public static final int MAX_NOTES = 256;
    protected String name;
    protected String author;
    protected Level[] levels;
    protected String[] notes;
    protected boolean debug;
    protected int creationDate;
    protected int modificationDate;
    protected int modificationCount;

    public Game() {
        name = "untitled";
        author = "";
        creationDate = now();
        modificationDate = creationDate;
        modificationCount = 0;
        debug = false;
    }

    public Level[] getLevels() {
        return levels;
    }

    public String getNote(int i) {
        return notes[i - 1];
    }

    /**
     * Returns the current time, in the PDB's format (palm date).
     * 
     * @return the current time in Palm format
     */
    public static int now() {
        return java2palmDate(new Date());
    }

    /**
     * Returns the the specified Palm date in Java format.
     * 
     * The Java date is milliseconds since 1970. The Palm date is seconds since
     * 1903. So basically we need two conversion methods, one from 1970 to 1903
     * and one from seconds to milliseconds.
     * 
     * @param palmDate
     *            the date in Palm format
     * @return the date in Java format
     */
    public static long palm2javaDate(int palmDate) {
        palmDate -= 2082844800;
        palmDate *= 1000;
        return palmDate;
    }

    /**
     * Returns the the specified Java date in Palm format.
     * 
     * The Java date is milliseconds since 1970. The Palm date is seconds since
     * 1903. So basically we need two conversion methods, one from milliseconds
     * to seconds and one from 1903 to 1970.
     * 
     * 
     * @param javaDate
     *            the date in Java format
     * @return the date in Palm format
     */
    public static int java2palmDate(long javaDate) {
        javaDate /= 1000;
        javaDate += 2082844800;
        return (int) javaDate;
    }

    /**
     * Returns the the specified Java date in Palm format.
     * 
     * The Java date is milliseconds since 1970. The Palm date is seconds since
     * 1903. So basically we need two conversion methods, one from milliseconds
     * to seconds and one from 1903 to 1970.
     * 
     * 
     * @param javaDate
     *            a Date object
     * @return the date in Palm format
     */
    public static int java2palmDate(Date javaDate) {
        return java2palmDate(javaDate.getTime());
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }
}
