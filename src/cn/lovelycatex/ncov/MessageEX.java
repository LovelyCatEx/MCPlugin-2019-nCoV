package cn.lovelycatex.ncov;

import com.sun.org.glassfish.gmbal.Description;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MessageEX {
    public void public_(String s) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',s));
    }
    public void private_(Player player, String s) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
    }
    public void private_(CommandSender commandSender, String s) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
    }
    public void private_(ConsoleCommandSender consoleCommandSender, String s) {
        consoleCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
    }
    @Description("Include [SimpleAPI] prefix")
    public void pprivate_(Player player, String s) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',nCovMain.CONFIG_PREFIX+s));
    }
    @Description("Include [SimpleAPI] prefix")
    public void pprivate_(CommandSender commandSender, String s) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',nCovMain.CONFIG_PREFIX+s));
    }
    @Description("Include [SimpleAPI] prefix")
    public void pprivate_(ConsoleCommandSender consoleCommandSender, String s) {
        consoleCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',nCovMain.CONFIG_PREFIX+s));
    }
}
