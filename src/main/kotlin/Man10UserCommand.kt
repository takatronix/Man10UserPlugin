package red.man10

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

public class Man10UserCommand (_plugin: Man10UserPlugin) : CommandExecutor {

    var plugin = _plugin
    val adminPermision = "red.man10.user.admin"

    val permissionErrorString = "§4§lYou don't have permission."



    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {

        if (sender is Player == false) {
            sender.sendMessage("Can't use in console.")
            return false
        }

        val p = sender as Player

        if (args.size == 0) {
            showHelp(sender)
            return false
        }

        val command = args[0]
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin) {
            try {

                plugin.broadcast("start")
                Thread.sleep(1000);
                plugin.broadcast(" おわり")




            } catch (e:Exception){
                Bukkit.getLogger().info(e.message)
                println(e.message)
            }
        }



        return true
    }

    fun showHelp(p: CommandSender) {

        p.sendMessage("§e§l Man10 User management plugin / created by takatronix.com")
        p.sendMessage("§e§l supported at 'man10.red' <-- Japanese Minecraft Server")


    }
}