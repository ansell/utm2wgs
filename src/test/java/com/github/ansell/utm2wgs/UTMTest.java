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
 */
package com.github.ansell.utm2wgs;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import com.github.ansell.utm2wgs.UTM;

/**
 * Tests for {@link UTM}.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class UTMTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.github.ansell.utm2wgs.UTM#getEasting()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testGetEasting() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.utm2wgs.UTM#getNorthing()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testGetNorthing() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.utm2wgs.UTM#getZone()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testGetZone() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.utm2wgs.UTM#getLetter()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testGetLetter() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.utm2wgs.UTM#toString()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testToString() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.github.ansell.utm2wgs.UTM#UTM(int, char, double, double)}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testUTMIntCharDoubleDouble() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.utm2wgs.UTM#UTM(java.lang.String)}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testUTMString() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.github.ansell.utm2wgs.UTM#fromWGS84(com.github.ansell.utm2wgs.WGS84)}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testFromWGS84WGS84() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.github.ansell.utm2wgs.UTM#fromWGS84(double, double)}.
     */
    @Ignore("The TMcoords.dat uses false easting = 0 and false northing = 0, which aren't supported here yet.")
    @Test
    public final void testFromWGS84DoubleDouble() throws Exception {
        Path inputPath = Paths.get(new URI("file:/home/peter/Downloads/TMcoords.dat"));
        try (final BufferedReader reader = Files.newBufferedReader(inputPath,
                StandardCharsets.UTF_8);) {
            String line = null;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                String[] split = line.split(" ");
                UTM nextUTM = UTM.fromWGS84(Double.parseDouble(split[0]),
                        Double.parseDouble(split[0]));
                int eastingCompare = Double.compare(Double.parseDouble(split[2]),
                        nextUTM.getEasting());
                if (eastingCompare != 0) {
                    fail("Found easting values that didn't match for line number: " + lineCount
                            + " => " + line + " expected easting of: [" + split[2] + "] but found ["
                            + nextUTM.getEasting() + "] utm=" + nextUTM.toString());
                }
                int northingCompare = Double.compare(Double.parseDouble(split[3]),
                        nextUTM.getNorthing());
                if (northingCompare != 0) {
                    fail("Found northing values that didn't match for line number: " + lineCount
                            + " => " + line + " expected northing of: [" + split[3]
                            + "] but found [" + nextUTM.getNorthing() + "] utm="
                            + nextUTM.toString());
                }
            }
        }
    }

    @Ignore
    @Test
    public final void testGeotoolsUTMMethod1() throws Exception {
        CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326");
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:32632");
        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, false);
        //Envelope sourceGeometry = new Envelope(new Coordinate(-34.8290148, 150.6009081));
        org.locationtech.jts.geom.Geometry sourceGeometry = null;
        
        org.locationtech.jts.geom.Geometry targetGeometry = JTS.transform( sourceGeometry, transform);
        System.out.println(targetGeometry);
    }
    
    @Test
    public final void testGeotoolsUTMMethod3() throws Exception {
        Coordinate inputCoordinate = new Coordinate(0, 0);
        MathTransform transform = CRS.findMathTransform(CRS.decode("EPSG:4326"), CRS.decode("EPSG:3857"), false);
        Coordinate outputCoordinate = JTS.transform(inputCoordinate, inputCoordinate, transform); 
        System.out.println(inputCoordinate + " transformed to " + outputCoordinate);
    }
}
