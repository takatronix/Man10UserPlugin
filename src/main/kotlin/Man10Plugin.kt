package red.man10

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class Man10UserPlugin : JavaPlugin() {

    var prefix = "[§5Man10User§f]"
    override fun onEnable() {
        // Plugin startup logic

        org.bukkit.Bukkit.broadcastMessage("aa")
        logger.info("Hello Kotlin!")
        org.bukkit.Bukkit.broadcastMessage("aa")
        logger.info("Hello Kotlin!")
        org.bukkit.Bukkit.broadcastMessage("aa")
        logger.info("Hello Kotlin!")



    }

    override fun onDisable() {
        broadcast("Disabling plugin...")
    }

    fun broadcast(message: String){
        Bukkit.broadcastMessage("${prefix} ${message}")
    }

}