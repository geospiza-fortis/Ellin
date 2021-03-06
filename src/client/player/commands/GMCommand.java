package client.player.commands;

import static client.player.commands.object.CommandProcessorUtil.getOptionalIntArg;

import client.Client;
import client.player.Player;
import client.player.PlayerJob;
import client.player.PlayerQuery;
import client.player.PlayerStat;
import client.player.commands.object.CommandExecute;
import client.player.commands.object.CommandProcessorUtil;
import client.player.inventory.Equip;
import client.player.inventory.Item;
import client.player.inventory.types.InventoryType;
import client.player.skills.PlayerSkillFactory;
import constants.CommandConstants.CoomandRank;
import constants.ItemConstants;
import constants.ServerProperties;
import handling.channel.ChannelServer;
import handling.world.CheaterData;
import handling.world.World;
import handling.world.service.BroadcastService;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import launch.Start;
import packet.creators.EffectPackets;
import packet.creators.PacketCreator;
import server.itens.InventoryManipulator;
import server.itens.ItemInformationProvider;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.maps.Field;
import server.maps.object.FieldObject;
import server.maps.object.FieldObjectType;
import server.maps.portal.Portal;
import tools.StringUtil;

/**
 * @author Emilyx3
 * @author GabrielSin (http://forum.ragezone.com/members/822844.html)
 */

public class GMCommand {

    public static CoomandRank getPlayerLevelRequired() {
        return CoomandRank.GM;
    }

