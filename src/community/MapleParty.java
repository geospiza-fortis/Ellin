/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
                       Matthias Butz <matze@odinms.de>
                       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation. You may not use, modify
    or distribute this program under any other version of the
    GNU Affero General Public License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package community;

import client.Client;
import client.player.Player;
import handling.channel.ChannelServer;
import handling.coordinator.MapleMatchCheckerCoordinator;
import handling.coordinator.matchchecker.MatchCheckerListenerFactory.MatchCheckerType;
import handling.world.service.PartyService;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import scripting.event.EventInstanceManager;
import server.maps.Field;
import server.partyquest.mcpq.MCField;
import server.partyquest.mcpq.MonsterCarnival;

public class MapleParty implements Serializable {

    private int id;
    private int leaderId;
    private int nextEntry = 0;
    private MaplePartyCharacter leader;
    private List<MaplePartyCharacter> pqMembers = null;
    private final List<MaplePartyCharacter> members = new LinkedList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(
        false
    );
    private Map<Integer, Integer> histMembers = new HashMap<>();

    public MapleParty(int id, MaplePartyCharacter chrfor) {
        this.leader = chrfor;
        this.leaderId = chrfor.getId();
        this.members.add(chrfor);
        this.id = id;
    }

    public boolean containsMembers(MaplePartyCharacter member) {
        lock.writeLock().lock();
        try {
            return members.contains(member);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void addMember(MaplePartyCharacter member) {
        lock.writeLock().lock();
        try {
            histMembers.put(member.getId(), nextEntry);
            nextEntry++;

            members.add(member);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeMember(MaplePartyCharacter member) {
        lock.writeLock().lock();
        try {
            histMembers.remove(member.getId());
            members.remove(member);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void setLeader(MaplePartyCharacter victim) {
        this.leader = victim;
    }

    public void updateMember(MaplePartyCharacter member) {
        lock.writeLock().lock();
        try {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getId() == member.getId()) {
                    members.set(i, member);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public MaplePartyCharacter getMemberById(int id) {
        lock.writeLock().lock();
        try {
            for (MaplePartyCharacter chr : members) {
                if (chr.getId() == id) {
                    return chr;
                }
            }
            return null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Collection<MaplePartyCharacter> getMembers() {
        lock.writeLock().lock();
        try {
            return Collections.unmodifiableList(members);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<MaplePartyCharacter> getPartyMembers() {
        lock.writeLock().lock();
        try {
            return Collections.unmodifiableList(members);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public byte getPartyDoor(int cid) {
        List<Entry<Integer, Integer>> histList;

        lock.writeLock().lock();
        try {
            histList = new LinkedList<>(histMembers.entrySet());
        } finally {
            lock.writeLock().unlock();
        }

        Collections.sort(
            histList,
            (Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) ->
                (o1.getValue()).compareTo(o2.getValue())
        );

        byte slot = 0;
        for (Entry<Integer, Integer> e : histList) {
            if (e.getKey() == cid) break;
            slot++;
        }

        return slot;
    }

    public MaplePartyCharacter getMemberByPos(int pos) {
        int i = 0;
        for (MaplePartyCharacter chr : members) {
            if (pos == i) {
                return chr;
            }
            i++;
        }
        return null;
    }

    public void setEligibleMembers(List<MaplePartyCharacter> eliParty) {
        pqMembers = eliParty;
    }

    public Collection<MaplePartyCharacter> getEligibleMembers() {
        return Collections.unmodifiableList(pqMembers);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MaplePartyCharacter getLeader() {
        lock.writeLock().lock();
        try {
            for (MaplePartyCharacter mpc : members) {
                if (mpc.getId() == leaderId) {
                    return mpc;
                }
            }

            return null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getLeaderId() {
        return leaderId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MapleParty other = (MapleParty) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    public static void leaveParty(MapleParty party, Client c) {
        ChannelServer cs = c.getChannelServer();
        Player player = c.getPlayer();
        MaplePartyCharacter partyplayer = player.getMPC();

        if (party != null && partyplayer != null) {
            if (partyplayer.getId() == party.getLeaderId()) {
                cs.removeMapPartyMembers(party.getId());

                MCField monsterCarnival = player.getMCPQField();
                if (monsterCarnival != null) {
                    monsterCarnival.onPlayerDisconnected(player);
                }

                PartyService.updateParty(
                    party.getId(),
                    MaplePartyOperation.DISBAND,
                    partyplayer
                );

                EventInstanceManager eim = player.getEventInstance();
                if (eim != null) {
                    eim.disbandParty();
                }
            } else {
                Field map = player.getMap();
                if (map != null) {
                    map.removePartyMember(player);
                }

                MCField monsterCarnival = player.getMCPQField();
                if (monsterCarnival != null) {
                    monsterCarnival.onPlayerDisconnected(player);
                }

                PartyService.updateParty(
                    party.getId(),
                    MaplePartyOperation.LEAVE,
                    partyplayer
                );

                EventInstanceManager eim = player.getEventInstance();
                if (eim != null) {
                    eim.leftParty(player);
                }
            }

            player.setParty(null);

            MapleMatchCheckerCoordinator mmce = c
                .getChannelServer()
                .getMatchCheckerCoordinator();
            if (
                mmce.getMatchConfirmationLeaderid(player.getId()) ==
                player.getId() &&
                mmce.getMatchConfirmationType(player.getId()) ==
                MatchCheckerType.GUILD_CREATION
            ) {
                mmce.dismissMatchConfirmation(player.getId());
            }
        }
    }
}
