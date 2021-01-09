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

function start() {
  if (cm.haveItem(4031045)) {
    if (cm.getEventNotScriptOpen("Genio")) {
      cm.sendYesNo(
        "It looks like it's full of empty seats for this trip. Please have your ticket in hand to board. The journey will be long, but it will reach your destination well. What do you think? Want to embark on this?"
      );
    } else {
      cm.sendOk(
        "I'm sorry, but Genius is already FULL. We cannot accept any more passengers. Please get on board next."
      );
      cm.dispose();
    }
  } else {
    cm.sendOk(
      "Oh no ... I don't think I have your ticket with you. You can't board without it. Please buy the ticket at the ticket counter."
    );
    cm.dispose();
  }
}
function action(mode, type, selection) {
  if (mode <= 0) {
    cm.sendOk("Okay, talk to me when you want again!");
    cm.dispose();
    return;
  }
  cm.gainItem(4031045, -1);
  cm.warp(260000110);
  cm.dispose();
}
