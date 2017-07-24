package DontTapTheWhiteTile.Command;

import DontTapTheWhiteTile.DontTapTheWhiteTile;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/7/23.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class DelGame extends Command{


    private static DontTapTheWhiteTile plugin = DontTapTheWhiteTile.getPlugin();

    public DelGame(){
        super("ddel");
        this.setAliases(new String[]{
                "ddel"
        });
        this.setDescription("DontTapTheWhiteTile del game setting");
        this.setUsage("/ddel");
        this.setPermission("DontTapTheWhiteTile.Command.DelGame");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args){
        if(!sender.hasPermission(this.getPermission())){
            sender.sendMessage(TextFormat.RED+"You don't have permission.");
            return true;
        }
        if(plugin.status != 0){
            plugin.status = 0;
            plugin.set = "";
            plugin.player = null;
            plugin.line = null;
            plugin.start = null;
            plugin.top = null;
            plugin.pos1 = null;
            plugin.pos2 = null;
            plugin.first = 0.0D;
            sender.sendMessage(TextFormat.GREEN+"成功删除配置");
        }else{
            sender.sendMessage(TextFormat.RED+"你还未设置");
        }
        return true;
    }

}
