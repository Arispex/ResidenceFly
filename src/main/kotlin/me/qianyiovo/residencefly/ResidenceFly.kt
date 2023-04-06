package me.qianyiovo.residencefly

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ResidenceFly : JavaPlugin() {
    override fun onEnable() {
        getCommand("resfly")?.setExecutor(ResFly(this))
        server.pluginManager.registerEvents(Listener(this), this)
    }

    override fun onDisable() {
        Bukkit.getPluginManager().disablePlugin(this)
    }
}