package me.qianyiovo.residencefly

import com.bekvon.bukkit.residence.event.ResidenceChangedEvent
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

class Listener(private val plugin: JavaPlugin) : Listener{
    @EventHandler
    fun onResidenceChanged(event: ResidenceChangedEvent) {
        val player = event.player
        if (!player.hasPermission("resfly.use")) {
            return
        }
        if (player.hasMetadata("resfly.use") && event.from != null && event.to == null) {
            if (player.getMetadata("resfly.use")[0].asBoolean()) {
                player.allowFlight = false
                player.setMetadata("resfly.use", FixedMetadataValue(plugin, false))
                val locale = player.locale
                if (locale.startsWith("zh")) {
                    player.sendMessage("${ChatColor.GREEN}已关闭飞行")
                }
                else {
                    player.sendMessage("${ChatColor.GREEN}Flight disabled")
                }
            }
        }
    }
}