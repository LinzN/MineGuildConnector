package de.linzn.mineGuild.connector.manager;

import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import de.linzn.mineSuite.economy.api.EconomyManager;
import de.linzn.mineSuite.economy.utils.EconomyType;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TransactionManager {
    //private static Economy eCon = MineSuiteCorePlugin.getEconomy();
    private static Chat chat = MineSuiteCorePlugin.getChat();


    public static void create_guild_account(UUID guildUUID) {
        //String guildUUIDName = create_guild_name(guildUUID);
        //if (!eCon.hasAccount(guildUUIDName)) {
        //    eCon.createPlayerAccount(guildUUIDName);
        //}
        //Con.withdrawPlayer(guildUUIDName, eCon.getBalance(guildUUIDName));
        if (EconomyManager.hasProfile(guildUUID, EconomyType.GUILD)) {
            EconomyManager.createProfile(guildUUID, EconomyType.GUILD);
        }
        EconomyManager.setProfileBalance(guildUUID, 0.0, EconomyType.GUILD);
    }

    public static void delete_guild_account(UUID guildUUID) {
        //String guildUUIDName = create_guild_name(guildUUID);
        //if (eCon.hasAccount(guildUUIDName)) {
        //    eCon.withdrawPlayer(guildUUIDName, eCon.getBalance(guildUUIDName));
        //}
        if (EconomyManager.hasProfile(guildUUID, EconomyType.GUILD)) {
            EconomyManager.deleteProfile(guildUUID, EconomyType.GUILD);
        }
    }

    public static void player_deposit_transaction(UUID guildUUID, UUID playerUUID, double amount) {
        if (amount <= 0) {
            return;
        }
        // String guildUUIDName = create_guild_name(guildUUID);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
        //if (!has_enough_for_transaction(offlinePlayer.getName(), amount)) {
        if (!has_enough_for_transaction(offlinePlayer.getUniqueId(), amount, EconomyType.PLAYER)) {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.NOT_ENOUGH_MONEY_TRANSACTION);
            }
            return;
        }

        //if (transaction(offlinePlayer.getName(), guildUUIDName, amount)) {
        if (EconomyManager.transactionBetweenProfiles(offlinePlayer.getUniqueId(), EconomyType.PLAYER, guildUUID, EconomyType.GUILD, amount)) {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_SUCCESS.replace("{zahl}", "" + amount));
            }
            MineGuildConnectorPlugin.inst().getLogger().info("Transaction SUCCESS: " + offlinePlayer.getName() + " to " + guildUUID + " :: " + amount);
        } else {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_ERROR.replace("{zahl}", "" + amount));
            }
            MineGuildConnectorPlugin.inst().getLogger().warning("Transaction ERROR: " + offlinePlayer.getName() + " to " + guildUUID + " :: " + amount);
        }

    }

    public static void add_to_guild_account(UUID guildUUID, double amount) {
        if (amount <= 0) {
            return;
        }
        //String guildUUIDName = create_guild_name(guildUUID);
        if (!EconomyManager.depositProfile(guildUUID, amount, EconomyType.GUILD).transactionSuccess()) {
            MineGuildConnectorPlugin.inst().getLogger().warning("Transaction ERROR: " + guildUUID.toString() + " :: " + amount);
        }
    }

    public static void player_withdraw_transaction(UUID guildUUID, UUID playerUUID, double amount) {
        if (amount <= 0) {
            return;
        }
        String guildUUIDName = create_guild_name(guildUUID);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
        //if (!has_enough_for_transaction(guildUUIDName, amount)) {
        if (!has_enough_for_transaction(guildUUID, amount, EconomyType.GUILD)) {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.NOT_ENOUGH_MONEY_TRANSACTION);
            }
            return;
        }

        //if (transaction(guildUUIDName, offlinePlayer.getName(), amount)) {
        if (transaction(guildUUID, EconomyType.GUILD, offlinePlayer.getUniqueId(), EconomyType.PLAYER, amount)) {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_SUCCESS.replace("{zahl}", "" + amount));
            }
            MineGuildConnectorPlugin.inst().getLogger().info("Transaction SUCCESS: " + guildUUIDName + " to " + offlinePlayer.getName() + " :: " + amount);
        } else {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_ERROR.replace("{zahl}", "" + amount));
            }
            MineGuildConnectorPlugin.inst().getLogger().warning("Transaction ERROR: " + guildUUIDName + " to " + offlinePlayer.getName() + " :: " + amount);
        }

    }

    //private static boolean transaction(String sourceName, String targetName, double amount) {
    //    return eCon.depositPlayer(targetName, amount).transactionSuccess() && eCon.withdrawPlayer(sourceName, amount).transactionSuccess();
    //}

    private static boolean transaction(UUID sourceName, EconomyType sourceType, UUID targetName, EconomyType targetType, double amount) {
        return EconomyManager.depositProfile(sourceName, amount, sourceType).transactionSuccess() && EconomyManager.withdrawProfile(targetName, amount, targetType).transactionSuccess();
        //return eCon.depositPlayer(targetName, amount).transactionSuccess() && eCon.withdrawPlayer(sourceName, amount).transactionSuccess();
    }

    //private static boolean has_enough_for_transaction(String accountName, double amount) {
    //    double bankAmount = eCon.getBalance(accountName);
    //    return bankAmount >= amount;
    //}
    private static boolean has_enough_for_transaction(UUID accountUUID, double amount, EconomyType type) {
        double bankAmount = EconomyManager.getBalance(accountUUID, type);
        return bankAmount >= amount;
    }

    private static String create_guild_name(UUID guildUUID) {
        return "guild_" + guildUUID.toString();
    }

    public static void player_set_prefix(Player player) {
        MineSuiteCorePlugin.getInstance().getLogger().info("Set guild prefix for " + player.getName());
        if (!player.hasPermission("mineguild.noprefix")) {
            chat.setPlayerPrefix(player, "??a");
        } else {
            if (chat.getPlayerPrefix(player) != null) {
                if (chat.getPlayerPrefix(player).equalsIgnoreCase("??a") || chat.getPlayerPrefix(player).equalsIgnoreCase("&a")) {
                    chat.setPlayerPrefix(player, null);
                }
            }
        }
    }

    public static void player_unset_prefix(Player player) {
        if (!player.hasPermission("mineguild.noprefix")) {
            MineSuiteCorePlugin.getInstance().getLogger().info("Unset guild prefix for " + player.getName());
            chat.setPlayerPrefix(player, null);
        }
    }

    public static boolean hasPrefix(Player player) {
        return chat.getPlayerPrefix(player) != null;
    }

}
