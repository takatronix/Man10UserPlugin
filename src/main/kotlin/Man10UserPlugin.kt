package red.man10

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener
import java.sql.ResultSet


class Man10UserPlugin : JavaPlugin() , Listener {

    var prefix = "[§5Man10User§f]"

    override fun onEnable() {

        server.pluginManager.registerEvents(this, this)
        saveResource("config.yml", false)

        getCommand("muser").setExecutor(Man10UserCommand(this));
    }

    override fun onDisable() {
        broadcast("Disabling plugin...")
    }


    fun broadcast(message: String) {
        Bukkit.broadcastMessage("${prefix} ${message}")
    }
    fun sendMessage(player:Player, message: String) {
        player.sendMessage("${prefix} ${message}")
    }


    fun showError(player:Player, message: String) {
        player.sendMessage("${prefix} §c${message}")
        for(p in Bukkit.getOnlinePlayers()){
            if(p.isOp){
                sendMessage(p,"§cエラー発生: ユーザー:${player.name} ${message}")
            }
        }
    }


    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        Bukkit.getScheduler().runTaskAsynchronously(this) {
            checkUser(e.player)
        }
    }



    fun checkUser(p:Player):Int{

        var mysql = MySQLManager(this,"man10user")

        try{
            var rs = mysql.query("select * from pda_player_data where uuid = '${p.uniqueId}'");

            if(rs == null){
                mysql.close()
                showError(p,"データベース接続エラー")
                return -1
            }

            var name = rs.getString("name")

            if(name == null){
                showError(p,"${p.name}の過去の名前取得失敗")
                return -1
            }

            broadcast("${p.name}の過去の名前は${name}")


        }
        catch (e:Exception)
        {
            mysql.close();
            showError(p,"例外発生 ${e.message}")
            return -1;
        }
        mysql.close();


        return 0
    }




}