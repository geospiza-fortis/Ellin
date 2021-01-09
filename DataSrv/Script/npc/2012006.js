/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
					   Matthias Butz <matze@odinms.de>
					   Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
-- Odin JavaScript --------------------------------------------------------------------------------
	Platform Usher - Orbis Ticketing Booth(200000100)
-- By ---------------------------------------------------------------------------------------------
	Information
-- Version Info -----------------------------------------------------------------------------------
	1.1 - Text fix [Information]
	1.0 - First Version by Information
---------------------------------------------------------------------------------------------------
**/
importPackage(Packages.client);

var mapid = new Array(200000110, 200000120, 200000130, 200000140, 200000150);
var platform = new Array("Ellinia", "Ludibrium", "Leafre", "Mu Lung", "Ariant");
var flight = new Array("ship", "ship", "ship", "Hak", "Geenie");
var menu;
var select;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 0) {
            cm.sendOk(
                "Please make sure you know where you are going and then go to the platform through me. The tour is on schedule, so it's best not to miss it!"
            );
            cm.dispose();
            return;
        }
        if (mode == 1) status++;
        else status--;
        if (status == 0) {
            menu =
                "Orbis Station has many platforms to choose from. You need to choose the one that will lead to the chosen destination. Which platform will you take?";
            for (var i = 0; i < platform.length; i++) {
                menu +=
                    "\r\n#L" +
                    i +
                    "##bThe platform for the ship heading to " +
                    platform[i] +
                    "#k#l";
            }
            cm.sendSimple(menu);
        } else if (status == 1) {
            select = selection;
            cm.sendYesNo(
                "Even if you take the wrong ticket, you can come back here using the portal. Do you want to go to the #platform of the ship going to " +
                    platform[select] +
                    "#k?"
            );
        } else if (status == 2) {
            cm.warp(mapid[select], 0);
            cm.dispose();
        }
    }
}
