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
            return true
        }

        if (!commandSender.hasPermission("resfly.use")) {
            commandSender.sendLocalizedErrorMessage()
            return true
        }

        val residencePlugin = Bukkit.getServer().pluginManager.getPlugin("Residence") as Residence?
        val residenceManager = residencePlugin?.residenceManager
        val loc = commandSender.location
        val residence = residenceManager?.getByLoc(loc)

        if (residence != null && residence.permissions.ownerUUID == commandSender.uniqueId) {
            commandSender.allowFlight = !commandSender.allowFlight
            commandSender.setMetadata("resfly.use", FixedMetadataValue(plugin, commandSender.allowFlight))
            commandSender.sendLocalizedFlightStatusMessage()
            return true
        } else {
            commandSender.sendLocalizedNotInClaimMessage()
            return true
        }
    }

    private fun Player.sendLocalizedErrorMessage() {
        val message = if (locale.startsWith("zh")) {
            "${ChatColor.RED}你没有权限访问此命令"
        } else {
            "${ChatColor.RED}You don't have permission to access this command"
        }
        sendMessage(message)
    }

    private fun Player.sendLocalizedFlightStatusMessage() {
        val message = if (locale.startsWith("zh")) {
            if (allowFlight) "${ChatColor.GREEN}已启用飞行" else "${ChatColor.GREEN}已关闭飞行"
        } else {
            if (allowFlight) "${ChatColor.GREEN}Flight enabled" else "${ChatColor.GREEN}Flight disabled"
        }
        sendMessage(message)
    }

    private fun Player.sendLocalizedNotInClaimMessage() {
        val message = if (locale.startsWith("zh")) {
            "${ChatColor.RED}你必须在你的领地中才能使用此指令"
        } else {
            "${ChatColor.RED}You must be in your claim to use this command"
        }
        sendMessage(message)
    }
}
