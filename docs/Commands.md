# Command Help

## PlayerCommand

| command       | description                                                    |
| ------------- | -------------------------------------------------------------- |
| @joinevent    | If an event is in progress, use this to warp to the event map. |
| @leaveevent   | If an event has ended, use this to warp to your original map.  |
| @drops        | bring up the search interface for item drops                   |
| @playtime     | Shows the playing time within our server.                      |
| @gm <message> | Sends a message to all online GMs in the case of an emergency. |
| @uptime       | Shows how long the server has been online.                     |
| @dispose      | Fixes your character if it is stuck.                           |
| @help         | show a list of player commands                                 |

## GMCommand

| command                       | description                                       |
| ----------------------------- | ------------------------------------------------- |
| !hide                         | hides the current character                       |
| !drop <itemid> <quantity>     |
| !spawn <mobid>                |
| !level <level>                | set the current level                             |
| !event [optional <mapid>]     | begin an event on a channel                       |
| !zakum                        | spawn zakum                                       |
| !horntail                     | spawn horntail (TODO)                             |
| !cake [optional <mobhp>]      | spawn cake monster                                |
| !papu                         | spawn papulatus                                   |
| !map <mapid>                  | warp player to map                                |
| !bomb [optional <count>]      | spawn a bomb                                      |
| !killall                      | kill all monsters on the map                      |
| !levelup                      | increase the current level by one                 |
| !clock [optional <seconds>]   | set a timer at the top of the map                 |
| !buffme                       | apply all the buffs                               |
| !killmap                      | kill all the players on the map.                  |
| !mutemap                      | mute the current map                              |
| !unmute                       | unmute the current map                            |
| !unbuffmap                    | remove buffs from all players in the current map  |
| !setall <value>               | set all stats to the value                        |
| !whereami                     | show the id of the current map                    |
| !job <jobid>                  | set the job for the current character             |
| !fullhp                       | increase hp and mp to max values                  |
| !gainmeso <value>             | add mesos to your inventory                       |
| !healhere                     | heal all characters on the map                    |
| !dc [-f] <player>             | disconnect a player                               |
| !online                       | show the players online                           |
| !giftnx <player> <amount>     | gift a player nx                                  |
| !say <message>                | broadcast a message to the server                 |
| !warn <message>               | send a warning message to all players             |
| !openportal <portalid>        | open a portal                                     |
| !closeportal <portalid>       | close a portal                                    |
| !closeportals                 | close all portals in the current map              |
| !openportals                  | open all portals in the current map               |
| !itemcheck <player> <itemid>  | check how many items a player has                 |
| !song <songid>                | change the current bgm of the map                 |
| !removeitem <player> <itemid> | remove items from the inventory of a player       |
| !speakmega <player> <message> | send a message as another player on megaphone     |
| !speak <player> <message>     | say a message as a player                         |
| !speakmap <message>           | make all players speak a message                  |
| !mesoeveryone <amount>        | Give everyone online mesos                        |
| !charinfo <player>            | show information about a player                   |
| !cheaters                     | show information about cheaters                   |
| !maxskills                    | max the skills of the current player              |
| !cleardrops                   | remove drops from the map                         |
| !exprate <value> [all]        | change the experience rate for channel (or world) |
| !mesorate <value> [all]       | change the meso rate for the channel (or world)   |
| !droprate <value> [all]       | change the drop rate for the channel (or world)   |
| !questrate <value> [all]      | change the quest rate for the channel (or world)  |
| !fame <player> <value>        | set the fame of a character                       |
| !eventrules                   | show the event rules in the map                   |
| !setname <old> <new>          | sets the name of a character                      |
| !uptime                       | get how long the server has been online           |
| !item <itemid> <quantity>     | add an item to the inventory                      |
| !goto <location>              | transport to a town center                        |
| !help                         | show help for gm commands                         |

## AdminCommand

| command                                           | description                                         |
| ------------------------------------------------- | --------------------------------------------------- |
| !shutdowntime <time>                              | show notice for shutdown                            |
| !position                                         | show the current player position and foothold       |
| !expeds                                           | show the status of expeditions                      |
| !seeds                                            | spawn seeds in Henesys PQ                           |
| !reloadevents                                     | reload events                                       |
| !reloaddrops                                      | reload drops                                        |
| !reloadportals                                    | reload portal                                       |
| !reloadmaps                                       | reload the current map                              |
| !shutdown                                         | shutdown the server                                 |
| !servermessage <message>                          | set the current server message in the scroll bar    |
| !saveall                                          | save the current state of the world to the database |
| !warpallhere                                      | warp all players to the current map in the channel  |
| !superequip <id> <stat>                           | create a weapon with custom stats                   |
| !npc <npcid>                                      | spawn an npc at the current position                |
| !notice <type> <message>                          | send various message types to players               |
| !find <one of: npc, mob, item, map, skill> <term> | search for the id of an item                        |
| !warp [player] [mapid]                            | warp a player to a map                              |
| !warpchhere                                       | warp all players in the channel to the current map  |
| !warpmapto <mapid>                                | warp a player a map                                 |
| !warphere <player>                                | warp a player to the current map                    |
| !rgm <player> <message>                           | send a gm notice to a player                        |
| !pnpc <npcid>                                     | place an NPC at the current position                |
| !sreactor <mobid> <mobTime>                       | spawn a reactor at the current location             |
| !pmob <mobid> <mobTime>                           | place a mob spawn at the current position           |
| !showtrace <threadid>                             | show the stack trace on a thread                    |
| !threads                                          | show the currently running threads                  |
| !helpadmin                                        | show admin help                                     |
