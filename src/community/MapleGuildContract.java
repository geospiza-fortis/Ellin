/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package community;

import client.Client;
import client.player.Player;
import constants.GameConstants;
import constants.MapConstants;
import handling.channel.ChannelServer;
import handling.channel.handler.GuildHandler;
import handling.world.service.GuildService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;
import packet.creators.GuildPackets;
import packet.creators.PacketCreator;
import tools.TimerTools.MapTimer;

/**
 *
 * @author GabrielSin
 */
public class MapleGuildContract {

    private static final Map<Integer, String> pendingParty = new HashMap<>();
    private static final Map<Integer, Integer> agreeCount = new HashMap<>();
    private static final Map<Integer, Integer> declineCount = new HashMap<>();
    private static final Map<Integer, Integer> answerCount = new HashMap<>();
    private static ScheduledFuture<?> sendPacketTime = null;
    private static final ReentrantLock lock = new ReentrantLock();

    public static int getAgreeCount(MapleParty party) {
        if (party == null) return 0;
        lock.lock();
        try {
            return agreeCount.get(party.getId());
        } finally {
            lock.unlock();
        }
    }

    public static int getDeclineCount(MapleParty party) {
        if (party == null) return 0;
        lock.lock();
        try {
            return declineCount.get(party.getId());
        } finally {
            lock.unlock();
        }
    }

    public static int getAnswerCount(MapleParty party) {
        if (party == null) return 0;
        lock.lock();
        try {
            return answerCount.get(party.getId());
        } finally {
            lock.unlock();
        }
    }

    public static String getGuildName(MapleParty party) {
        if (party == null) return null;
        lock.lock();
        try {
            return pendingParty.get(party.getId());
        } finally {
            lock.unlock();
        }
    }

    public static void gainAgreeCount(MapleParty party) {
        if (party == null) return;
        lock.lock();
        try {
            agreeCount.put(party.getId(), agreeCount.get(party.getId()) + 1);
        } finally {
            lock.unlock();
        }
    }

    public static void gainDeclineCount(MapleParty party) {
        if (party == null) return;
        lock.lock();
        try {
            declineCount.put(
                party.getId(),
                declineCount.get(party.getId()) + 1
            );
        } finally {
            lock.unlock();
        }
    }

    public static boolean containsParty(MapleParty party) {
        return pendingParty.containsKey(party.getId());
    }

    public static void clear(MapleParty party) {
        if (pendingParty.get(party.getId()) != null) {
            pendingParty.clear();
        }
        if (declineCount.get(party.getId()) != null) {
            declineCount.clear();
        }
        if (agreeCount.get(party.getId()) != null) {
            agreeCount.clear();
        }
        if (answerCount.get(party.getId()) != null) {
            answerCount.clear();
        }
        if (sendPacketTime != null) {
            sendPacketTime.cancel(true);
        }
    }

    public static void sendContractMembers(
        Player p,
        String guildName,
        MapleParty party
    ) {
        if (p != null) {
            if (containsParty(party)) {
                sendNPCSay(
                    "The members of the group are still deciding, wait!",
                    p.getClient()
                );
                return;
            }
            if (isGuildNameAccept(guildName)) {
                sendNPCSay(
                    "The name of the guild you chose is not valid !",
                    p.getClient()
                );
                return;
            }
            if (party == null) {
                sendNPCSay(
                    "An error occurred. Please try again.",
                    p.getClient()
                );
                return;
            }
            if (
                p.getName() == null
                    ? party.getLeader().getName() != null
                    : !p.getName().equals(party.getLeader().getName())
            ) {
                sendNPCSay("Ask your leader to speak to me!", p.getClient());
                return;
            }
            if (p.getMeso() < GameConstants.GUILD_CRETECOST) {
                sendNPCSay("You don't have enough mesos!", p.getClient());
                return;
            }
            if (p.getParty().getMembers().size() < 6) {
                sendNPCSay(
                    "An error occurred. Please try again!",
                    p.getClient()
                );
                return;
            }
            for (final Player mc : p.getPartyMembers()) {
                if (
                    mc.getGuild() != null ||
                    mc.getMapId() != MapConstants.GUILD_ROOM
                ) {
                    sendNPCSay(
                        "Someone is already in a guild or not on the map!",
                        p.getClient()
                    );
                    return;
                }
            }
            for (Player mc : p.getPartyMembers()) {
                mc
                    .getClient()
                    .getSession()
                    .write(
                        GuildPackets.ContractGuildMember(
                            party.getId(),
                            guildName,
                            p.getName()
                        )
                    );
            }
            lock.lock();
            try {
                pendingParty.put(party.getId(), guildName);
                answerCount.put(party.getId(), (party.getMembers().size() - 1));
                agreeCount.put(party.getId(), 0);
                declineCount.put(party.getId(), 0);
            } finally {
                lock.unlock();
            }
            sendPacketTime =
                MapTimer
                    .getInstance()
                    .schedule(
                        () -> {
                            clear(party);
                        },
                        35000
                    );
        }
    }

