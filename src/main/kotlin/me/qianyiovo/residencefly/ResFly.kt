package me.qianyiovo.residencefly

import com.bekvon.bukkit.residence.Residence
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

class ResFly(private val plugin: JavaPlugin) : CommandExecutor {
    override fun onCommand(commandSender: CommandSender, command: Command, string: String, args: Array<out String>): Boolean {
        if (commandSender !is Player) {
            commandSender.sendMessage("You can only use this command in the game")
            return false
        }
        val residencePlugin = Bukkit.getServer().pluginManager.getPlugin("Residence") as Residence?
        val residenceManager = residencePlugin?.residenceManager
        val loc = (commandSender as Player?)?.location
        val residence = residenceManager?.getByLoc(loc)
        val locale = (commandSender as Player?)?.locale
        if (!commandSender.hasPermission("resfly.use")) {
            if (locale != null) {
                return if (locale.startsWith("zh")) {
                    commandSender.sendMessage("${ChatColor.RED}你没有权限访问此命令")
                    false
                } else {
                    commandSender.sendMessage("${ChatColor.RED}You don't have permission to access this command")
                    false
                }
            }
        }
        if (residence != null) {
            commandSender.allowFlight = !commandSender.allowFlight
            commandSender.setMetadata("resfly.use", FixedMetadataValue(plugin, commandSender.allowFlight))
            if (locale != null) {
                return if (locale.startsWith("zh")) {
                    commandSender.sendMessage(if (commandSender.allowFlight) "${ChatColor.GREEN}已启用飞行" else "${ChatColor.GREEN}已关闭飞行")
                    true
                } else {
                    commandSender.sendMessage(if (commandSender.allowFlight) "${ChatColor.GREEN}Flight enabled" else "${ChatColor.GREEN}Flight disabled")
                    true
                }
            }
        }
        if (locale != null) {
            return if (locale.startsWith("zh")) {
                commandSender.sendMessage("${ChatColor.RED}你必须在你的领地中才能使用此指令")
                false
            } else {
                commandSender.sendMessage("${ChatColor.RED}You must be in your claim to use this command")
                false
            }
        }
        return false
    }
}