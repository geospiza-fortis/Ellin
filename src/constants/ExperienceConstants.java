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

package constants;

public class ExperienceConstants {
    
     private static int[] exp = {0, 15, 34, 57, 92, 135, 372, 560, 840, 1242, 1716, 2360, 3216, 4200, 5460, 7050, 8840,
        11040, 13716, 16680, 20216, 24402, 28980, 34320, 40512, 47216, 54900, 63666, 73080, 83720, 95700, 108480,
        122760, 138666, 155540, 174216, 194832, 216600, 240500, 266682, 294216, 324240, 356916, 391160, 428280, 468450,
        510420, 555680, 604416, 655200, 709716, 748608, 789631, 832902, 878545, 926689, 977471, 1031036, 1087536,
        1147032, 1209994, 1276301, 1346242, 1420016, 1497832, 1579913, 1666492, 1757815, 1854143, 1955750, 2062925,
        2175973, 2295216, 2420993, 2553663, 2693603, 2841212, 2996910, 3161140, 3334370, 3517093, 3709829, 3913127,
        4127566, 4353756, 4592341, 4844001, 5109452, 5389449, 5684790, 5996316, 6324914, 6671519, 7037118, 7422752,
        7829518, 8258575, 8711144, 9188514, 9692044, 10223168, 10783397, 11374327, 11997640, 12655110, 13348610,
        14080113, 14851703, 15665576, 16524049, 17429566, 18384706, 19392187, 20454878, 21575805, 22758159, 24005306,
        25320796, 26708375, 28171993, 29715818, 31344244, 33061908, 34873700, 36784778, 38800583, 40926854, 43169645,
        45535341, 48030677, 50662758, 53439077, 56367538, 59456479, 62714694, 66151459, 69776558, 73600313, 77633610,
        81887931, 86375389, 91108760, 96101520, 101367883, 106992842, 112782213, 118962678, 125481832, 132358236,
        139611467, 147262175, 155332142, 163844343, 172823012, 182293713, 192283408, 202820538, 213935103, 225658746,
        238024845, 251068606, 264827165, 279339639, 294647508, 310794191, 327825712, 345790561, 364739883, 384727628,
        405810702, 428049128, 451506220, 476248760, 502347192, 529875818, 558913012, 589541445, 621848316, 655925603,
        691870326, 729784819, 769777027, 811960808, 856456260, 903390063, 952895838, 1005114529, 1060194805,
        1118293480, 1179575962, 1244216724, 1312399800, 1384319309, 1460180007, 1540197871, 1624600714, 1713628833,
        1807535693, 1906558648, 2011069705, 2121276324
    };     

    private static final int[] closeness = {
        0, 1, 3, 6, 14, 31, 60, 108, 181, 287,
        434, 632, 891, 1224, 1642, 2161, 2793,
        3557, 4467, 5542, 6801, 8263, 9950, 11882,
        14084, 16578, 19391, 22547, 26074, 30000};


    private static final int[] mountexp = {
        0, 6, 25, 50, 105, 134, 196, 254, 263,
        315, 367, 430, 543, 587, 679, 725, 897,
        1146, 1394, 1701, 2247, 2543, 2898, 3156,
        3313, 3584, 3923, 4150, 4305, 4550};
    
    public static int getPartyQuestExp(String PQ, int level) {
        switch (PQ) {
            case "HenesysPQ":
                return 1250 * level / 5;
            case "KerningPQFinal":
                return 500 * level / 5;
            case "KerningPQ4th":
                return 400 * level / 5;
            case "KerningPQ3rd":
                return 300 * level / 5;
            case "KerningPQ2nd":
                return 200 * level / 5;
            case "KerningPQ1st":
               return 100 * level / 5;
            case "LudiMazePQ":
                return 2000 * level / 5;
            case "LudiPQ1st":
                return 100 * level / 5;
            case "LudiPQ2nd":
                return 250 * level / 5;
            case "LudiPQ3rd":
                return 350 * level / 5;
            case "LudiPQ4th":
                return 350 * level / 5;
            case "LudiPQ5th":
                return 400 * level / 5;
            case "LudiPQ6th":
                return 450 * level / 5;
            case "LudiPQ7th":
                return 500 * level / 5;
            case "LudiPQ8th":
                return 650 * level / 5;
            case "LudiPQLast":
                return 800 * level / 5;
        }
        return 0;
    }

    public static int getMountExpNeededForLevel(int level) {
        if (level > mountexp.length || level < 0) {
            return 4550;
        }
        return mountexp[level - 1];
    }
	
     public static int getExpNeededForLevel(int level) {
        if (level > 200) {
            return Integer.MAX_VALUE;
        }
        return exp[level];
    }
	
    public static int getClosenessNeededForLevel(int level) {
        return closeness[level - 1];
    }
}