    public static void receivedVote(
        Client c,
        MapleParty party,
        boolean voteReceived,
        int characterId
    ) {
        if (party == null || party.getLeader() == null) {
            return;
        }
        ChannelServer cserv = ChannelServer.getInstance(c.getChannel());
        Player p = cserv.getPlayerStorage().getCharacterById(characterId);
        if (p == null) {
            return;
        }
        final Player leader = party.getLeader().getPlayer();
        if (leader == null) {
            for (MaplePartyCharacter mc : party.getMembers()) {
                if (mc != null) {
                    mc
                        .getPlayer()
                        .dropMessage("An error occurred. Please try again!");
                }
            }
            return;
        }
        if (p.getParty().getMembers().size() < 6) {
            sendNPCSay(
                "There was an error, it seems that your group does not have all the necessary members.",
                leader.getClient()
            );
            clear(party);
            return;
        }

        if (!containsParty(party)) {
            sendNPCSay("An error occurred. Please try again!", p.getClient());
            return;
        }

        int vote = voteReceived ? 1 : 0;
        switch (vote) {
            case 0:
                gainDeclineCount(party);
                break;
            case 1:
                gainAgreeCount(party);
                break;
        }
        int totalVotes = (getAgreeCount(party) + getDeclineCount(party));
        if (totalVotes == (getAnswerCount(party))) {
            if (getAgreeCount(party) == (getAnswerCount(party))) {
                if (getGuildName(party) != null) {
                    createGuild(leader, getGuildName(party));
                    clear(party);
                } else {
                    sendNPCSay(
                        "An error occurred. Please try again!",
                        leader.getClient()
                    );
                }
            } else {
                sendNPCSay(
                    "Unfortunately, someone does not agree with your guild proposal!",
                    leader.getClient()
                );
                clear(party);
            }
        }
    }

    public static void sendNPCSay(String text, Client c) {
        c.announce(PacketCreator.GetNPCTalk(2010007, (byte) 0, text, "00 00"));
        c.announce(PacketCreator.EnableActions());
    }

    public static void createGuild(Player p, String gName) {
        int gid = GuildService.createGuild(p.getId(), gName);
        if (gid == 0) {
            p
                .getClient()
                .getSession()
                .write(GuildPackets.GenericGuildMessage((byte) 0x1C));
            return;
        }
        if (p.getMeso() < GameConstants.GUILD_CRETECOST) {
            return;
        }
        p.gainMeso(-GameConstants.GUILD_CRETECOST, true, false, true);
        p.setGuildId(gid);
        p.setGuildRank(1);
        p.saveGuildStatus();
        for (Player mc : p.getPartyMembers()) {
            if (mc != p) {
                mc.setGuildId(gid);
                mc.setGuildRank(5);
                int s = GuildService.addGuildMember(mc.getMGC());
                if (s == 0) {
                    mc.setGuildId(0);
                    return;
                }
                mc.saveGuildStatus();
                mc
                    .getClient()
                    .getSession()
                    .write(GuildPackets.ShowGuildInfo(mc));
                GuildService.setGuildMemberOnline(
                    mc.getMGC(),
                    true,
                    mc.getClient().getChannel()
                );
                GuildHandler.respawnPlayer(mc);
            } else {
                GuildService.setGuildMemberOnline(
                    mc.getMGC(),
                    true,
                    mc.getClient().getChannel()
                );
                p
                    .getClient()
                    .getSession()
                    .write(GuildPackets.ShowGuildInfo(mc));
                GuildHandler.respawnPlayer(mc);
            }
        }
        p.getClient().getSession().write(GuildPackets.ShowGuildInfo(p));
        p
            .getClient()
            .announce(
                PacketCreator.GetNPCTalk(
                    2010007,
                    (byte) 0,
                    "Congratulations~ " +
                    gName +
                    " the guild has been successfully registered.",
                    "00 00"
                )
            );
        GuildHandler.respawnPlayer(p);
    }

    private static boolean isGuildNameAccept(String name) {
        if (name.length() < 3 || name.length() > 12) {
            return true;
        }
        for (int i = 0; i < name.length(); i++) {
            if (
                !Character.isLowerCase(name.charAt(i)) &&
                !Character.isUpperCase(name.charAt(i))
            ) return true;
        }
        return false;
    }
}
