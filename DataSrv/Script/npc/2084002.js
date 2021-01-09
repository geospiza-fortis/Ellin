/*
 * @autor Java
 * JS-Maple MapleStory Private Server
 */

importPackage(Packages.client);
importPackage(Packages.server.maps);

/* Variables */
var status = 0;
var tempo = new Date();
var dia = tempo.getDay();
var ano = tempo.getFullYear();
var mes = tempo.getMonth();
var data = tempo.getDate();
var hora = tempo.getHours();
var min = tempo.getMinutes();
var seg = tempo.getSeconds();

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) status++;
        else status--;
        if (cm.getPlayer().getMapId() == 390009999) {
            if (status == 0) {
                cm.sendNext(
                    "Welcome to the Rico Dourado quest. You brought something for me?"
                );
            } else if (status == 1) {
                cm.sendSimple(
                    "What would you like to do?\r\n#b#L1#Change Letters#l#k\r\n#b#L2#Exchange EXP Eggs#l#k\r\n#b#L3#Enter the mission#l#k\r\n#b#L4#Other information#l#k"
                );
            } else if (selection == 1) {
                if (
                    cm.haveItem(3994102, 20) &&
                    cm.haveItem(3994103, 20) &&
                    cm.haveItem(3994104, 20) &&
                    cm.haveItem(3994105, 20)
                ) {
                    cm.gainItem(3994102, -20);
                    cm.gainItem(3994103, -20);
                    cm.gainItem(3994104, -20);
                    cm.gainItem(3994105, -20);
                    cm.gainItem(2430008, 1);
                    cm.sendOk("You just received the compass!");
                    cm.dispose();
                } else {
                    cm.sendOk("To make the switch, all letters are required.");
                    cm.dispose();
                }
            } else if (selection == 2) {
                ovosexp = cm.getPlayer().countItem(4001255);
                if (ovosexp > 0) {
                    cm.getPlayer().gainExpRiche();
                    cm.gainItem(4001255, -1);
                    cm.dispose();
                } else {
                    cm.sendOk("To make the exchange you need to have the Egg.");
                    cm.dispose();
                }
            } else if (selection == 3) {
                if ((hora >= 08 && hora < 09) || (hora >= 20 && hora < 21)) {
                    if (!cm.haveItem(2430008, 1)) {
                        cm.sendOk("You need the compass to enter!");
                        cm.dispose();
                        return;
                    }
                    cm.RichieEnter(cm.getClient());
                } else {
                    cm.sendOk("Not yet in business hours, check!");
                    cm.dispose();
                }
            } else if (selection == 4) {
                cm.sendOk(
                    "Opening hours are from 8:00 am to 9:00 am and 8:00 pm to 9:00 pm. To obtain the compass, it is necessary to have 20 pieces of each letter (N, E, W, S). The experience obtained through the eggs and according to the level of the character."
                );
                cm.dispose();
            }
        }
        if (cm.getPlayer().getMapId() == 100000000) {
            if (status == 0) {
                cm.sendNext(
                    "You would like to participate in the Rich Golden Mission?"
                );
            } else if (status == 1) {
                if (cm.getPlayer().getLevel() > 9) {
                    cm.getPlayer().saveLocation(SavedLocationType.RICHIE);
                    cm.warp(390009999, 0);
                    cm.dispose();
                } else {
                    cm.sendOk("You need to have at least lv. 10.");
                }
            }
        }
    }
}
