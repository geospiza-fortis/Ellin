/*
 * @author Soulfist
 * LeaderMS Revision
 * TODO
 */

importPackage(Packages.tools);

function start() {
    cm.sendSimple(
        "Parece que voce consiguiu chegar ate o topo da missao, parabens #h #... \r\n\r\n\t#b#L0#Sim, eu consegui!#l"
    );
}

function action(m, t, s) {
    cm.dispose();
    if (m > 0) {
        cm.warp(105040300, 0);
        cm.dispose();
    }
}
