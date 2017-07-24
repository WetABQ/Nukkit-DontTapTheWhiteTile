package DontTapTheWhiteTile.Listener;

import DontTapTheWhiteTile.DontTapTheWhiteTile;
import DontTapTheWhiteTile.Task.CheckGameTimeout;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
public class EventListener implements Listener {

    private static DontTapTheWhiteTile plugin = DontTapTheWhiteTile.getPlugin();


    @EventHandler
    public void onPlyaerJoinEvent(final PlayerJoinEvent event){
        if(plugin.player != null) {
            if (plugin.player.equals(event.getPlayer().getName())) {
                CheckGameTimeout.time = 200;
            }
        }
    }

    @EventHandler
    public void onPlyaerQuitEvent(final PlayerQuitEvent event){
        if(plugin.player != null) {
            if (plugin.player.equals(event.getPlayer().getName())) {
                CheckGameTimeout.time = 200;
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        //plugin.getLogger().notice(plugin.line+"");
        if (player.getName().equals(plugin.set)) {
            switch (plugin.status) {
                case 0:
                    plugin.pos1 = event.getBlock().getLocation();
                    player.sendTitle(TextFormat.GOLD + "请点击方块设置方框的第二点");
                    player.setSubtitle(TextFormat.RED + "方框的第一点设置 x=" + event.getBlock().x + " y=" + event.getBlock().y + " z=" + event.getBlock().z);
                    plugin.status++;
                    break;
                case 1:
                    if (plugin.pos1.getLevel().getFolderName().equals(event.getBlock().getLevel().getFolderName())) {
                        if (plugin.pos1.x == block.x) {
                            if (Math.abs(plugin.pos1.y - block.y) == 4 && Math.abs(plugin.pos1.z - block.z) == 3) {
                                plugin.pos2 = block.getLocation();
                                int x1 = (int) plugin.pos1.x;
                                int y1;
                                if (plugin.pos1.y > plugin.pos2.y) {
                                    y1 = (int) plugin.pos2.y;
                                } else {
                                    y1 = (int) plugin.pos1.y;
                                }
                                int z1;
                                if (plugin.pos1.z > plugin.pos2.z) {
                                    z1 = (int) plugin.pos2.z;
                                } else {
                                    z1 = (int) plugin.pos1.z;
                                }
                                HashMap<Integer, HashMap<Integer, Position>> line;
                                line = wtLine(x1, y1, z1, block.getLevel(), true);
                                plugin.line = line;
                                plugin.status++;
                                wtBlock(line, player.getLevel());
                                player.sendTitle(TextFormat.GOLD + "请点击木牌作为游戏开始按钮");
                                player.setSubtitle(TextFormat.RED + "方框的第二点设置 x=" + event.getBlock().x + " y=" + event.getBlock().y + " z=" + event.getBlock().z);
                            } else {
                                plugin.status = 0;
                                player.sendMessage(TextFormat.RED + "请确认设置是在同一地图的 4(宽) * 5(高) 的方框");
                            }
                        } else if (plugin.pos1.z == block.z) {
                            if (Math.abs(plugin.pos1.y - block.y) == 4 && Math.abs(plugin.pos1.x - block.x) == 3) {
                                plugin.pos2 = block.getLocation();
                                int z1 = (int) plugin.pos1.z;
                                int y1;
                                if (plugin.pos1.y > plugin.pos2.y) {
                                    y1 = (int) plugin.pos2.y;
                                } else {
                                    y1 = (int) plugin.pos1.y;
                                }
                                int x1;
                                if (plugin.pos1.x > plugin.pos2.x) {
                                    x1 = (int) plugin.pos2.x;
                                } else {
                                    x1 = (int) plugin.pos1.x;
                                }
                                HashMap<Integer, HashMap<Integer, Position>> line;
                                line = wtLine(x1, y1, z1, block.getLevel(), false);
                                plugin.line = line;
                                plugin.status++;
                                wtBlock(line, player.getLevel());
                                player.sendTitle(TextFormat.GOLD + "请点击木牌作为游戏开始按钮");
                                player.setSubtitle(TextFormat.RED + "方框的第二点设置 x=" + event.getBlock().x + " y=" + event.getBlock().y + " z=" + event.getBlock().z);
                            } else {
                                plugin.status = 0;
                                player.sendMessage(TextFormat.RED + "请确认设置是在同一地图的 4(宽) * 5(高) 的方框");
                            }
                        } else {
                            plugin.status = 0;
                            player.sendMessage(TextFormat.RED + "请确认设置是在同一地图的 4(宽) * 5(高) 的方框");
                        }
                    } else {
                        plugin.status = 0;
                        player.sendMessage(TextFormat.RED + "请确认设置是在同一地图的 4(宽) * 5(高) 的方框");
                    }
                    break;
                case 2:
                    if (block.getId() == 63 || block.getId() == 68 || block.getId() == 323) {
                        plugin.start = block.getLocation();
                        BlockEntity blockEntity = block.getLevel().getBlockEntity(block.getLocation());
                        BlockEntitySign sign = new BlockEntitySign(blockEntity.chunk, blockEntity.namedTag);
                        sign.setText(TextFormat.GOLD + "§lDontTapTheWhiteTile", TextFormat.GREEN + "游戏状态: 点击开始", TextFormat.AQUA + "当前玩家: 无", TextFormat.WHITE + "作者:WetABQ");
                        plugin.status++;
                        player.sendTitle(TextFormat.GOLD + "请点击木牌作为排行榜");
                    } else {
                        player.sendMessage(TextFormat.RED + "请点击木牌!");
                    }
                    break;
                case 3:
                    if (block.getId() == 63 || block.getId() == 68 || block.getId() == 323) {
                        plugin.top = block.getLocation();
                        BlockEntity blockEntity = block.getLevel().getBlockEntity(block.getLocation());
                        BlockEntitySign sign = new BlockEntitySign(blockEntity.chunk, blockEntity.namedTag);
                        sign.setText(TextFormat.GOLD + "§lTop1", TextFormat.YELLOW + "无", TextFormat.AQUA + "999", TextFormat.WHITE + "作者:WetABQ");
                        plugin.status++;
                        plugin.set = "";
                        plugin.saveData();
                        player.sendTitle(TextFormat.GOLD + "别踩白块儿 全部设置完成 , 可以进行游戏了!");
                    } else {
                        player.sendMessage(TextFormat.RED + "请点击木牌!");
                    }
                    break;
            }
            event.setCancelled();
        } else if (plugin.player != null) {
            if(plugin.status == 0){
                return;
            }
            
            if (player.getName().equals(plugin.player)) {
                if (plugin.line.get(0).get(0).level.getFolderName().equals(block.getLevel().getFolderName()) && plugin.line.get(0).get(0).x <= block.x && plugin.line.get(0).get(0).y <= block.y && plugin.line.get(0).get(0).z <= block.z && plugin.line.get(4).get(3).x >= block.x && plugin.line.get(4).get(3).y >= block.y && plugin.line.get(4).get(3).z >= block.z) {
                    if (block.y == plugin.line.get(0).get(0).y) {
                        if (block.getId() == 35 && block.getDamage() == 0) {
                            for(Map.Entry<Integer, HashMap<Integer, Position>> entry:plugin.line.entrySet()){
                                for(Map.Entry<Integer,Position> entry1:entry.getValue().entrySet()){
                                    player.getLevel().setBlock(entry1.getValue(),Block.get(35,14));
                                }
                            }
                            plugin.player = null;
                            plugin.game = 0;
                            plugin.StartTime = 0;
                            player.sendTitle("你失败了!");
                            BlockEntity blockEntity = plugin.line.get(0).get(0).getLevel().getBlockEntity(plugin.start);
                            BlockEntitySign sign = new BlockEntitySign(blockEntity.chunk, blockEntity.namedTag);
                            sign.setText(TextFormat.GOLD + "§lDontTapTheWhiteTile", TextFormat.GREEN + "游戏状态: 点击开始", TextFormat.AQUA + "当前玩家: 无", TextFormat.WHITE + "作者:WetABQ");
                        } else if (block.getId() == 35 && block.getDamage() == 15) {
                            if(plugin.game == 49){
                                long nanotime = System.nanoTime() - plugin.StartTime;
                                BigDecimal b = new BigDecimal(((double) nanotime / 1000000000));
                                double gametime = b.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
                                player.sendTitle(TextFormat.GREEN+"恭喜通关,成绩"+(float)gametime);
                                if(gametime < plugin.first){
                                    BlockEntity blockEntity = plugin.top.getLevel().getBlockEntity(plugin.top);
                                    BlockEntitySign sign = new BlockEntitySign(blockEntity.chunk, blockEntity.namedTag);
                                    sign.setText(TextFormat.GOLD + "§lTop1", TextFormat.YELLOW + player.getName(), TextFormat.AQUA + ""+(float)gametime, TextFormat.WHITE + "作者:WetABQ");
                                    plugin.getServer().broadcastMessage(TextFormat.GOLD+"恭喜 "+player.getName()+" 获得了别踩白块第一名");
                                }
                                plugin.player = null;
                                BlockEntity blockEntity = plugin.line.get(0).get(0).getLevel().getBlockEntity(plugin.start);
                                BlockEntitySign sign = new BlockEntitySign(blockEntity.chunk, blockEntity.namedTag);
                                sign.setText(TextFormat.GOLD + "§lDontTapTheWhiteTile", TextFormat.GREEN + "游戏状态: 点击开始", TextFormat.AQUA + "当前玩家: 无", TextFormat.WHITE + "作者:WetABQ");
                                ChangeBlock(plugin.game,player.getLevel());
                                plugin.game = 0;
                                CheckGameTimeout.time = 0;
                            }else{
                                CheckGameTimeout.time = 0;
                                ChangeBlock(plugin.game,player.getLevel());
                                plugin.game++;
                            }
                        }
                    }
                } else if(block.getX() == plugin.start.x && block.getY() == plugin.start.y && block.getZ() == plugin.start.z && block.getLevel().getFolderName().equals(plugin.start.level.getFolderName())) {
                    //游戏开始后点击牌子 取消游戏
                    player.sendMessage(TextFormat.RED+"* 你取消了游戏");
                    plugin.player = null;
                    plugin.game = 0;
                    plugin.StartTime = 0;
                    BlockEntity blockEntity = plugin.line.get(0).get(0).getLevel().getBlockEntity(plugin.start);
                    BlockEntitySign sign = new BlockEntitySign(blockEntity.chunk, blockEntity.namedTag);
                    sign.setText(TextFormat.GOLD + "§lDontTapTheWhiteTile", TextFormat.GREEN + "游戏状态: 点击开始", TextFormat.AQUA + "当前玩家: 无", TextFormat.WHITE + "作者:WetABQ");
                    wtBlock(plugin.line,player.getLevel());
                }
            } else if(block.getX() == plugin.start.x && block.getY() == plugin.start.y && block.getZ() == plugin.start.z && block.getLevel().getFolderName().equals(plugin.start.level.getFolderName())){
                player.sendMessage(TextFormat.YELLOW+"* 已经有人在游戏中 , 你无法开始 !");
            }
        }else if(plugin.status != 0 && block.getX() == plugin.start.x && block.getY() == plugin.start.y && block.getZ() == plugin.start.z && block.getLevel().getFolderName().equals(plugin.start.level.getFolderName())){
            plugin.player = player.getName();
            CheckGameTimeout.time = 0;
            BlockEntity blockEntity = plugin.line.get(0).get(0).getLevel().getBlockEntity(plugin.start);
            BlockEntitySign sign = new BlockEntitySign(blockEntity.chunk, blockEntity.namedTag);
            sign.setText(TextFormat.GOLD + "§lDontTapTheWhiteTile", TextFormat.RED + "游戏状态: 已经开始", TextFormat.AQUA + "当前玩家: "+player.getName(), TextFormat.WHITE + "作者:WetABQ");
            player.sendTitle(TextFormat.YELLOW+"游戏开始!");
            ChangeBlock(0,player.getLevel());
            plugin.game++;
        }
    }

    private static void ChangeBlock(int in,Level level){
        //plugin.getLogger().warning(plugin.color+"");
        if(in == 0){
            for(Map.Entry<Integer, HashMap<Integer, Position>> entry:plugin.line.entrySet()){
                for(Map.Entry<Integer,Position> entry1:entry.getValue().entrySet()){
                    level.setBlock(entry1.getValue(),Block.get(35));
                }
            }
            plugin.StartTime = System.nanoTime();
            for(int i=0;i<5;i++){

                HashMap<Integer,Block> map = new HashMap<>();
                for(int n=0;n<4;n++){
                    map.put(n,Block.get(35));
                    //plugin.getLogger().notice(i+"|"+n+"|"+map);
                }
                plugin.color.put(i,map);
                int r = r(0,3);
                HashMap<Integer,Block> map2 = plugin.color.get(i);
                map2.put(r,Block.get(35,15));
                plugin.color.put(i,map2);
                //plugin.getLogger().error(plugin.color+"");
            }
            /*for(int i=0;i<5;i++){
                for(int n=0;n<4;n++){
                    Vector3 pos = new Vector3(plugin.line.get(i).get(n).x,plugin.line.get(i).get(n).y,plugin.line.get(i).get(n).z);
                    level.setBlock(pos,plugin.color.get(i).get(n));
                }
            }*/
            cb(level);
        }else if(in <= 45){
            for(int i=0;i<4;i++){
                plugin.color.put(i,plugin.color.get(i+1));
            }
            HashMap<Integer,Block> map = new HashMap<>();
            map.put(0,Block.get(35));
            plugin.color.put(4,map);
            HashMap<Integer,Block> map2 = plugin.color.get(4);
            map2.put(1,Block.get(35));
            plugin.color.put(4,map2);
            HashMap<Integer,Block> map3 = plugin.color.get(4);
            map3.put(2,Block.get(35));
            plugin.color.put(4,map3);
            HashMap<Integer,Block> map4 = plugin.color.get(4);
            map4.put(3,Block.get(35));
            plugin.color.put(4,map4);
            HashMap<Integer,Block> map5 = plugin.color.get(4);
            map5.put(r(0,3),Block.get(35,15));
            plugin.color.put(4,map5);
            cb(level);
        }else{
            for(int i=0;i<50-in;i++){
                plugin.color.put(i,plugin.color.get(i+1));
            }
            for(int i=4;i>49-in;i--){
                HashMap<Integer,Block> map = plugin.color.get(i);
                map.put(0,Block.get(35,5));
                plugin.color.put(i,map);
                HashMap<Integer,Block> map2 = plugin.color.get(i);
                map2.put(1,Block.get(35,5));
                plugin.color.put(i,map2);
                HashMap<Integer,Block> map3 = plugin.color.get(i);
                map3.put(2,Block.get(35,5));
                plugin.color.put(i,map3);
                HashMap<Integer,Block> map4 = plugin.color.get(i);
                map4.put(3,Block.get(35,5));
                plugin.color.put(i,map4);
            }
            cb(level);
        }
    }

    private static void cb(Level level){
        for(Map.Entry<Integer,HashMap<Integer,Block>> entry:plugin.color.entrySet()){
            for(Map.Entry<Integer,Block> entry1:entry.getValue().entrySet()){
                Position p = plugin.line.get(entry.getKey()).get(entry1.getKey());
                Vector3 pos = new Vector3(p.x,p.y,p.z);
                level.setBlock(pos,entry1.getValue());
            }
        }
    }


    public static void wtBlock(HashMap<Integer, HashMap<Integer, Position>> line, Level level) {
        int c = 1;
        for(Map.Entry<Integer, HashMap<Integer, Position>> entry:line.entrySet()){
            for(Map.Entry<Integer,Position> entry1:entry.getValue().entrySet()){
                if(c % 2 == 0){
                    level.setBlock(entry1.getValue(),Block.get(35));
                }else{
                    level.setBlock(entry1.getValue(),Block.get(35,15));
                }
                c++;
            }
            c++;
        }
        /*for (int i = 0; i < 5; i++) {
            for (int n = 0; n < 4; n++) {
                Vector3 pos = new Vector3(line.get(i).get(n).x, line.get(i).get(n).y, line.get(i).get(n).z);
                if (c % 2 == 0) {
                    line.get(i).get(n).getLevel().setBlock(pos, Block.get(35));
                } else {
                    line.get(i).get(n).getLevel().setBlock(pos, Block.get(35, 15));
                }
                c++;
            }
            c++;
        }*/
    }

    private HashMap<Integer, HashMap<Integer, Position>> wtLine(int x1, int y1, int z1, Level level, Boolean type) {
        HashMap<Integer, HashMap<Integer, Position>> line = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            HashMap<Integer, Position> in = new HashMap<>();
            for (int n = 0; n < 4; n++) {
                if (type) {
                    in.put(n, new Position(x1, y1 + i, z1 + n, level));
                } else {
                    in.put(n, new Position(x1 + n, y1 + i, z1, level));
                }
                //plugin.getLogger().notice(i+"|"+n+"|"+in);
            }
            line.put(i, in);
        }
        return line;
    }

    private static int r(int min,int max){
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }


}
