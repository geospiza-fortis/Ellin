function start() {
    if (cm.haveItem(4031576)) {
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
    cm.gainItem(4031576, -1);
    cm.warp(200000152, 0);
    cm.dispose();
}
