/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.mineGuild.connector.utils;


import com.gmail.nossr50.datatypes.skills.PrimarySkillType;

public class PluginUtil {
    public static double get_mcmmo_skill_value(PrimarySkillType skill) {
        double multiplier;

        switch (skill) {
            case ACROBATICS:
                multiplier = 0.4D;
                break;
            case ALCHEMY:
                multiplier = 0.9D;
                break;
            case ARCHERY:
                multiplier = 0.3D;
                break;
            case AXES:
                multiplier = 0.3D;
                break;
            case EXCAVATION:
                multiplier = 0.4D;
                break;
            case FISHING:
                multiplier = 0.3D;
                break;
            case HERBALISM:
                multiplier = 0.44D;
                break;
            case MINING:
                multiplier = 0.34D;
                break;
            case REPAIR:
                multiplier = 0.9D;
                break;
            case SALVAGE:
                multiplier = 0.9D;
                break;
            case SMELTING:
                multiplier = 0.9D;
                break;
            case SWORDS:
                multiplier = 0.3D;
                break;
            case TAMING:
                multiplier = 0.9D;
                break;
            case UNARMED:
                multiplier = 0.3D;
                break;
            case WOODCUTTING:
                multiplier = 0.3D;
                break;
            default:
                multiplier = 0.2D;
                break;
        }
        return multiplier;
    }

    public static double get_mcmmo_multiplikator(int guildLevel) {
        double value;
        int maxLevel = 30;
        int maxShare = 60;
        if (guildLevel < maxLevel) {
            value = ((50D / (double) maxLevel) * (double) guildLevel);
        } else {
            value = ((maxShare / (double) maxLevel) * (double) guildLevel);
        }
        value = (value / 100D);
        return value;
    }

    public static boolean is_valid_guild_name(String guildName) {
        if (!Character.isLetter(guildName.charAt(0))) {
            return false;
        }
        if (guildName.length() < 4 || guildName.length() >= 20) {
            return false;
        }
        for (char c : guildName.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
