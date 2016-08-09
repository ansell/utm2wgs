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
package com.github.ansell.shp;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.geotools.referencing.factory.ReferencingFactoryContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.opengis.geometry.Geometry;
import org.opengis.parameter.ParameterDescriptorGroup;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.referencing.ReferenceIdentifier;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.GeographicCRS;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.cs.CartesianCS;
import org.opengis.referencing.operation.Conversion;
import org.opengis.referencing.operation.CoordinateOperationFactory;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.MathTransformFactory;
import org.opengis.referencing.operation.OperationMethod;
import org.opengis.util.GenericName;
import org.opengis.util.InternationalString;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

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
     * Test method for {@link com.github.ansell.shp.UTM#getEasting()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testGetEasting() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.shp.UTM#getNorthing()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testGetNorthing() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.shp.UTM#getZone()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testGetZone() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.shp.UTM#getLetter()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testGetLetter() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.shp.UTM#toString()}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testToString() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.github.ansell.shp.UTM#UTM(int, char, double, double)}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testUTMIntCharDoubleDouble() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.github.ansell.shp.UTM#UTM(java.lang.String)}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testUTMString() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.github.ansell.shp.UTM#fromWGS84(com.github.ansell.shp.WGS84)}.
     */
    @Ignore("TODO: Implement me!")
    @Test
    public final void testFromWGS84WGS84() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.github.ansell.shp.UTM#fromWGS84(double, double)}.
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
        com.vividsolutions.jts.geom.Geometry sourceGeometry = null;
        
        com.vividsolutions.jts.geom.Geometry targetGeometry = JTS.transform( sourceGeometry, transform);
        System.out.println(targetGeometry);
    }
    
    /**
     * Geotools method derived from https://stackoverflow.com/a/176925/638674
     */
    @Ignore
    @Test
    public final void testGeotoolsUTMMethod2() throws Exception {
        double utmZoneCenterLongitude = -123; // Center lon of zone, example:
                                              // zone 10 = -123
        int zoneNumber = 10; // zone number, example: 10
        double latitude = 0;
        double longitude = 0; // lat, lon in degrees

        MathTransformFactory mtFactory = ReferencingFactoryFinder.getMathTransformFactory(null);
        ReferencingFactoryContainer factories = new ReferencingFactoryContainer(null);

        GeographicCRS geoCRS = org.geotools.referencing.crs.DefaultGeographicCRS.WGS84;
        CartesianCS cartCS = org.geotools.referencing.cs.DefaultCartesianCS.GENERIC_2D;

        ParameterValueGroup parameters = mtFactory.getDefaultParameters("Transverse_Mercator");
        parameters.parameter("central_meridian").setValue(utmZoneCenterLongitude);
        parameters.parameter("latitude_of_origin").setValue(0.0);
        parameters.parameter("scale_factor").setValue(0.9996);
        parameters.parameter("false_easting").setValue(500000.0);
        parameters.parameter("false_northing").setValue(0.0);

        Map<String, String> properties = Collections.singletonMap("name", "WGS 84 / UTM Zone " + zoneNumber);
        CoordinateOperationFactory coordinateOperationFactory = ReferencingFactoryFinder.getCoordinateOperationFactory(null);
        OperationMethod method = null;
        Conversion definingConversion = coordinateOperationFactory.createDefiningConversion(properties, method, parameters);
        
        // The following is deprecated in GeoTools-15 so will not be available in future
        ProjectedCRS projCRS = factories.createProjectedCRS(properties, geoCRS, null, parameters,
                cartCS);

        MathTransform transform = CRS.findMathTransform(geoCRS, projCRS);

        double[] dest = new double[2];
        transform.transform(new double[] { longitude, latitude }, 0, dest, 0, 1);

        int easting = (int) Math.round(dest[0]);
        int northing = (int) Math.round(dest[1]);

    }

}
