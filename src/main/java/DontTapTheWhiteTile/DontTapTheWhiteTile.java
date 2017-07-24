package DontTapTheWhiteTile;

import DontTapTheWhiteTile.Command.DelGame;
import DontTapTheWhiteTile.Command.SetGame;
import DontTapTheWhiteTile.Listener.EventListener;
import DontTapTheWhiteTile.Task.CheckGameTimeout;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.level.Position;
import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/7/22.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class DontTapTheWhiteTile extends PluginBase{

    private Config config; //Config
    public String player; //游戏玩家
    public String set; //设置玩家
    public HashMap<Integer,HashMap<Integer,Position>> line = new HashMap<>(); // 游戏区域
    public Integer status; //设置状态
    public Position top; //排行榜坐标
    public Position top2; //排行榜2坐标
    public Position top3; //排行榜3坐标
    public Position start; //开始坐标
    public Position pos1;//方框点1
    public Position pos2;//方框点2
    public long StartTime;
    public int game;
    public double first;
    public double second;
    public double third;
    public HashMap<Integer,HashMap<Integer,Block>> color = new HashMap<>();
    private static DontTapTheWhiteTile plugin;

    public static DontTapTheWhiteTile getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable(){
        plugin = this;
        this.getServer().getPluginManager().registerEvents(new EventListener(),this);
        this.config = new Config(this.getDataFolder()+"/config.yml",Config.YAML);
        this.getServer().getScheduler().scheduleRepeatingTask(new CheckGameTimeout(this), 20);
        if (!this.config.exists("status")){
            this.config.set("status","0"); //设置状态
            this.config.set("top","false"); //第一名牌子
            this.config.set("top2","false"); //第二名牌子
            this.config.set("top3","false"); //第三名牌子
            this.config.set("start","false"); //开始牌子
            this.config.set("line",new HashMap<>());//游戏区域
            this.config.set("first","999");
            this.config.set("second","999");
            this.config.set("third","999");
            this.config.save();
        }
        initCommand();
        status = Integer.parseInt(config.get("status").toString());
        if(status != 0) {
            String[] t = config.get("top").toString().split(":");
            top = new Position(Float.parseFloat(t[0]),Float.parseFloat(t[1]), Float.parseFloat(t[2]), this.getServer().getLevelByName(t[3]));
            String[] t2 = config.get("top2").toString().split(":");
            top2 = new Position(Float.parseFloat(t2[0]),Float.parseFloat(t2[1]), Float.parseFloat(t2[2]), this.getServer().getLevelByName(t2[3]));
            String[] t3 = config.get("top3").toString().split(":");
            top3 = new Position(Float.parseFloat(t3[0]),Float.parseFloat(t3[1]), Float.parseFloat(t3[2]), this.getServer().getLevelByName(t3[3]));
            //this.getLogger().info(""+top);
            String[] s = config.get("start").toString().split(":");
            start = new Position(Float.parseFloat(s[0]), Float.parseFloat(s[1]), Float.parseFloat(s[2]),this.getServer().getLevelByName(t[3]));
            //this.getLogger().info(""+start);
            String[] l = config.get("line").toString().split("\\|");
            for(String l1 : l){
                HashMap<Integer,Position> map = new HashMap<>();
                String[] l2 = l1.split("=>");
                String[] l3 = l2[1].split("@");
                for(String l4: l3){
                    String[] l5 = l4.split("->");
                    String[] l6 = l5[1].split(":");
                    map.put(Integer.parseInt(l5[0]),new Position(Float.parseFloat(l6[0]),Float.parseFloat(l6[1]),Float.parseFloat(l6[2]),this.getServer().getLevelByName(l6[3])));
                    if(Integer.parseInt(l5[0]) == 3){
                        line.put(Integer.parseInt(l2[0]),map);
                    }
                }
                //line.put(Integer.parseInt(l2[0]),map);
                //this.getLogger().info(""+line);
            }
            //line = (HashMap) config.get("line");
            first = Double.parseDouble(config.get("first").toString());
            second = Double.parseDouble(config.get("second").toString());
            third = Double.parseDouble(config.get("third").toString());
        }else{
            top = new Position();
            top2 = new Position();
            top3 = new Position();
            start = new Position();
            line = new HashMap();
            first = 999;
            second = 999;
            third = 999;
        }
        this.player = null;
        this.getLogger().notice("游戏 *别踩白块儿* 开启 !");
    }

    @Override
    public void onDisable(){
        this.getLogger().warning("游戏 *别踩白块儿* 卸载!");
        saveData();
    }

    private void initCommand(){
        HashMap<String, String> permissions = new HashMap<>();
        permissions.put("DontTapTheWhiteTile.Command.SetGame", "op");
        permissions.put("DontTapTheWhiteTile.Command.DelGame", "op");
        permissions.forEach((name, permission) -> Server.getInstance().getPluginManager().addPermission(new Permission(name, permission)));
        Server.getInstance().getCommandMap().register("", new SetGame());
        Server.getInstance().getCommandMap().register("", new DelGame());
    }


    public void saveData(){
        /*ConfigSection map = new ConfigSection();
        map.put("status",status);
        map.put("top",top.x+":"+top.y+":"+top.z+":"+top.level.getFolderName());
        map.put("start",start.x+":"+start.y+":"+start.z+":"+start.level.getFolderName());
        map.put("line",line);
        map.put("first",first);
        //LinkedHashMap<String,Object> l = new LinkedHashMap<>();
        //l.putAll(map);
        config.setAll(map);*/
        if(status != 0) {
            config.set("status", status);
            config.set("top", top.x + ":" + top.y + ":" + top.z + ":" + top.level.getFolderName());
            config.set("top2", top2.x + ":" + top2.y + ":" + top2.z + ":" + top2.level.getFolderName());
            config.set("top3", top3.x + ":" + top3.y + ":" + top3.z + ":" + top3.level.getFolderName());
            config.set("start", start.x + ":" + start.y + ":" + start.z + ":" + start.level.getFolderName());
            String l = "";
            for (Map.Entry<Integer, HashMap<Integer, Position>> entry : line.entrySet()) {
                l += entry.getKey() + "=>";
                for (Map.Entry<Integer, Position> entry1 : entry.getValue().entrySet()) {
                    l += entry1.getKey() + "->" + entry1.getValue().x + ":" + entry1.getValue().y + ":" + entry1.getValue().z + ":" + entry1.getValue().level.getFolderName() + "@";
                }
                l = l.substring(0, l.length() - 1);
                l += "|";
            }
            l = l.substring(0, l.length() - 1);
            config.set("line", l);
            config.set("first", first);
            config.set("second", second);
            config.set("third", third);
            config.save();
        }
    }

    public void GameTimeout(){
        player = null;
        game = 0;
        StartTime = 0;
        Server.getInstance().broadcastMessage(TextFormat.RED+"DontTapTheWhiteTile >> 游戏超时");
        BlockEntity blockEntity = plugin.line.get(0).get(0).getLevel().getBlockEntity(plugin.start);
        BlockEntitySign sign = new BlockEntitySign(blockEntity.chunk, blockEntity.namedTag);
        sign.setText(TextFormat.GOLD + "§lDontTapTheWhiteTile", TextFormat.GREEN + "游戏状态: 点击开始", TextFormat.AQUA + "当前玩家: 无", TextFormat.WHITE + "作者:WetABQ");
        EventListener.wtBlock(line,line.get(0).get(0).getLevel());
    }

}
