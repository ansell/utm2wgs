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

import java.util.Arrays;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 * Tool for converting from UTM coordinates to WGS decimal coordinates.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class UTM2WGS {

    public static void main(String... args) throws Exception {
        final OptionParser parser = new OptionParser();

        final OptionSpec<Void> help = parser.accepts("help").forHelp();
        final OptionSpec<Integer> utmZoneOpt = parser.acceptsAll(Arrays.asList("z", "zone"))
                .withRequiredArg().ofType(Integer.class).required().describedAs("The UTM Zone");
        final OptionSpec<String> utmLetterOpt = parser.acceptsAll(Arrays.asList("l", "letter"))
                .withRequiredArg().ofType(String.class).required().describedAs("The UTM Letter");
        final OptionSpec<Double> utmEastingOpt = parser.acceptsAll(Arrays.asList("e", "easting"))
                .withRequiredArg().ofType(Double.class).required().describedAs("The UTM Easting");
        final OptionSpec<Double> utmNorthingOpt = parser.acceptsAll(Arrays.asList("n", "northing"))
                .withRequiredArg().ofType(Double.class).required().describedAs("The UTM Northing");
        final OptionSpec<Boolean> printWkt = parser.accepts("output-wkt").withOptionalArg()
                .ofType(Boolean.class).defaultsTo(Boolean.TRUE).describedAs("Output WKT version");

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

        WGS84 fromUTM = WGS84.fromUTM(utmZoneOpt.value(options),
                utmLetterOpt.value(options).charAt(0), utmEastingOpt.value(options),
                utmNorthingOpt.value(options));

        if (options.has(printWkt) && printWkt.value(options)) {
            System.out.println(fromUTM.toWKT());
        } else {
            System.out.println(fromUTM.toString());
        }
    }
}
