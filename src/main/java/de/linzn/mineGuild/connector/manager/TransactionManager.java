package de.linzn.mineGuild.connector.manager;

import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TransactionManager {
    private static Economy eCon = MineSuiteCorePlugin.getEconomy();
    private static Chat chat = MineSuiteCorePlugin.getChat();

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
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_SUCCESS.replace("{zahl}", "" + amount));
            }
            MineGuildConnectorPlugin.inst().getLogger().info("Transaction SUCCESS: " + offlinePlayer.getName() + " to " + guildUUIDName + " :: " + amount);
        } else {
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LanguageDB.TRANSACTION_ERROR.replace("{zahl}", "" + amount));
            }
            MineGuildConnectorPlugin.inst().getLogger().warning("Transaction ERROR: " + offlinePlayer.getName() + " to " + guildUUIDName + " :: " + amount);
        }

    }

    public static void add_to_guild_account(UUID guildUUID, double amount) {
        if (amount <= 0) {
            return;
        }
        String guildUUIDName = create_guild_name(guildUUID);
        if (!eCon.depositPlayer(guildUUIDName, amount).transactionSuccess()) {
            MineGuildConnectorPlugin.inst().getLogger().warning("Transaction ERROR: " + guildUUIDName + " :: " + amount);
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

    public static void player_set_prefix(Player player) {
        if (!player.hasPermission("mineguild.noprefix")) {
            chat.setPlayerPrefix(player, "§a");
        }
    }

    public static void player_unset_prefix(Player player) {
            chat.setPlayerPrefix(player, null);
    }

    public static void migrate_guild_to_uuid(UUID guildUUID, String guildName) {
        double old_account = eCon.getBalance("Guild-" + guildName);

        if (!eCon.hasAccount(create_guild_name(guildUUID))) {
            eCon.createPlayerAccount(create_guild_name(guildUUID));
        }

        eCon.withdrawPlayer(create_guild_name(guildUUID), eCon.getBalance(create_guild_name(guildUUID))); /* Create a clean account */
        eCon.depositPlayer(create_guild_name(guildUUID), old_account);
        eCon.withdrawPlayer("Guild-" + guildName, eCon.getBalance("Guild-" + guildName)); /* Cleanup old account */
        MineGuildConnectorPlugin.inst().getLogger().warning("Transaction migration: " + "Guild-" + guildName + " to " + create_guild_name(guildUUID) + " :: " + old_account);
    }

}
