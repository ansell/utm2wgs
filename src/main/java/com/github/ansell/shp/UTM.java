/*
 * Copyright (c) 2016, Peter Ansell
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Originally licensed as CC-BY-SA-3.0 by user2548538 on Stack Overflow 
 * <https://stackoverflow.com/users/2548538/user2548538">
 */
package com.github.ansell.shp;

import java.util.Locale;

/**
 * Copied from Stack Overflow, licensed under
 * <a href="https://creativecommons.org/licenses/by-sa/3.0/">CC-BY-SA</a> per
 * the Stack Overflow content policy. Migrated to CC-BY-SA-4.0 per the licensing
 * conditions of CC-BY-SA-3.0, and then licensed under GPL-3.0 or later per the
 * <a href=
 * "https://creativecommons.org/share-your-work/licensing-considerations/compatible-licenses/">
 * Compatible Licenses declarations</a> for CC-BY-SA-4.0.
 * 
 * @author user2548538
 * @see <a href="https://stackoverflow.com/users/2548538/user2548538">Stack
 *      Overflow user</a>
 * @see <a href="https://stackoverflow.com/a/28224544">Stack Overflow</a>
 */
public class UTM {
    private double easting;
    private double northing;
    private int zone;
    private char letter;

    public double getEasting() {
        return easting;
    }

    public double getNorthing() {
        return northing;
    }

    public int getZone() {
        return zone;
    }

    public char getLetter() {
        return letter;
    }

    public String toString() {
        return String.format("%s %c %s %s", zone, letter, easting, northing);
    }

    /**
     * Tests the exact representation. There might be more representations for
     * the same geographical point with different letters or zones, but that is
     * not taken into account.
     */
    public boolean equals(Object o) {
        if (o instanceof UTM) {
            UTM other = (UTM) o;
            return (zone == other.zone) && (letter == other.letter) && (easting == other.easting)
                    && (northing == other.northing);
        }
        return false;
    }

    @Override
    public int hashCode() {
        long least = Double.doubleToRawLongBits(easting);
        long lnort = Double.doubleToRawLongBits(northing);
        long x = least ^ lnort;
        return (int) (x ^ (x >>> 32));
    }

    public UTM(int zone, char letter, double easting, double northing) {
        this.zone = zone;
        this.letter = Character.toUpperCase(letter);
        this.easting = easting;
        this.northing = northing;
    }

    public UTM(String utm) {
        String[] parts = utm.split(" ");
        zone = Integer.parseInt(parts[0]);
        letter = parts[1].toUpperCase(Locale.ENGLISH).charAt(0);
        easting = Double.parseDouble(parts[2]);
        northing = Double.parseDouble(parts[3]);
    }

    public static UTM fromWGS84(WGS84 wgs) {
        return fromWGS84(wgs.getLatitude(), wgs.getLongitude());
    }

    public static UTM fromWGS84(double latitude, double longitude) {
        int zone = (int) Math.floor(longitude / 6 + 31);
        char letter;
        if (latitude < -72)
            letter = 'C';
        else if (latitude < -64)
            letter = 'D';
        else if (latitude < -56)
            letter = 'E';
        else if (latitude < -48)
            letter = 'F';
        else if (latitude < -40)
            letter = 'G';
        else if (latitude < -32)
            letter = 'H';
        else if (latitude < -24)
            letter = 'J';
        else if (latitude < -16)
            letter = 'K';
        else if (latitude < -8)
            letter = 'L';
        else if (latitude < 0)
            letter = 'M';
        else if (latitude < 8)
            letter = 'N';
        else if (latitude < 16)
            letter = 'P';
        else if (latitude < 24)
            letter = 'Q';
        else if (latitude < 32)
            letter = 'R';
        else if (latitude < 40)
            letter = 'S';
        else if (latitude < 48)
            letter = 'T';
        else if (latitude < 56)
            letter = 'U';
        else if (latitude < 64)
            letter = 'V';
        else if (latitude < 72)
            letter = 'W';
        else
            letter = 'X';
        double easting = 0.5
                * Math.log((1 + Math.cos(latitude * Math.PI / 180)
                        * Math.sin(longitude * Math.PI / 180 - (6 * zone - 183) * Math.PI / 180))
                        / (1 - Math.cos(latitude * Math.PI / 180) * Math
                                .sin(longitude * Math.PI / 180 - (6 * zone - 183) * Math.PI / 180)))
                * 0.9996 * 6399593.62
                / Math.pow(
                        (1 + Math.pow(0.0820944379, 2)
                                * Math.pow(Math.cos(latitude * Math.PI / 180), 2)),
                        0.5)
                * (1 + Math.pow(0.0820944379, 2) / 2 * Math.pow((0.5 * Math.log((1
                        + Math.cos(latitude * Math.PI / 180) * Math
                                .sin(longitude * Math.PI / 180 - (6 * zone - 183) * Math.PI / 180))
                        / (1 - Math.cos(latitude * Math.PI / 180) * Math.sin(
                                longitude * Math.PI / 180 - (6 * zone - 183) * Math.PI / 180)))),
                        2) * Math.pow(Math.cos(latitude * Math.PI / 180), 2) / 3)
                + 500000;
        easting = Math.round(easting * 100) * 0.01;
        double northing = (Math
                .atan(Math.tan(latitude * Math.PI / 180)
                        / Math.cos((longitude * Math.PI / 180 - (6 * zone - 183) * Math.PI / 180)))
                - latitude * Math.PI / 180) * 0.9996
                * 6399593.625
                / Math.sqrt(
                        1 + 0.006739496742
                                * Math.pow(Math.cos(latitude * Math.PI / 180),
                                        2))
                * (1 + 0.006739496742 / 2 * Math.pow(0.5 * Math.log((1
                        + Math.cos(latitude * Math.PI / 180) * Math.sin(
                                (longitude * Math.PI / 180 - (6 * zone - 183) * Math.PI / 180)))
                        / (1 - Math.cos(latitude * Math.PI / 180) * Math.sin(
                                (longitude * Math.PI / 180 - (6 * zone - 183) * Math.PI / 180)))),
                        2)
                        * Math
                                .pow(Math
                                        .cos(latitude * Math.PI
                                                / 180),
                                        2))
                + 0.9996 * 6399593.625
                        * (latitude * Math.PI / 180
                                - 0.005054622556 * (latitude * Math.PI / 180
                                        + Math.sin(2 * latitude * Math.PI / 180)
                                                / 2)
                                + 4.258201531e-05 * (3
                                        * (latitude * Math.PI / 180
                                                + Math.sin(2 * latitude * Math.PI / 180) / 2)
                                        + Math.sin(2 * latitude * Math.PI / 180)
                                                * Math.pow(Math.cos(latitude * Math.PI / 180), 2))
                                        / 4
                                - 1.674057895e-07 * (5 * (3
                                        * (latitude * Math.PI / 180
                                                + Math.sin(2 * latitude * Math.PI / 180) / 2)
                                        + Math.sin(2 * latitude * Math.PI / 180)
                                                * Math.pow(Math.cos(latitude * Math.PI / 180), 2))
                                        / 4
                                        + Math.sin(2 * latitude * Math.PI / 180)
                                                * Math.pow(Math.cos(latitude * Math.PI / 180), 2)
                                                * Math.pow(Math.cos(latitude * Math.PI / 180), 2))
                                        / 3);
        if (letter < 'M')
            northing = northing + 10000000;
        northing = Math.round(northing * 100) * 0.01;

        return new UTM(zone, letter, easting, northing);
    }

}
