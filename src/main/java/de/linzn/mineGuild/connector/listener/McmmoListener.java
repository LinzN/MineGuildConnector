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

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.experience.XPGainReason;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import de.linzn.mineGuild.connector.GuildDatabase;
import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.manager.GuildConnectorManager;
import de.linzn.mineGuild.connector.objects.Guild;
import de.linzn.mineGuild.connector.objects.GuildPlayer;
import de.linzn.mineGuild.connector.utils.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashSet;

public class McmmoListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void mcmmoEvent(final McMMOPlayerXpGainEvent event) {
        MineGuildConnectorPlugin.inst().getServer().getScheduler().runTaskAsynchronously(MineGuildConnectorPlugin.inst(), () -> {

            if (event.isCancelled()) {
                return;
            }
            if (event.getXpGainReason() == XPGainReason.UNKNOWN) {
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
            final PrimarySkillType skill = event.getSkill();
            double multiplier = PluginUtil.get_mcmmo_skill_value(skill);
            double base = 1.95D;
            HashSet<GuildPlayer> hashSet = GuildConnectorManager.get_nearby_guild_player(gPlayer);
            final float sharedXp = (float) (PluginUtil.get_mcmmo_multiplikator(guild.guildLevel) * event.getRawXpGained());
            for (GuildPlayer otherGPlayer : hashSet) {
                final Player p = Bukkit.getPlayer(otherGPlayer.getUUID());
                MineGuildConnectorPlugin.inst().getServer().getScheduler().runTask(MineGuildConnectorPlugin.inst(), () -> ExperienceAPI.addRawXP(p, skill.getName(), sharedXp, XPGainReason.UNKNOWN.name(), true));
            }
            if (hashSet.size() != 0) {
                multiplier = multiplier + 0.35D;
            }
            double guildExp = base * multiplier;
            MineGuildConnectorPlugin.inst().getGuildUpdater().addData(guild.guildUUID, guildExp);
        });

    }

}
