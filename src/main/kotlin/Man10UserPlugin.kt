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

            rs?.next()
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

            if(name == p.name){
                return 0
            }


            Thread.sleep(3500)

            broadcast("§c${name}が名前を${p.name}に変更したことを検出！ 過去の犯罪履歴/スコアを照合しています....")
            Thread.sleep(2500)
            var ret = mysql.execute("update pda_player_data set name = '${p.name}' where uuid = '${p.uniqueId}'")
            if(!ret){
                broadcast("§c${name}が名前を${p.name}に変更した呪いによりスコアを取得できなくなった...")
                return -1
            }
            broadcast("§e${name}が名前を${p.name}に変更したことを通知するため国王を呼び出しています..... §kxxxx")

            Bukkit.broadcastMessage("§8[国王] あっ？ぁ・・？？呼んだ？？？。いま、、ねてる・・・ぐぅ・・")
            Thread.sleep(2500)
            broadcast("§e${name}が名前を${p.name}に変更したことを通知するため国王を呼び出しています..... §kxxxx")
            Thread.sleep(2000)
            Bukkit.broadcastMessage("§8[国王] なんだって！！！！（驚）${name}が名前を${p.name}に変更しただと！！")
            Thread.sleep(2000)
            Bukkit.broadcastMessage("§8[国王] 名前変える頻繁に変えるやつだいたい犯罪者だからな、、えっと、、")
            Thread.sleep(2000)
            Bukkit.broadcastMessage("§8[国王] §e${name}(${p.name})の過去の犯罪履歴を登録・・。タァーン！（リターンキーを叩く）")
            Thread.sleep(2000)
            Bukkit.broadcastMessage("§8[国王] 過去データ更新処理、めんどくせえーー。")
            Thread.sleep(2000)
            Bukkit.broadcastMessage("§e[国王] ${name}(${p.name})の名前変更処理が完了！")
            Thread.sleep(2000)
            Bukkit.broadcastMessage("§8[国王] ${name}(${p.name})をブラックリストに登録っ！！。タァーン！（リターンキーを叩く）")

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