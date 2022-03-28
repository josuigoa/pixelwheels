/*
 * Copyright 2022 Aurélien Gâteau <mail@agateau.com>
 *
 * This file is part of Pixel Wheels.
 *
 * Tiny Wheels is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.agateau.pixelwheels;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.agateau.pixelwheels.vehicledef.VehicleDef;
import com.agateau.pixelwheels.vehicledef.VehicleIO;
import com.agateau.utils.FileUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.XmlReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class VehicleIOTest {
    @Test
    public void getVehicleWithoutWheels() {
        XmlReader.Element root =
                FileUtils.parseXml(
                        String.join(
                                "\n",
                                "<vehicle speed='1' name='Bob' width='30' height='50'>",
                                "    <main image='img'/>",
                                "    <shapes>",
                                "        <octogon corner='1' width='30' height='50'/>",
                                "    </shapes>",
                                "</vehicle>"));
        assertNotNull(root);
        VehicleDef def = VehicleIO.get(root, "id");

        assertThat((int) def.boundingRect.width, is(50));
        assertThat((int) def.boundingRect.height, is(30));
        assertTrue(isCentered(def.boundingRect));
    }

    @Test
    public void getVehicleWithInvisibleWheels() {
        XmlReader.Element root =
                FileUtils.parseXml(
                        String.join(
                                "\n",
                                "<vehicle speed='1' name='Bob' width='30' height='50'>",
                                "    <main image='img'/>",
                                "    <shapes>",
                                "        <octogon corner='1' width='30' height='50'/>",
                                "    </shapes>",
                                "    <axle y='25' width='14' steer='1'/>",
                                "    <axle y='15' width='14'/>",
                                "</vehicle>"));
        assertNotNull(root);
        VehicleDef def = VehicleIO.get(root, "id");

        assertThat((int) def.boundingRect.width, is(50));
        assertThat((int) def.boundingRect.height, is(30));
        assertTrue(isCentered(def.boundingRect));
    }

    @Test
    public void getVehicleWithVisibleWheelsOnSides() {
        XmlReader.Element root =
                FileUtils.parseXml(
                        String.join(
                                "\n",
                                "<vehicle speed='1' name='Bob' width='30' height='50'>",
                                "    <main image='img'/>",
                                "    <shapes>",
                                "        <octogon corner='1' width='30' height='50'/>",
                                "    </shapes>",
                                "    <axle y='25' width='30' steer='1'/>",
                                "    <axle y='15' width='30'/>",
                                "</vehicle>"));
        assertNotNull(root);
        VehicleDef def = VehicleIO.get(root, "id");

        assertThat((int) def.boundingRect.height, is(46));
        assertThat((int) def.boundingRect.width, is(50));
        assertTrue(isCentered(def.boundingRect));
    }

    @Test
    public void getVehicleWithVisibleWheelsTopBottom() {
        XmlReader.Element root =
                FileUtils.parseXml(
                        String.join(
                                "\n",
                                "<vehicle speed='1' name='Bob' width='30' height='50'>",
                                "    <main image='img'/>",
                                "    <shapes>",
                                "        <octogon corner='1' width='30' height='50'/>",
                                "    </shapes>",
                                "    <axle y='0' width='14' steer='1'/>",
                                "    <axle y='50' width='14'/>",
                                "</vehicle>"));
        assertNotNull(root);
        VehicleDef def = VehicleIO.get(root, "id");

        assertThat((int) def.boundingRect.height, is(30));
        assertThat((int) def.boundingRect.width, is(66));
        assertTrue(isCentered(def.boundingRect));
    }

    private static boolean isCentered(Rectangle rect) {
        return (int) rect.x + (int) rect.width / 2 == 0
                && (int) rect.y + (int) rect.height / 2 == 0;
    }
}
