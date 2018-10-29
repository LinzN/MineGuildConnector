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
    public static String NOT_IN_GUILD = ChatColor.DARK_RED + "Dies funktioniert ohne Gilde leider nicht!";
    public static String NOT_A_NUMBER = ChatColor.DARK_RED + "Dies ist keine gültige Zahl!";
    public static String NO_CONSOLE = ChatColor.DARK_RED + "Dies geht nur ingame!";
    public static String NOT_ENOUGH_TRANSACTION = ChatColor.DARK_RED + "Die kleinstmögliche Transaktion ist {zahl}!";
    public static String NOT_ENOUGH_MONEY_TRANSACTION = ChatColor.DARK_RED + "Transaktion fehlgeschlagen. Nicht genügend Mines auf Konto vorhanden!";
    public static String INVALID_GUILD_NAME = ChatColor.DARK_RED + "Dies ist kein gültiger Gildennamen. Verwende [a-zA-Z 0-9] 4 bis 20 Zeichen. Erstes Zeichen muss ein Buchstaben sein!";
    public static String NO_COMMAND = ChatColor.DARK_RED + "Dies ist kein gültiger Befehl. Gib {command} für Hilfe ein!";
    public static String COMMAND_USAGE = ChatColor.RED + "Benutze: {command}";
    public static String TRANSACTION_SUCCESS = ChatColor.GREEN + "Transaktion von {zahl} Mines erfolgreich abgeschlossen!";
    public static String TRANSACTION_ERROR = ChatColor.DARK_RED + "Transaktion von {zahl} Mines fehlgeschlagen. Bitte an Admin wenden!";
    public static String ACTION_WARNING = ChatColor.YELLOW + "Bist du dir bei dieser Aktion sicher? \n" + ChatColor.GREEN + "Bitte bestätigen: " + ChatColor.YELLOW + ChatColor.BOLD + "/guild confirm";
}
