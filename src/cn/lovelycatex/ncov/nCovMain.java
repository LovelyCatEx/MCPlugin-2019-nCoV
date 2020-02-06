package cn.lovelycatex.ncov;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class nCovMain extends JavaPlugin {
    public static String CONFIG_API = null;
    public static boolean CONFIG_ENABLED = false;
    public static String CONFIG_PREFIX = null;

    public static Describe describe = new Describe();
    public static Province province = new Province();
    public static News news = new News();

    private MessageEX m = new MessageEX();
    private ConsoleCommandSender ccs = getServer().getConsoleSender();
    @Override
    public void onEnable() {
        saveDefaultConfig();

        CONFIG_API = getConfig().getString("api");
        CONFIG_ENABLED = getConfig().getBoolean("enable");
        CONFIG_PREFIX = getConfig().getString("prefix");

        m.pprivate_(ccs,"&anCoV疫情动态查询插件已启用！");
        m.pprivate_(ccs,"&aAuthor &d>> &bLovelyCat");

        DataAPI.updateData(ccs);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();
        if (cmd.equalsIgnoreCase("ncov")) {
            if (!sender.hasPermission("ncov.use")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c您无权操作！"));
                return true;
            }
            switch (args.length) {
                case 0:
                    for (int i=0;i<getHelpList().size();i++) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getHelpList().get(i)));
                    }
                    break;
                case 1:
                    //单个参数 仅GET概况 最新新闻
                    if (args[0].equalsIgnoreCase("get")) {
                        if (!CONFIG_ENABLED && !sender.isOp()) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c已关闭查询功能！"));
                            return true;
                        }
                        List<String> list = DataAPI.getPreview();
                        for (int i=0;i<list.size();i++) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',list.get(i)));
                        }
                    }else if (args[0].equalsIgnoreCase("reload")) {
                        if (!sender.hasPermission("ncov.reload")) {
                            m.pprivate_(sender,"&c您无权操作！");
                            return true;
                        }
                        reload(sender);
                    }else if (args[0].equalsIgnoreCase("update")) {
                        if (!sender.hasPermission("ncov.update")) {
                            m.pprivate_(sender,"&c您无权操作！");
                            return true;
                        }
                        DataAPI.updateData(sender);
                        m.pprivate_(sender,"&a数据已更新！");
                    }else {
                        m.pprivate_(sender,"&c请检查指令是否错误！");
                    }
                    break;
                case 2:
                    //双参数 获取省份 获取新闻
                    if (args[0].equalsIgnoreCase("get")) {
                        if (!CONFIG_ENABLED && !sender.isOp()) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c已关闭查询功能！"));
                            return true;
                        }
                        if (!args[1].equalsIgnoreCase("news") && !args[1].equalsIgnoreCase("allnews")) {
                            List<String> list = DataAPI.getProvince(args[1]);
                            for (int i=0;i<list.size();i++) {
                                m.private_(sender,list.get(i));
                            }
                        }else if (args[1].equalsIgnoreCase("news")) {
                            List<String> list = DataAPI.getNews(false);
                            for (int i=0;i<list.size();i++) {
                                m.private_(sender,list.get(i));
                            }
                        }else if (args[1].equalsIgnoreCase("allnews")) {
                            List<String> list = DataAPI.getNews(true);
                            for (int i=0;i<list.size();i++) {
                                m.private_(sender,list.get(i));
                            }
                        }else {
                            m.pprivate_(sender,"&c请检查指令是否错误！");
                        }
                    }else {
                        m.pprivate_(sender,"&c请检查指令是否错误！");
                    }


                    break;
            }
        }
        return true;
    }
    public void reload(CommandSender sender){
        saveDefaultConfig();
        reloadConfig();
        CONFIG_API = getConfig().getString("api");
        CONFIG_ENABLED = getConfig().getBoolean("enable");
        CONFIG_PREFIX = getConfig().getString("prefix");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a插件已成功重载！"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a信息前缀：&b"+CONFIG_PREFIX));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&aAPI地址：&b"+CONFIG_API));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a是否开放查询：&b"+CONFIG_ENABLED));
    }

    public List<String> getHelpList() {
        List<String> aL = new ArrayList<>();
        aL.add("&6>>&bnCov疫情动态查询");
        aL.add("&b/ncov &e获取帮助");
        aL.add("&b/ncov get &e获取疫情概况");
        aL.add("&b/ncov get [城市名]&e获取指定城市疫情信息");
        aL.add("&b/ncov get news &e获取最新新闻");
        aL.add("&b/ncov get allnews &e获取所有新闻");

        return aL;
    }
}
