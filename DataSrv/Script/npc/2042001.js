/*
  [Monster Carnival PQ] [By Haiku01]
*/
importPackage(Packages.server.maps);

var status = 0;
var rnk = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.sendOk("Alright then, I hope we can talk more next time.");
            cm.dispose();
            return;
        }
        if (mode == 1) status++;
        else status--;
        if (cm.getPlayer().getMap().getId() == 220000000) {
            if (status == 0) {
                cm.sendSimple(
                    "Hello, my name is Spieglemann, and I am responsible for the Monster Family. I'm trying to find players who are willing to fight, because they live against each other in a fair competition.\r\n\r\n#L0#I would like to participate#l\r\n#L1#What is the Monster Family?#l\r\n#L2#Exchange MapleCoins#l"
                );
            } else if (status == 1) {
                if (selection == 0) {
                    if (
                        (cm.getLevel() > 29 && cm.getLevel() < 51) ||
                        cm.getPlayer().isGM()
                    ) {
                        cm.getPlayer().saveLocation(
                            SavedLocationType.MONSTER_CARNIVAL
                        );
                        cm.warp(980000000, 0);
                        cm.dispose();
                        return;
                    } else {
                        cm.sendOk(
                            "I'm sorry, but only players of level 30 ~ 50 can participate."
                        );
                        cm.dispose();
                        return;
                    }
                } else if (selection == 1) {
                    cm.sendOk(
                        "What is Monster Family? Haha ha! Let's just say it is an experience I will never forget! It is a battle against other travelers like you, and I know it is very dangerous for you to fight each other using real weapons, nor would I like to suggest such a barbaric act. Not my friend, what I offer is the competition. The battle emotion and emotion against people as strong and motivated as yourself. This is the essence of the Monster Family. In addition, you can use your MapleCoins earned during the Monster Family and obtain new items and weapons! Of course, it's not that simple. There are different ways to prevent the other party from hunting monsters, and it's up to you to find out how. What do you think? Interested in a little friendly competition (or not so friendly)?"
                    );
                    cm.dispose();
                    status = 0;
                } else if (selection == 2) {
                    cm.sendSimple(
                        "Here you can buy a necklace, you need 50 MapleCoins.\r\n#L43#Click to win!#l"
                    );
                }
            } else if (selection == 43) {
                if (cm.haveItem(4001129, 50)) {
                    cm.gainItem(1122007, 1);
                    cm.gainItem(4001129, -50);
                    cm.dispose();
                } else {
                    cm.sendOk("You don't have enough coins!");
                    cm.dispose();
                }
            } else if (cm.getPlayer().getMap().isCPQWinnerMap()) {
                if (status == 0) {
                    if (cm.getPlayer().getParty() != null) {
                        var shi =
                            "Congratulations on your victory !!! It was really perfect! The opposite group can barely react! From now on this is what I expect from you whenever you participate!\r\n\r\n#bMonster Festival Rank: ";
                        switch (cm.calculateCPQRanking()) {
                            case 1:
                                shi += "A";
                                rnk = 1;
                                cm.sendOk(shi);
                                break;
                            case 2:
                                shi += "B";
                                rnk = 2;
                                cm.sendOk(shi);
                                break;
                            case 3:
                                shi += "C";
                                rnk = 3;
                                cm.sendOk(shi);
                                break;
                            case 4:
                                shi += "D";
                                rnk = 4;
                                cm.sendOk(shi);
                                break;
                            default:
                                cm.sendOk(
                                    "There was an error with the Monster Family."
                                );
                        }
                    } else {
                        cm.warp(980000000, 0);
                        cm.dispose();
                    }
                } else if (status == 1) {
                    switch (rnk) {
                        case 1:
                            cm.warp(980000000, 0);
                            cm.gainExp(30000);
                            cm.dispose();
                            break;
                        case 2:
                            cm.warp(980000000, 0);
                            cm.gainExp(25500);
                            cm.dispose();
                            break;
                        case 3:
                            cm.warp(980000000, 0);
                            cm.gainExp(21000);
                            cm.dispose();
                            break;
                        case 4:
                            cm.warp(980000000, 0);
                            cm.gainExp(19550);
                            cm.dispose();
                            break;
                        default:
                            cm.warp(980000000, 0);
                            cm.gainExp(19550);
                    }
                }
            } else if (cm.getPlayer().getMap().isCPQLoserMap()) {
                if (status == 0) {
                    if (cm.getPlayer().getParty() != null) {
                        var shiu =
                            "Sorry for the loss ... However, you did very well and put on a show! From now on this is what I expect from you whenever you participate, performance!\r\n\r\nMonster Festival Rank: ";
                        switch (cm.calculateCPQRanking()) {
                            case 10:
                                shiu += "A";
                                rnk = 10;
                                cm.sendOk(shiu);
                                break;
                            case 20:
                                shiu += "B";
                                rnk = 20;
                                cm.sendOk(shiu);
                                break;
                            case 30:
                                shiu += "C";
                                rnk = 30;
                                cm.sendOk(shiu);
                                break;
                            case 40:
                                shiu += "D";
                                rnk = 40;
                                cm.sendOk(shiu);
                                break;
                            default:
                                cm.sendOk(
                                    "There was an error with the Monster Family."
                                );
                        }
                    } else {
                        cm.warp(980000000, 0);
                        cm.dispose();
                    }
                }
            } else if (status == 1) {
                switch (rnk) {
                    case 10:
                        cm.warp(980000000, 0);
                        cm.gainExp(10000);
                        cm.dispose();
                        break;
                    case 20:
                        cm.warp(980000000, 0);
                        cm.gainExp(8500);
                        cm.dispose();
                        break;
                    case 30:
                        cm.warp(980000000, 0);
                        cm.gainExp(7000);
                        cm.dispose();
                        break;
                    case 40:
                        cm.warp(980000000, 0);
                        cm.gainExp(4550);
                        cm.dispose();
                        break;
                }
            }
        }
    }
}