    /** !hide - hides the current character */
    public static class Hide extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            PlayerSkillFactory
                .getSkill(9001004)
                .getEffect(1)
                .applyTo(c.getPlayer());
            return true;
        }
    }

    /** !drop <itemid> <quantity> */
    public static class Drop extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            final int itemId = Integer.parseInt(splitted[1]);
            final short quantity = (short) CommandProcessorUtil.getOptionalIntArg(
                splitted,
                2,
                1
            );
            ItemInformationProvider ii = ItemInformationProvider.getInstance();
            if (ItemConstants.isPet(itemId)) {
                c
                    .getPlayer()
                    .dropMessage(
                        5,
                        "Please purchase a pet from the cash shop instead."
                    );
            } else if (!ii.itemExists(itemId)) {
                c.getPlayer().dropMessage(5, itemId + " does not exist.");
            } else {
                Item toDrop;
                if (
                    ItemConstants.getInventoryType(itemId) ==
                    InventoryType.EQUIP
                ) {
                    toDrop = ii.randomizeStats((Equip) ii.getEquipById(itemId));
                } else {
                    toDrop =
                        new Item(itemId, (byte) 0, (short) quantity, (byte) 0);
                }
                c
                    .getPlayer()
                    .getMap()
                    .spawnItemDrop(
                        c.getPlayer(),
                        c.getPlayer(),
                        toDrop,
                        c.getPlayer().getPosition(),
                        true,
                        true
                    );
            }
            return true;
        }
    }

    /** !spawn <mobid> */
    public static class Spawn extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().yellowMessage("Syntax: !spawn <mobid>");
                return false;
            }

            MapleMonster monster = MapleLifeFactory.getMonster(
                Integer.parseInt(splitted[1])
            );
            if (monster == null) {
                c.getPlayer().yellowMessage("monster does not exist.");
                return false;
            }
            if (splitted.length > 2) {
                for (int i = 0; i < Integer.parseInt(splitted[2]); i++) {
                    c
                        .getPlayer()
                        .getMap()
                        .spawnMonsterOnGroundBelow(
                            MapleLifeFactory.getMonster(
                                Integer.parseInt(splitted[1])
                            ),
                            c.getPlayer().getPosition()
                        );
                }
            } else {
                c
                    .getPlayer()
                    .getMap()
                    .spawnMonsterOnGroundBelow(
                        MapleLifeFactory.getMonster(
                            Integer.parseInt(splitted[1])
                        ),
                        c.getPlayer().getPosition()
                    );
            }
            return true;
        }
    }

    /** !level <level> - set the current level */
    public static class Level extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c.getPlayer().setLevel(Short.parseShort(splitted[1]));
            c.getPlayer().levelUp(false);
            c.getPlayer().setExp(0);
            c.getPlayer().getStat().updateSingleStat(PlayerStat.EXP, 0);
            return true;
        }
    }

    /** !event [optional <mapid>] - begin an event on a channel */
    public static class Event extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (!c.getChannelServer().getEventStarted()) {
                int mapId = getOptionalIntArg(
                    splitted,
                    1,
                    c.getPlayer().getMapId()
                );
                c.getChannelServer().setEvent(true);
                c.getChannelServer().setEventMap(mapId);
                BroadcastService.broadcastMessage(
                    PacketCreator.ServerNotice(
                        6,
                        "[Event] Event started on channel (" +
                        c.getChannel() +
                        "). Please use @joinevent to participate in the event."
                    )
                );
            } else {
                c.getChannelServer().setEvent(false);
                BroadcastService.broadcastMessage(
                    PacketCreator.ServerNotice(
                        6,
                        "[Event] Entrance to the event has been closed."
                    )
                );
            }
            return true;
        }
    }

    /** !zakum - spawn zakum */
    public static class Zakum extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c
                .getPlayer()
                .getMap()
                .spawnFakeMonsterOnGroundBelow(
                    MapleLifeFactory.getMonster(8800000),
                    c.getPlayer().getPosition()
                );
            for (int x = 8800003; x < 8800011; x++) {
                c
                    .getPlayer()
                    .getMap()
                    .spawnMonsterOnGroudBelow(
                        MapleLifeFactory.getMonster(x),
                        c.getPlayer().getPosition()
                    );
            }
            return true;
        }
    }

    /** !horntail - spawn horntail (TODO) */
    public static class Horntail extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            final Point targetPoint = c.getPlayer().getPosition();
            final Field targetMap = c.getPlayer().getMap();

            // Todo : horntail
            //targetMap.spawnHorntailOnGroundBelow(targetPoint);
            return true;
        }
    }

    /** !cake [optional <mobhp>] - spawn cake monster */
    public static class Cake extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            MapleMonster monster = MapleLifeFactory.getMonster(9400606);
            if (splitted.length > 1) {
                double mobHp = Double.parseDouble(splitted[1]);
                int newHp = (mobHp <= 0)
                    ? Integer.MAX_VALUE
                    : (
                        (mobHp > Integer.MAX_VALUE)
                            ? Integer.MAX_VALUE
                            : (int) mobHp
                    );

                monster.getStats().setHp(newHp);
                monster.setHp(newHp);
            }

            c
                .getPlayer()
                .getMap()
                .spawnMonsterOnGroundBelow(
                    monster,
                    c.getPlayer().getPosition()
                );
            return true;
        }
    }

    /** !papu - spawn papulatus */
    public static class Papu extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c
                .getPlayer()
                .getMap()
                .spawnMonsterOnGroundBelow(
                    MapleLifeFactory.getMonster(8500001),
                    c.getPlayer().getPosition()
                );
            return true;
        }
    }

    /** !map <mapid> - warp player to map */
    public static class Map extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            int mapid;
            try {
                mapid = Integer.parseInt(splitted[1]);
            } catch (NumberFormatException mwa) {
                return false;
            }
            Field warp = c.getPlayer().getWarpMap(mapid);
            if (warp == null) {
                c
                    .getPlayer()
                    .yellowMessage(
                        "Map ID or name " +
                        getOptionalIntArg(splitted, 2, 0) +
                        " is invalid."
                    );
                return false;
            } else {
                c.getPlayer().changeMap(warp);
            }
            return true;
        }
    }

    /** !bomb [optional <count>] - spawn a bomb */
    public static class Bomb extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length > 1) {
                for (int i = 0; i < Integer.parseInt(splitted[1]); i++) {
                    c.getPlayer().spawnBomb();
                }
                c.getPlayer().dropMessage("Planted " + splitted[1] + " bombs.");
                return true;
            } else {
                c.getPlayer().spawnBomb();
                c.getPlayer().dropMessage("Planted a bomb.");
                return true;
            }
        }
    }

    /** !killall - kill all monsters on the map */
    public static class KillAll extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            Field map = c.getPlayer().getMap();
            List<FieldObject> monsters = map.getMapObjectsInRange(
                c.getPlayer().getPosition(),
                Double.POSITIVE_INFINITY,
                Arrays.asList(FieldObjectType.MONSTER)
            );
            int count = 0;
            for (FieldObject monstermo : monsters) {
                MapleMonster monster = (MapleMonster) monstermo;
                if (
                    !monster.getStats().isFriendly() &&
                    !(monster.getId() >= 8810010 && monster.getId() <= 8810018)
                ) {
                    map.damageMonster(
                        c.getPlayer(),
                        monster,
                        Integer.MAX_VALUE
                    );
                    count++;
                }
            }
            c.getPlayer().dropMessage(5, "Killed " + count + " monsters.");
            return true;
        }
    }

    /** !levelup - increase the current level by one */
    public static class Levelup extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c.getPlayer().levelUp(true);
            c.getPlayer().getStat().updateSingleStat(PlayerStat.EXP, 0);
            return true;
        }
    }

    /** !clock [optional <seconds>] - set a timer at the top of the map */
    public static class Clock extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c
                .getPlayer()
                .getMap()
                .displayClock(
                    c.getPlayer(),
                    getOptionalIntArg(splitted, 1, 60)
                );
            return true;
        }
    }

    /** !buffme - apply all the buffs */
    public static class Buffme extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            final int[] array = {
                9001000,
                9101002,
                9101003,
                9101008,
                2001002,
                1101007,
                1005,
                2301003,
                5121009,
                1111002,
                4111001,
                4111002,
                4211003,
                4211005,
                1321000,
                2321004,
                3121002,
            };
            for (int i : array) {
                PlayerSkillFactory
                    .getSkill(i)
                    .getEffect(PlayerSkillFactory.getSkill(i).getMaxLevel())
                    .applyTo(c.getPlayer());
            }
            return true;
        }
    }

    /** !killmap - kill all the players on the map. */
    public static class Killmap extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            int players = 0;
            for (Player mch : c.getPlayer().getMap().getCharacters()) {
                if (mch != null && !mch.isGameMaster()) {
                    mch.kill();
                    players++;
                }
            }
            c
                .getPlayer()
                .dropMessage("A total " + players + " players are at zero HP.");
            return true;
        }
    }

    /** !mutemap - mute the current map */
    public static class Mutemap extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (
                c
                    .getPlayer()
                    .getMap()
                    .getProperties()
                    .getProperty("mute")
                    .equals(Boolean.TRUE)
            ) {
                c.getPlayer().dropMessage("The map is already set to mute.");
                return false;
            }
            c
                .getPlayer()
                .getMap()
                .getProperties()
                .setProperty("mute", Boolean.TRUE);
            c
                .getPlayer()
                .dropMessage(
                    "The map [" +
                    c.getPlayer().getMapName(c.getPlayer().getMapId()) +
                    "] is muted."
                );
            return true;
        }
    }

    /** !unmute - unmute the current map */
    public static class Unmutemap extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (
                c
                    .getPlayer()
                    .getMap()
                    .getProperties()
                    .getProperty("mute")
                    .equals(Boolean.FALSE)
            ) {
                c.getPlayer().dropMessage("The 'no' map is set to mute.");
                return false;
            }
            c
                .getPlayer()
                .getMap()
                .getProperties()
                .setProperty("mute", Boolean.FALSE);
            c
                .getPlayer()
                .dropMessage(
                    "The map [" +
                    c.getPlayer().getMapName(c.getPlayer().getMapId()) +
                    "] is not muted."
                );
            return true;
        }
    }

    /** !unbuffmap - remove buffs from all players in the current map */
    public static class Unbuffmap extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            for (Player map : c.getPlayer().getMap().getCharacters()) {
                if (map != null && map != c.getPlayer()) {
                    map.cancelAllBuffs(false);
                }
            }
            return true;
        }
    }

    /** !setall <value> - set all stats to the value */
    public static class Setall extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length < 1) {
                c.getPlayer().dropMessage(6, "!setall <quantity stat>");
                return false;
            }
            final int x = Short.parseShort(splitted[1]);
            c.getPlayer().getStat().setStr(x);
            c.getPlayer().getStat().setDex(x);
            c.getPlayer().getStat().setInt(x);
            c.getPlayer().getStat().setLuk(x);
            c.getPlayer().getStat().updateSingleStat(PlayerStat.STR, x);
            c.getPlayer().getStat().updateSingleStat(PlayerStat.DEX, x);
            c.getPlayer().getStat().updateSingleStat(PlayerStat.INT, x);
            c.getPlayer().getStat().updateSingleStat(PlayerStat.LUK, x);
            return true;
        }
    }

    /** !whereami - show the id of the current map */
    public static class Whereami extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c
                .getPlayer()
                .dropMessage(
                    "You are on map <" + c.getPlayer().getMap().getId() + ">."
                );
            return true;
        }
    }

    /** !job <jobid> - set the job for the current character */
    public static class Job extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            int jobId = Integer.parseInt(splitted[1]);
            if (PlayerJob.getById(jobId) != null) {
                c.getPlayer().changeJob(PlayerJob.getById(jobId));
            } else {
                c.getPlayer().dropMessage("Job not found!");
                return false;
            }
            return true;
        }
    }

    /** !fullhp - increase hp and mp to max values */
    public static class Fullhp extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c.getPlayer().getStat().setHp(c.getPlayer().getStat().getMaxHp());
            c.getPlayer().getStat().setMp(c.getPlayer().getStat().getMaxMp());
            c
                .getPlayer()
                .getStat()
                .updateSingleStat(
                    PlayerStat.HP,
                    c.getPlayer().getStat().getMaxHp()
                );
            c
                .getPlayer()
                .getStat()
                .updateSingleStat(
                    PlayerStat.MP,
                    c.getPlayer().getStat().getMaxMp()
                );
            return true;
        }
    }

    /** !gainmeso <value> - add mesos to your inventory */
    public static class GainMeso extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length >= 2) {
                c.getPlayer().gainMeso(Integer.parseInt(splitted[1]), true);
                return true;
            } else {
                c
                    .getPlayer()
                    .gainMeso(
                        Integer.MAX_VALUE - c.getPlayer().getMeso(),
                        true
                    );
                return true;
            }
        }
    }

    /** !healhere - heal all characters on the map */
    public static class HealHere extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            Player p = c.getPlayer();
            for (Player mch : p.getMap().getCharacters()) {
                if (mch != null) {
                    c
                        .getPlayer()
                        .getStat()
                        .setHp(c.getPlayer().getStat().getMaxHp());
                    c
                        .getPlayer()
                        .getStat()
                        .updateSingleStat(
                            PlayerStat.HP,
                            c.getPlayer().getStat().getMaxHp()
                        );
                    c
                        .getPlayer()
                        .getStat()
                        .setMp(c.getPlayer().getStat().getMaxMp());
                    c
                        .getPlayer()
                        .getStat()
                        .updateSingleStat(
                            PlayerStat.MP,
                            c.getPlayer().getStat().getMaxMp()
                        );
                }
            }
            return true;
        }
    }

    /** !dc [-f] <player> - disconnect a player */
    public static class DC extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            int level = 0;
            Player victim;
            if (splitted[1].charAt(0) == '-') {
                level = StringUtil.countCharacters(splitted[1], 'f');
                victim =
                    c
                        .getChannelServer()
                        .getPlayerStorage()
                        .getCharacterByName(splitted[2]);
            } else {
                victim =
                    c
                        .getChannelServer()
                        .getPlayerStorage()
                        .getCharacterByName(splitted[1]);
            }
            if (level < 2 && victim != null) {
                victim.getClient().getSession().close();
                if (level >= 1) {
                    victim.getClient().disconnect(true, false);
                }
                return true;
            } else {
                c
                    .getPlayer()
                    .dropMessage(
                        6,
                        "Please use dc -f instead, or the victim does not exist."
                    );
                return false;
            }
        }
    }

    /** !online - show the players online */
    public static class Online extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c.getPlayer().yellowMessage("=========================");
            c
                .getPlayer()
                .yellowMessage(
                    "Total Online " +
                    ServerProperties.Login.SERVER_NAME +
                    ": " +
                    World.getConnected()
                );
            c.getPlayer().yellowMessage("=========================");
            c.getPlayer().dropMessage(6, "<Channel (" + c.getChannel() + ")>");
            c
                .getPlayer()
                .dropMessage(
                    6,
                    c
                        .getChannelServer()
                        .getPlayerStorage()
                        .getOnlinePlayers(true)
                );
            return true;
        }
    }

    /** !giftnx <player> <amount> - gift a player nx */
    public static class GiftNX extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            ChannelServer cserv = c.getChannelServer();
            Player receiver = cserv
                .getPlayerStorage()
                .getCharacterByName(splitted[1]);
            if (receiver == null) {
                c.getPlayer().dropMessage("Receiver not found!");
                return false;
            }
            cserv
                .getPlayerStorage()
                .getCharacterByName(splitted[1])
                .getCashShop()
                .gainCash(1, Integer.parseInt(splitted[2]));
            c
                .getPlayer()
                .dropMessage(
                    6,
                    "Done, it was sent to " +
                    splitted[1] +
                    " the amount of " +
                    splitted[2] +
                    "."
                );
            return true;
        }
    }

    /** !say <message> - broadcast a message to the server */
    public static class Say extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append(c.getPlayer().getName());
                sb.append("] ");
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                BroadcastService.broadcastMessage(
                    PacketCreator.ServerNotice(6, sb.toString())
                );
            } else {
                c.getPlayer().dropMessage(6, "Syntax: !say <message>");
                return false;
            }
            return true;
        }
    }

    /** !warn <message> - send a warning message to all players */
    public static class Warn extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                BroadcastService.broadcastMessage(
                    PacketCreator.ServerNotice(1, sb.toString())
                );
            } else {
                c.getPlayer().dropMessage(6, "Syntax: !warn <message>");
                return false;
            }
            return true;
        }
    }

    /** !openportal <portalid> - open a portal */
    public static class OpenPortalId extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().yellowMessage("Syntax: !openportal <portalid>");
                return false;
            }
            BroadcastService.broadcastMessage(
                PacketCreator.ServerNotice(
                    5,
                    "The portal has now opened. Try pressing the up arrow key on portal!"
                )
            );
            c.getPlayer().getMap().getPortal(splitted[1]).setPortalState(true);
            return true;
        }
    }

    /** !closeportal <portalid> - close a portal */
    public static class ClosePortalId extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().yellowMessage("Syntax: !closeportal <portalid>");
                return false;
            }
            BroadcastService.broadcastMessage(
                PacketCreator.ServerNotice(5, "The portal has been closed!")
            );
            c.getPlayer().getMap().getPortal(splitted[1]).setPortalState(false);
            return true;
        }
    }

    /** !closeportals - close all portals in the current map */
    public static class ClosePortals extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            for (Portal portal : c.getPlayer().getMap().getPortals()) {
                portal.setPortalState(false);
            }
            BroadcastService.broadcastMessage(
                PacketCreator.ServerNotice(5, "The portal has been closed!")
            );
            return true;
        }
    }

    /** !openportals - open all portals in the current map */
    public static class OpenPortals extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            for (Portal portal : c.getPlayer().getMap().getPortals()) {
                portal.setPortalState(true);
            }
            BroadcastService.broadcastMessage(
                PacketCreator.ServerNotice(
                    5,
                    "The portal has now opened. Try pressing the up arrow key on portal!"
                )
            );
            return true;
        }
    }

    /** !itemcheck <player> <itemid> - check how many items a player has */
    public static class ItemCheck extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (
                splitted.length < 3 ||
                splitted[1] == null ||
                splitted[1].equals("") ||
                splitted[2] == null ||
                splitted[2].equals("")
            ) {
                c
                    .getPlayer()
                    .dropMessage(6, "!itemcheck <playername> <itemid>");
                return false;
            } else {
                int item = Integer.parseInt(splitted[2]);
                Player chr = c
                    .getChannelServer()
                    .getPlayerStorage()
                    .getCharacterByName(splitted[1]);
                int itemamount = chr.getItemQuantity(item, true);
                if (itemamount > 0) {
                    c
                        .getPlayer()
                        .dropMessage(
                            6,
                            chr.getName() +
                            " has " +
                            itemamount +
                            " (" +
                            item +
                            ")."
                        );
                } else {
                    c
                        .getPlayer()
                        .dropMessage(
                            6,
                            chr.getName() + " doesn't have (" + item + ")"
                        );
                }
            }
            return true;
        }
    }

    /** !song <songid> - change the current bgm of the map */
    public static class Song extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c
                .getPlayer()
                .getMap()
                .broadcastMessage(EffectPackets.MusicChange(splitted[1]));
            return true;
        }
    }

    /** !removeitem <player> <itemid> - remove items from the inventory of a player */
    public static class RemoveItem extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(6, "Need <name> <itemid>");
                return false;
            }
            Player chr = c
                .getChannelServer()
                .getPlayerStorage()
                .getCharacterByName(splitted[1]);
            if (chr == null) {
                c.getPlayer().dropMessage(6, "This player does not exist");
                return false;
            }
            chr.removeAll(Integer.parseInt(splitted[2]));
            c
                .getPlayer()
                .dropMessage(
                    6,
                    "All items with the ID " +
                    splitted[2] +
                    " has been removed from the inventory of " +
                    splitted[1] +
                    "."
                );
            return true;
        }
    }

    /** !speakmega <player> <message> - send a message as another player on megaphone */
    public static class SpeakMega extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            Player victim = c
                .getChannelServer()
                .getPlayerStorage()
                .getCharacterByName(splitted[1]);
            BroadcastService.broadcastSmega(
                PacketCreator.ServerNotice(
                    3,
                    victim == null
                        ? c.getChannel()
                        : victim.getClient().getChannel(),
                    victim == null
                        ? splitted[1]
                        : victim.getName() +
                        " : " +
                        StringUtil.joinStringFrom(splitted, 2),
                    true
                )
            );
            return true;
        }
    }

    /** !speak <player> <message> - say a message as a player */
    public static class Speak extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            Player victim = c
                .getChannelServer()
                .getPlayerStorage()
                .getCharacterByName(splitted[1]);
            if (victim == null) {
                c.getPlayer().dropMessage(5, "unable to find '" + splitted[1]);
                return false;
            } else {
                victim
                    .getMap()
                    .broadcastMessage(
                        PacketCreator.GetChatText(
                            victim.getId(),
                            StringUtil.joinStringFrom(splitted, 2),
                            victim.isGameMaster(),
                            (byte) 0
                        )
                    );
            }
            return true;
        }
    }

    /** !speakmap <message> - make all players speak a message */
    public static class SpeakMap extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            for (Player victim : c
                .getPlayer()
                .getMap()
                .getCharactersThreadsafe()) {
                if (victim.getId() != c.getPlayer().getId()) {
                    victim
                        .getMap()
                        .broadcastMessage(
                            PacketCreator.GetChatText(
                                victim.getId(),
                                StringUtil.joinStringFrom(splitted, 1),
                                victim.isGameMaster(),
                                (byte) 0
                            )
                        );
                }
            }
            return true;
        }
    }

    /** !mesoeveryone <amount> - Give everyone online mesos */
    public static class MesoEveryone extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (Player mch : cserv.getPlayerStorage().getAllCharacters()) {
                    mch.gainMeso(Integer.parseInt(splitted[1]), true);
                }
            }
            return true;
        }
    }

    /** !charinfo <player> - show information about a player */
    public static class CharInfo extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            final StringBuilder builder = new StringBuilder();
            final Player other = c
                .getChannelServer()
                .getPlayerStorage()
                .getCharacterByName(splitted[1]);
            if (other == null) {
                builder.append("...does not exist");
                c.getPlayer().dropMessage(6, builder.toString());
                return false;
            }
            builder.append(Client.getLogMessage(other, ""));
            builder.append(" at ").append(other.getPosition().x);
            builder.append(" /").append(other.getPosition().y);

            builder.append(" || HP : ");
            builder.append(other.getStat().getHp());
            builder.append(" /");
            builder.append(other.getStat().getCurrentMaxHp());

            builder.append(" || MP : ");
            builder.append(other.getStat().getMp());
            builder.append(" /");
            builder.append(other.getStat().getCurrentMaxMp());

            builder.append(" || WATK : ");
            builder.append(other.getStat().getTotalWatk());
            builder.append(" || MATK : ");
            builder.append(other.getStat().getTotalMagic());
            builder.append(" || MAXDAMAGE : ");
            builder.append(other.getStat().getCurrentMaxBaseDamage());

            builder.append(" || STR : ");
            builder.append(other.getStat().getStr());
            builder.append(" || DEX : ");
            builder.append(other.getStat().getDex());
            builder.append(" || INT : ");
            builder.append(other.getStat().getInt());
            builder.append(" || LUK : ");
            builder.append(other.getStat().getLuk());

            builder.append(" || Total STR : ");
            builder.append(other.getStat().getTotalStr());
            builder.append(" || Total DEX : ");
            builder.append(other.getStat().getTotalDex());
            builder.append(" || Total INT : ");
            builder.append(other.getStat().getTotalInt());
            builder.append(" || Total LUK : ");
            builder.append(other.getStat().getTotalLuk());

            builder.append(" || EXP : ");
            builder.append(other.getCurrentExp());

            builder.append(" || hasParty : ");
            builder.append(other.getParty() != null);

            builder.append(" || hasTrade: ");
            builder.append(other.getTrade() != null);

            builder.append(" || remoteAddress: ");

            other.getClient().DebugMessage(builder);

            c.getPlayer().dropMessage(6, builder.toString());
            return true;
        }
    }

    /** !cheaters - show information about cheaters */
    public static class Cheaters extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            List<CheaterData> cheaters = World.getCheaters();
            if (cheaters.isEmpty()) {
                c
                    .getPlayer()
                    .dropMessage(6, "There are no cheaters at the moment.");
                return false;
            }
            for (int x = cheaters.size() - 1; x >= 0; x--) {
                CheaterData cheater = cheaters.get(x);
                c.getPlayer().dropMessage(6, cheater.getInfo());
            }
            return true;
        }
    }

    /** !maxskills - max the skills of the current player */
    public static class Maxskills extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c.getPlayer().maxAllSkills();
            return true;
        }
    }

    /** !cleardrops - remove drops from the map */
    public static class Cleardrops extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            Field field = c.getPlayer().getMap();
            double range = Double.POSITIVE_INFINITY;
            List<FieldObject> items = field.getMapObjectsInRange(
                c.getPlayer().getPosition(),
                range,
                Arrays.asList(FieldObjectType.ITEM)
            );
            for (FieldObject itemmo : items) {
                field.removeMapObject(itemmo);
                field.broadcastMessage(
                    PacketCreator.RemoveItemFromMap(
                        itemmo.getObjectId(),
                        4,
                        c.getPlayer().getId()
                    )
                );
            }
            c
                .getPlayer()
                .dropMessage(
                    "You have destroyed " +
                    items.size() +
                    " items on the ground."
                );
            return true;
        }
    }

    /** !exprate <value> [all] - change the experience rate for channel (or world) */
    public static class ExpRate extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (
                    splitted.length > 2 && splitted[2].equalsIgnoreCase("all")
                ) {
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setExpRate(rate);
                    }
                } else {
                    c.getChannelServer().setExpRate(rate);
                }
                c
                    .getPlayer()
                    .dropMessage(
                        6,
                        "Exprate has been changed to " + rate + "x"
                    );
            } else {
                c.getPlayer().dropMessage(6, "Syntax: !exprate <number> [all]");
                return false;
            }
            return true;
        }
    }

    /** !mesorate <value> [all] - change the meso rate for the channel (or world) */
    public static class MesoRate extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (
                    splitted.length > 2 && splitted[2].equalsIgnoreCase("all")
                ) {
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setMesoRate(rate);
                    }
                } else {
                    c.getChannelServer().setMesoRate(rate);
                }
                c
                    .getPlayer()
                    .dropMessage(
                        6,
                        "Exprate has been changed to " + rate + "x"
                    );
            } else {
                c.getPlayer().dropMessage(6, "Syntax: !exprate <number> [all]");
                return false;
            }
            return true;
        }
    }

    /** !droprate <value> [all] - change the drop rate for the channel (or world) */
    public static class DropRate extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (
                    splitted.length > 2 && splitted[2].equalsIgnoreCase("all")
                ) {
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setDropRate(rate);
                    }
                } else {
                    c.getChannelServer().setDropRate(rate);
                }
                c
                    .getPlayer()
                    .dropMessage(
                        6,
                        "Drop Rate has been changed to " + rate + "x"
                    );
            } else {
                c
                    .getPlayer()
                    .dropMessage(6, "Syntax: !droprate <number> [all]");
                return false;
            }
            return true;
        }
    }

    /** !questrate <value> [all] - change the quest rate for the channel (or world) */
    public static class QuestRate extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (
                    splitted.length > 2 && splitted[2].equalsIgnoreCase("all")
                ) {
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setQuestRate(rate);
                    }
                } else {
                    c.getChannelServer().setQuestRate(rate);
                }
                c
                    .getPlayer()
                    .dropMessage(
                        6,
                        "Quest Rate has been changed to " + rate + "x"
                    );
            } else {
                c
                    .getPlayer()
                    .dropMessage(6, "Syntax: !questrate <number> [all]");
                return false;
            }
            return true;
        }
    }

    /** !fame <player> <value> - set the fame of a character */
    public static class Fame extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            ChannelServer cserv = c.getChannelServer();
            Player victim = cserv
                .getPlayerStorage()
                .getCharacterByName(splitted[1]);
            if (victim != null) {
                victim.setFame(getOptionalIntArg(splitted, 2, 1));
                victim
                    .getStat()
                    .updateSingleStat(PlayerStat.FAME, victim.getFame());
                return true;
            } else {
                c.getPlayer().dropMessage("Player not found!");
                return false;
            }
        }
    }

    /** !eventrules - show the event rules in the map */
    public static class EventRules extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            for (Player p : c.getPlayer().getMap().getAllPlayers()) {
                p.announce(PacketCreator.showEventInstructions());
            }
            return true;
        }
    }

    /** !setname <old> <new> - sets the name of a character */
    public static class SetName extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length != 3) {
                return false;
            }
            ChannelServer cserv = c.getChannelServer();
            Player victim = cserv
                .getPlayerStorage()
                .getCharacterByName(splitted[1]);
            String newname = splitted[2];
            if (splitted.length == 3) {
                if (PlayerQuery.getIdByName(newname, 0) == -1) {
                    if (victim != null) {
                        victim.getClient().disconnect(false, false);
                        victim.getClient().getSession().close();
                        // TODO: only change the name if the name is valid
                        victim.setName(newname, true);
                        c
                            .getPlayer()
                            .dropMessage(
                                splitted[1] + " is now named " + newname + ""
                            );
                        return true;
                    } else {
                        c
                            .getPlayer()
                            .dropMessage(
                                "The player " +
                                splitted[1] +
                                " is either offline or not in this channel"
                            );
                        return false;
                    }
                } else {
                    c.getPlayer().dropMessage("Character name in use.");
                    return false;
                }
            } else {
                c.getPlayer().dropMessage("Incorrect syntax !");
                return false;
            }
        }
    }

    /** !uptime - get how long the server has been online */
    public static class Uptime extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            c
                .getPlayer()
                .dropMessage(
                    6,
                    "Server has been up for " +
                    StringUtil.getReadableMillis(
                        Start.startTime,
                        System.currentTimeMillis()
                    )
                );
            return true;
        }
    }

    /** !item <itemid> <quantity> - add an item to the inventory */
    public static class item extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            ItemInformationProvider ii = ItemInformationProvider.getInstance();
            final int itemId = Integer.parseInt(splitted[1]);
            final short quantity = (short) getOptionalIntArg(splitted, 2, 1);

            if (ItemConstants.isPet(itemId)) {
                c
                    .getPlayer()
                    .dropMessage(
                        5,
                        "Please purchase a pet from the cash shop instead."
                    );
                return false;
            } else if (!ii.itemExists(itemId)) {
                c.getPlayer().dropMessage(5, itemId + " does not exist.");
                return false;
            } else {
                Item item;
                if (
                    ItemConstants.getInventoryType(itemId) ==
                    InventoryType.EQUIP
                ) {
                    item = ii.randomizeStats((Equip) ii.getEquipById(itemId));
                } else {
                    item = new Item(itemId, (byte) 0, quantity);
                }
                item.setOwner(c.getPlayer().getName());
                InventoryManipulator.addbyItem(c, item);
                return true;
            }
        }
    }

    /** !goto <location> - transport to a town center */
    public static class GoTo extends CommandExecute {

        private static final HashMap<String, Integer> gotoMaps = new HashMap<>();

        static {
            gotoMaps.put("gmmap", 180000000);
            gotoMaps.put("southperry", 2000000);
            gotoMaps.put("amherst", 1010000);
            gotoMaps.put("henesys", 100000000);
            gotoMaps.put("ellinia", 101000000);
            gotoMaps.put("perion", 102000000);
            gotoMaps.put("kerning", 103000000);
            gotoMaps.put("lithharbour", 104000000);
            gotoMaps.put("sleepywood", 105040300);
            gotoMaps.put("florina", 110000000);
            gotoMaps.put("orbis", 200000000);
            gotoMaps.put("happyville", 209000000);
            gotoMaps.put("elnath", 211000000);
            gotoMaps.put("ludibrium", 220000000);
            gotoMaps.put("aquaroad", 230000000);
            gotoMaps.put("leafre", 240000000);
            gotoMaps.put("mulung", 250000000);
            gotoMaps.put("herbtown", 251000000);
            gotoMaps.put("omegasector", 221000000);
            gotoMaps.put("koreanfolktown", 222000000);
            gotoMaps.put("newleafcity", 600000000);
            gotoMaps.put("sharenian", 990000000);
            gotoMaps.put("pianus", 230040420);
            gotoMaps.put("horntail", 240060200);
            gotoMaps.put("chorntail", 240060201);
            gotoMaps.put("mushmom", 100000005);
            gotoMaps.put("griffey", 240020101);
            gotoMaps.put("manon", 240020401);
            gotoMaps.put("zakum", 280030000);
            gotoMaps.put("czakum", 280030001);
            gotoMaps.put("papulatus", 220080001);
            gotoMaps.put("showatown", 801000000);
            gotoMaps.put("zipangu", 800000000);
            gotoMaps.put("ariant", 260000100);
            gotoMaps.put("nautilus", 120000000);
            gotoMaps.put("boatquay", 541000000);
            gotoMaps.put("malaysia", 550000000);
            gotoMaps.put("taiwan", 740000000);
            gotoMaps.put("thailand", 500000000);
            gotoMaps.put("erev", 130000000);
            gotoMaps.put("ellinforest", 300000000);
            gotoMaps.put("kampung", 551000000);
            gotoMaps.put("singapore", 540000000);
            gotoMaps.put("amoria", 680000000);
            gotoMaps.put("timetemple", 270000000);
            gotoMaps.put("pinkbean", 270050100);
            gotoMaps.put("peachblossom", 700000000);
            gotoMaps.put("fm", 910000000);
            gotoMaps.put("freemarket", 910000000);
            gotoMaps.put("oxquiz", 109020001);
            gotoMaps.put("ola", 109030101);
            gotoMaps.put("fitness", 109040000);
            gotoMaps.put("snowball", 109060000);
            gotoMaps.put("cashmap", 741010200);
            gotoMaps.put("golden", 950100000);
            gotoMaps.put("phantom", 610010000);
            gotoMaps.put("cwk", 610030000);
            gotoMaps.put("rien", 140000000);
        }

        @Override
        public boolean execute(Client c, String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "Syntax: !goto <mapname>");
            } else {
                if (gotoMaps.containsKey(splitted[1])) {
                    Field target = c
                        .getChannelServer()
                        .getMapFactory()
                        .getMap(gotoMaps.get(splitted[1]));
                    Portal targetPortal = target.getPortal(0);
                    c.getPlayer().changeMap(target, targetPortal);
                } else {
                    if (splitted[1].equals("locations")) {
                        c
                            .getPlayer()
                            .dropMessage(
                                6,
                                "Use !goto <location>. Locations are as follows:"
                            );
                        StringBuilder sb = new StringBuilder();
                        for (String s : gotoMaps.keySet()) {
                            sb.append(s).append(", ");
                        }
                        c
                            .getPlayer()
                            .dropMessage(6, sb.substring(0, sb.length() - 2));
                    } else {
                        c
                            .getPlayer()
                            .dropMessage(
                                6,
                                "Invalid command syntax - Use !goto <location>. For a list of locations, use !goto locations."
                            );
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /** !help - show help for gm commands */
    public static class Help extends CommandExecute {

        @Override
        public boolean execute(Client c, String[] splitted) {
            final Player p = c.getPlayer();
            p.dropMessage(5, "!hide - hides the current character");
            p.dropMessage(5, "!drop <itemid> <quantity>");
            p.dropMessage(5, "!spawn <mobid>");
            p.dropMessage(5, "!level <level> - set the current level");
            p.dropMessage(
                5,
                "!event [optional <mapid>] - begin an event on a channel"
            );
            p.dropMessage(5, "!zakum - spawn zakum");
            p.dropMessage(5, "!horntail - spawn horntail (TODO)");
            p.dropMessage(5, "!cake [optional <mobhp>] - spawn cake monster");
            p.dropMessage(5, "!papu - spawn papulatus");
            p.dropMessage(5, "!map <mapid> - warp player to map");
            p.dropMessage(5, "!bomb [optional <count>] - spawn a bomb");
            p.dropMessage(5, "!killall - kill all monsters on the map");
            p.dropMessage(5, "!levelup - increase the current level by one");
            p.dropMessage(
                5,
                "!clock [optional <seconds>] - set a timer at the top of the map"
            );
            p.dropMessage(5, "!buffme - apply all the buffs");
            p.dropMessage(5, "!killmap - kill all the players on the map.");
            p.dropMessage(5, "!mutemap - mute the current map");
            p.dropMessage(5, "!unmute - unmute the current map");
            p.dropMessage(
                5,
                "!unbuffmap - remove buffs from all players in the current map"
            );
            p.dropMessage(5, "!setall <value> - set all stats to the value");
            p.dropMessage(5, "!whereami - show the id of the current map");
            p.dropMessage(
                5,
                "!job <jobid> - set the job for the current character"
            );
            p.dropMessage(5, "!fullhp - increase hp and mp to max values");
            p.dropMessage(5, "!gainmeso <value> - add mesos to your inventory");
            p.dropMessage(5, "!healhere - heal all characters on the map");
            p.dropMessage(5, "!dc [-f] <player> - disconnect a player");
            p.dropMessage(5, "!online - show the players online");
            p.dropMessage(5, "!giftnx <player> <amount> - gift a player nx");
            p.dropMessage(
                5,
                "!say <message> - broadcast a message to the server"
            );
            p.dropMessage(
                5,
                "!warn <message> - send a warning message to all players"
            );
            p.dropMessage(5, "!openportal <portalid> - open a portal");
            p.dropMessage(5, "!closeportal <portalid> - close a portal");
            p.dropMessage(
                5,
                "!closeportals - close all portals in the current map"
            );
            p.dropMessage(
                5,
                "!openportals - open all portals in the current map"
            );
            p.dropMessage(
                5,
                "!itemcheck <player> <itemid> - check how many items a player has"
            );
            p.dropMessage(
                5,
                "!song <songid> - change the current bgm of the map"
            );
            p.dropMessage(
                5,
                "!removeitem <player> <itemid> - remove items from the inventory of a player"
            );
            p.dropMessage(
                5,
                "!speakmega <player> <message> - send a message as another player on megaphone"
            );
            p.dropMessage(
                5,
                "!speak <player> <message> - say a message as a player"
            );
            p.dropMessage(
                5,
                "!speakmap <message> - make all players speak a message"
            );
            p.dropMessage(
                5,
                "!mesoeveryone <amount> - Give everyone online mesos"
            );
            p.dropMessage(
                5,
                "!charinfo <player> - show information about a player"
            );
            p.dropMessage(5, "!cheaters - show information about cheaters");
            p.dropMessage(
                5,
                "!maxskills - max the skills of the current player"
            );
            p.dropMessage(5, "!cleardrops - remove drops from the map");
            p.dropMessage(
                5,
                "!exprate <value> [all] - change the experience rate for channel (or world)"
            );
            p.dropMessage(
                5,
                "!mesorate <value> [all] - change the meso rate for the channel (or world)"
            );
            p.dropMessage(
                5,
                "!droprate <value> [all] - change the drop rate for the channel (or world)"
            );
            p.dropMessage(
                5,
                "!questrate <value> [all] - change the quest rate for the channel (or world)"
            );
            p.dropMessage(
                5,
                "!fame <player> <value> - set the fame of a character"
            );
            p.dropMessage(5, "!eventrules - show the event rules in the map");
            p.dropMessage(
                5,
                "!setname <old> <new> - sets the name of a character"
            );
            p.dropMessage(
                5,
                "!uptime - get how long the server has been online"
            );
            p.dropMessage(
                5,
                "!item <itemid> <quantity> - add an item to the inventory"
            );
            p.dropMessage(5, "!goto <location> - transport to a town center");
            p.dropMessage(5, "!help - show help for gm commands");
            return true;
        }
    }
}
