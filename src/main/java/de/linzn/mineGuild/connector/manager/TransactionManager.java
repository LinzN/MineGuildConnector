package de.linzn.mineGuild.connector.manager;

import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TransactionManager {
    private static Economy eCon = MineGuildConnectorPlugin.getEconomy();
    private static Chat chat = MineGuildConnectorPlugin.getChat();

    public static void player_deposit_transaction(UUID actor, double amount) {

    }

    public static void player_withdraw_transaction(UUID actor, double amount) {

    }


    public static void test_test(UUID guildUUID, double amount) {
        if (amount > 0) {
            eCon.depositPlayer(create_guild_name(guildUUID), amount);
        }
    }

    private static String create_guild_name(UUID guildUUID) {
        return "guild-" + guildUUID.toString();
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
