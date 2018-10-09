package de.linzn.mineGuild.connector.manager;

import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TransactionManager {
    private static Economy eCon = MineGuildConnectorPlugin.getEconomy();
    private static Chat chat = MineGuildConnectorPlugin.getChat();

    public static void player_deposit_transaction(UUID guildUUID, UUID playerUUID, double amount) {
        if (amount <= 0) {
            return;
        }
        String guildUUIDName = create_guild_name(guildUUID);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
        if (!has_enough_for_transaction(offlinePlayer.getName(), amount)) {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.NOT_ENOUGH_MONEY_TRANSACTION);
            }
            return;
        }

        if (transaction(offlinePlayer.getName(), guildUUIDName, amount)) {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_SUCCESS);
            }
            MineGuildConnectorPlugin.inst().getLogger().info("Transaction SUCCESS: " + offlinePlayer.getName() + " to " + guildUUIDName + " :: " + amount);
        } else {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_ERROR);
            }
            MineGuildConnectorPlugin.inst().getLogger().warning("Transaction ERROR: " + offlinePlayer.getName() + " to " + guildUUIDName + " :: " + amount);
        }

    }

    public static void player_withdraw_transaction(UUID guildUUID, UUID playerUUID, double amount) {
        if (amount <= 0) {
            return;
        }
        String guildUUIDName = create_guild_name(guildUUID);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
        if (!has_enough_for_transaction(guildUUIDName, amount)) {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.NOT_ENOUGH_MONEY_TRANSACTION);
            }
            return;
        }

        if (transaction(guildUUIDName, offlinePlayer.getName(), amount)) {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_SUCCESS);
            }
            MineGuildConnectorPlugin.inst().getLogger().info("Transaction SUCCESS: " + guildUUIDName + " to " + offlinePlayer.getName() + " :: " + amount);
        } else {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_ERROR);
            }
            MineGuildConnectorPlugin.inst().getLogger().warning("Transaction ERROR: " + guildUUIDName + " to " + offlinePlayer.getName() + " :: " + amount);
        }

    }

    private static boolean transaction(String sourceName, String targetName, double amount) {
        return eCon.depositPlayer(targetName, amount).transactionSuccess() && eCon.withdrawPlayer(sourceName, amount).transactionSuccess();
    }

    private static boolean has_enough_for_transaction(String accountName, double amount) {
        double bankAmount = eCon.getBalance(accountName);
        return bankAmount >= amount;
    }

    private static String create_guild_name(UUID guildUUID) {
        return "guild_" + guildUUID.toString();
    }

    public static void player_set_prefix(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null && !player.hasPermission("mineguild.noprefix")) {
            chat.setPlayerPrefix(player, "§a");
        }
    }

    public static void player_unset_prefix(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null && !player.hasPermission("mineguild.noprefix")) {
            chat.setPlayerPrefix(player, null);
        }
    }

}
