/*
 * @autor GabrielSin
 * LeaderMS Revision
 */

var status = 0;

importPackage(Packages.client);

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
            copns = cm.getPlayer().countItem(4031868);
            if (copns < 1) {
                cm.sendOk("Que pena, voc� nao conseguiu nenhuma j�ia!");
                cm.dispose();
            }
            if (copns > 0 || !cm.getPlayer().isGM()) {
                cm.sendNext(
                    "Ok, vamos ver...Voc� foi muito bem, e voc� trouxe #b" +
                        copns +
                        "#k j�ias que eu adoro. Como voc� completou a partida, vou recompens�-lo com a pontua��o da Arena de Batalhas de #b5 Pontos#k. Se voc� quiser saber mais sobre a pontua��o de Arena de Batalha, ent�o fale com #b#p2101015##k."
                );
            }
        } else if (status == 1) {
            //cm.warp(980010020, 0);
            cm.removeAll(4031868);
            cm.getPlayer().gainExp(
                92.7 * cm.getPlayer().getExpRate() * copns,
                true,
                true
            );
            cm.getPlayer().gainAriantPontos(3);
            cm.dispose();
        }
    }
}
