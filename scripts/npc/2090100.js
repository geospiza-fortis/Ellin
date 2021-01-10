/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
                       Matthias Butz <matze@odinms.de>
                       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation. You may not use, modify
    or distribute this program under any other version of the
    GNU Affero General Public License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/* Grandpa Luo
	Mu Lung VIP Hair/Hair Color Change.
*/
var status = 0;
var beauty = 0;
var mhair = Array(30250, 30350, 30270, 30150, 30300, 30600, 30160);
var fhair = Array(
    31040,
    31250,
    31310,
    31220,
    31300,
    31680,
    31160,
    31030,
    31230
);
var hairnew = Array();

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
        if (mode == 1) status++;
        else status--;
        if (status == 0) {
            cm.sendSimple(
                "Welcome to the Mu Lung hair shop. If you have a #b#t5150025##k, or a #b#t5151020##k, allow me to take care of your hairdo. Please choose the one you want.\r\n#L0##i5150025##t5150025##l\r\n#L1##i5151020##t5151020##l"
            );
        } else if (status == 1) {
            if (selection == 0) {
                beauty = 1;
                hairnew = Array();
                if (cm.getPlayer().getGender() == 0) {
                    for (var i = 0; i < mhair.length; i++) {
                        hairnew.push(
                            mhair[i] + parseInt(cm.getPlayer().getHair() % 10)
                        );
                    }
                }
                if (cm.getPlayer().getGender() == 1) {
                    for (var i = 0; i < fhair.length; i++) {
                        hairnew.push(
                            fhair[i] + parseInt(cm.getPlayer().getHair() % 10)
                        );
                    }
                }
                cm.sendStyle(
                    "I can totally change up your hairstyle and make it look so good. Why don't you change it up a bit? With #b#t5150025##k, I'll take care of the rest for you. Choose the style of your liking!",
                    hairnew
                );
            } else if (selection == 1) {
                beauty = 2;
                haircolor = Array();
                var current = parseInt(cm.getPlayer().getHair() / 10) * 10;
                for (var i = 0; i < 8; i++) {
                    haircolor.push(current + i);
                }
                cm.sendStyle(
                    "I can totally change your haircolor and make it look so good. Why don't you change it up a bit? With #b#t5151020##k, I'll take care of the rest. Choose the color of your liking!",
                    haircolor
                );
            }
        } else if (status == 2) {
            if (beauty == 1) {
                if (cm.haveItem(5150025)) {
                    cm.gainItem(5150025, -1);
                    cm.setHair(hairnew[selection]);
                    cm.sendOk("Enjoy your new and improved hairstyle!");
                    cm.dispose();
                } else {
                    cm.sendOk(
                        "Hmmm...it looks like you don't have our designated coupon...I'm afraid I can't give you a haircut without it. I'm sorry..."
                    );
                    cm.dispose();
                }
            }
            if (beauty == 2) {
                if (cm.haveItem(5151020)) {
                    cm.gainItem(5151020, -1);
                    cm.setHair(haircolor[selection]);
                    cm.sendOk("Enjoy your new and improved haircolor!");
                    cm.dispose();
                } else {
                    cm.sendOk(
                        "Hmmm...it looks like you don't have our designated coupon...I'm afraid I can't dye your hair without it. I'm sorry..."
                    );
                    cm.dispose();
                }
            }
        }
    }
}