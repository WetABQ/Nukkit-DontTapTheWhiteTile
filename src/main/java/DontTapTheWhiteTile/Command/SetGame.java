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
public class SetGame extends Command{

    private static DontTapTheWhiteTile plugin = DontTapTheWhiteTile.getPlugin();

    public SetGame(){
        super("dset");
        this.setAliases(new String[]{
                "dset"
        });
        this.setDescription("DontTapTheWhiteTile set game");
        this.setUsage("/dset");
        this.setPermission("DontTapTheWhiteTile.Command.SetGame");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args){
        if(!sender.hasPermission(this.getPermission())){
            sender.sendMessage(TextFormat.RED+"You don't have permission.");
            return true;
        }
        if(args.length == 0){
            if(plugin.status == 0){
                plugin.set = sender.getName();
                sender.sendMessage(TextFormat.GREEN+"你已经处于设置状态下,请先设置一个4(宽)*5(高)的方框");
            }else{
                sender.sendMessage(TextFormat.RED+"已经设置 或 你处于设置状态");
            }
        }else if(args[0].equals("b")){
            plugin.status = 0;
            plugin.set = "";
            plugin.player = null;
            plugin.line = null;
            plugin.start = null;
            plugin.top = null;
            plugin.pos1 = null;
            plugin.pos2 = null;
            plugin.first = 0.0D;
            sender.sendMessage(TextFormat.GREEN+"成功退出设置");
        }
        return true;
    }

}
