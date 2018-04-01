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

import org.bukkit.ChatColor;

public class LanguageDB {

    public static String NO_PERMISSIONS = ChatColor.DARK_RED + "Du hast keine Berechtigung!";
    public static String NOT_A_NUMBER = ChatColor.DARK_RED + "Dies ist keine gültige Zahl!";
    public static String NO_CONSOLE = ChatColor.DARK_RED + "Dies geht nur ingame!";
    public static String INVALID_GUILD_NAME = ChatColor.DARK_RED + "Der Gildenname darf nur aus Zahlen und Buchstaben bestehen!";
    public static String NO_COMMAND = ChatColor.DARK_RED + "Dies ist kein gültiger Befehl. Gib {command} für Hilfe ein!";
    public static String COMMAND_USAGE = ChatColor.RED + "Benutze: {command}";
}
