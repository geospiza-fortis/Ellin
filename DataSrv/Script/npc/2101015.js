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
            apqpontos = cm.getPlayer().getAriantPontos();
            if (apqpontos < 100) {
                cm.sendOk(
                    "A sua Pontua��o de Arena de Batalha � #b" +
                        apqpontos +
                        "#k Pontos. Voc� precisa ultrapassar os #b100 Pontos#k para que eu possa lhe dar a #bCadeira de Praia com Palmeira#k.Estou ocupado, ent�o fale comigo quando voc� tiver pontos suficientes e fale comigo novamente."
                );
                cm.dispose();
            }
            if (apqpontos > 99) {
                cm.sendNext(
                    "Uaaal, parece que voc� conseguiu os #b100 Pontos#k necess�rios para troca, vamos l�?!"
                );
            }
        } else if (status == 1) {
            cm.getPlayer().gainAriantPontos(-100);
            cm.gainItem(3010018, 1);
            cm.dispose();
        }
    }
}
