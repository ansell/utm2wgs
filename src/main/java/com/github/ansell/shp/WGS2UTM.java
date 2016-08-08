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

import java.util.Arrays;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 * Tool for converting from WGS decimal coordinates to UTM coordinates.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class WGS2UTM {

    public static void main(String... args) throws Exception {
        final OptionParser parser = new OptionParser();

        final OptionSpec<Void> help = parser.accepts("help").forHelp();
        final OptionSpec<Double> wgsLongitudeOpt = parser.acceptsAll(Arrays.asList("longitude"))
                .withRequiredArg().ofType(Double.class).required().describedAs("The WGS Longitude");
        final OptionSpec<Double> wgsLatitudeOpt = parser.acceptsAll(Arrays.asList("latitude"))
                .withRequiredArg().ofType(Double.class).required().describedAs("The WGS Latitude");

        OptionSet options = null;

        try {
            options = parser.parse(args);
        } catch (final OptionException e) {
            System.out.println(e.getMessage());
            parser.printHelpOn(System.out);
            throw e;
        }

        if (options.has(help)) {
            parser.printHelpOn(System.out);
            return;
        }

        UTM fromWGS = UTM.fromWGS84(wgsLatitudeOpt.value(options), wgsLongitudeOpt.value(options));

        System.out.println(fromWGS.toString());
    }
}
