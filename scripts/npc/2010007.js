/* NPC: Heracle
   Function: Guild Creation/Expasion/Disband (GMS-Like)
   Map: 200000301
   Author: Shedder
*/

var status = 0;
var sel;

function start() {
    partymembers = cm.getPartyMembers();
    if (cm.getPlayer().getGuildId() > 0) {
        cm.sendSimple(
            "Ento, como posso ajudar?\r\n#b#L0#Eu quero aumentar meu cl#l\r\n#L1#Eu quero desfazer meu cl#l"
        );
    } else {
        cm.sendNext("Ei... Por acaso voc se interessa por CLs?");
    }
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 1 && sel == 1) {
            cm.sendNext(
                "Bem pensado. Eu no gostaria de desfazer meu cl que j est to forte..."
            );
            cm.dispose();
            return;
        }
        if (
            (mode == 0 && status == 0) ||
            (mode == 0 && status == 2 && sel == 2) ||
            (mode == 0 && status == 2 && sel == 0)
        ) {
            cm.dispose();
            return;
        }
        if (mode == 1) status++;
        else status--;
        if (status == 1) {
            if (cm.getPlayer().getGuildId() > 0) {
                sel = selection;
                if (selection == 0) {
                    if (cm.getPlayer().getGuild().getCapacity() > 95) {
                        cm.sendNext(
                            "Seu cl parece ter crescido um pouco. Eu no posso mais aumentar seu cl."
                        );
                        cm.dispose();
                    } else {
                        if (
                            cm.getPlayer().getGuildId() < 1 ||
                            cm.getPlayer().getGuildRank() != 1
                        ) {
                            cm.sendNext(
                                "Ei, voc no  o Mestre do Cl!! Esta deciso s pode ser tomada pelo Mestre do Cl."
                            );
                            cm.dispose();
                        } else {
                            cm.sendNext(
                                "Voc est aqui para aumentar seu cl? Seu cl deve ter crescido um pouco~ Para aumentar seu cl, ele precisa ser recadastrado no nosso Quartel-General de Cls e isto vai exigir um pagamento pelo servio..."
                            );
                        }
                    }
                } else if (selection == 1) {
                    if (
                        cm.getPlayer().getGuildId() < 1 ||
                        cm.getPlayer().getGuildRank() != 1
                    ) {
                        cm.sendNext(
                            "Ei, voc no  o Mestre do Cl!! Esta deciso s pode ser tomada pelo Mestre do Cl."
                        );
                    } else {
                        cm.sendYesNo(
                            "Tem certeza de que deseja desfazer seu cl? Srio... lembre-se, se voc desfizer o cl, ele ser eliminado para sempre. Ah, e mais uma coisa. Se voc quiser desfazer seu cl, vai precisar pagar 200.000 mesos pelo custo do servio. Ainda quer fazer isto?"
                        );
                    }
                }
            } else {
                cm.sendSimple(
                    "#b#L0#O que  um cl?#l\r\n#L1#O que eu fao para criar um cl??#l\r\n#L2# Eu quero criar um cl#l"
                );
            }
        } else if (status == 2) {
            if (cm.getPlayer().getGuildId() > 0) {
                if (
                    cm.getPlayer().getGuildId() > 0 &&
                    cm.getPlayer().getGuildRank() == 1
                ) {
                    if (sel == 0) {
                        cm.sendYesNo(
                            "O custo do servio ser apenas #r" +
                                cm
                                    .getPlayer()
                                    .getGuild()
                                    .getIncreaseGuildCost(
                                        cm.getPlayer().getGuild().getCapacity()
                                    ) +
                                " mesos#k Voc gostaria de aumentar seu cl?"
                        );
                    } else if (sel == 1) {
                        if (cm.getPlayer().getMeso() < 200000) {
                            cm.sendNext(
                                "Ei, voc no tem o dinheiro para o servio... tem certeza de que tem dinheiro suficiente a?"
                            );
                            cm.dispose();
                        } else {
                            cm.getPlayer().disbandGuild();
                            cm.gainMeso(-200000);
                            cm.dispose();
                        }
                    }
                } else {
                    cm.sendNext(
                        "Ei, voc no  o Mestre do Cl!! Esta deciso s pode ser tomada pelo Mestre do Cl."
                    );
                    cm.dispose();
                }
            } else {
                sel = selection;
                if (selection == 0) {
                    cm.sendNext(
                        "Um cl ... bem, voc pode pensar nele como um pequeno grupo cheio de pessoas com interesses e objetivos parecidos. Alm disto, ele ser cadastrado no nosso Quartel-General de Cls para ser validado."
                    );
                    cm.dispose();
                } else if (selection == 1) {
                    cm.sendNext(
                        "Para fazer seu prprio cl, voc vai precisar estar, pelo menos, no nvel 10. Voc tambm vai precisar ter pelo menos 1.500.000 mesos com voc. Este  o preo para registrar seu cl."
                    );
                } else if (selection == 2) {
                    cm.sendYesNo("Certo, agora, voc quer criar um cl?");
                }
            }
        } else if (status == 3) {
            if (cm.getPlayer().getGuildId() > 0) {
                if (sel == 0) {
                    cm.getPlayer().increaseGuildCapacity();
                    cm.dispose();
                }
            } else {
                if (sel == 1) {
                    cm.sendNext(
                        "Para fazer um cl, voc vai precisar de 6 pessoas no total. Esses 6 devem estar no mesmo grupo e o lder deve vir falar comigo. Fique ciente tambm de que o lder do grupo tambm se torna o Mestre do Cl. Uma vez designado o Mestre do Cl, a posio permanece a mesma at que o Cl seja desfeito."
                    );
                } else if (sel == 2) {
                    if (cm.getPlayer().getLevel() < 10) {
                        cm.sendNext(
                            "Humm... Eu no acho que voc possua as qualificaes para ser um Mestre do Cl. Por favor, treine mais para se tornar Mestre do Cl."
                        );
                    } else if (cm.getPlayer().getParty() == null) {
                        cm.sendNext(
                            "Eu no me importo com o quo forte voc acha que seja... Para formar um cl, voc precisa estar em um grupo de 6. Crie um grupo e ento traga todos os membros aqui se realmente quiser criar um cl."
                        );
                    } else if (!cm.isLeader()) {
                        cm.sendNext("Voc no  o lder de um grupo.");
                    } else if (partymembers.size() < 6) {
                        cm.sendNext(
                            "Parece que voc no tem membros suficientes no seu grupo ou alguns dos membros no esto presentes. Preciso de todos os 6 membros aqui para cadastrar seu cl. Se seu grupo no consegue coordenar esta simples tarefa, voc devia pensar duas vezes antes de formar um cl."
                        );
                    } else if (
                        partymembers.get(1).getGuild() != null ||
                        partymembers.get(2).getGuild() != null ||
                        partymembers.get(3).getGuild() != null ||
                        partymembers.get(4).getGuild() != null ||
                        partymembers.get(5).getGuild() != null
                    ) {
                        cm.sendNext(
                            "Parece que h um traidor entre ns. Algum em seu grupo j faz parte de outro cl. Para formar um cl, todos do seu grupo precisam estar sem cl. Volte quando tiver resolvido o problema com o traidor."
                        );
                    } else if (
                        cm.isSendContractAvailable(cm.getPlayer().getParty())
                    ) {
                        cm.sendNext(
                            "Por favor, aguarde os membros responderem a solicitao para enviar novamente!"
                        );
                    } else if (cm.getPlayer().getGuildId() <= 0) {
                        cm.getPlayer().genericGuildMessage(1);
                    } else {
                        cm.sendNext("Desculpe, mas voc no pode criar um cl.");
                    }
                    cm.dispose();
                }
            }
        } else if (status == 4) {
            cm.sendNext(
                "Uma vez que 6 pessoas estejam reunidas, voc vai precisar de 1.500.000 mesos. Esse  o preo para registrar seu cl."
            );
        } else if (status == 5) {
            cm.sendNext(
                "Certo, para registrar seu cl, traga pessoas aqui~ Voc no pode criar um sem todos os 6.\r\nAh,  claro, os 6 no podem fazer parte de outro cl!"
            );
            cm.dispose();
        }
    }
}
