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

package de.linzn.mineGuild.connector.listener;

import com.gamingmesh.jobs.api.JobsPaymentEvent;
import de.linzn.mineGuild.connector.GuildDatabase;
import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.objects.Guild;
import de.linzn.mineGuild.connector.objects.GuildPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class JobsRebornListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void payInGuildBank(JobsPaymentEvent event) {
        double taxes = 10D;

        if (event.isCancelled()) {
            return;
        }

        GuildPlayer gPlayer = GuildDatabase.getGuildPlayer(event.getPlayer().getUniqueId());
        if (gPlayer == null) {
            return;
        }
        Guild guild = gPlayer.getGuild();
        if (guild == null) {
            return;
        }

        double amount = event.getAmount();
        double guildAmount = amount / 100D * taxes;
        double newAmount = amount - guildAmount;
        event.setAmount(newAmount);

        //CookieVaultApi.addGuildBalance(guild.getGuildName(), guildAmount);
        MineGuildConnectorPlugin.inst().getLogger().info("DEBUG: Add " + guildAmount + " Money to Guild " + guild.guildUUID.toString());

    }
}
