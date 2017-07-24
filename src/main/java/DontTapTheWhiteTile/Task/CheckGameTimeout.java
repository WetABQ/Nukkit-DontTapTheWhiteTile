package DontTapTheWhiteTile.Task;

import DontTapTheWhiteTile.DontTapTheWhiteTile;
import cn.nukkit.scheduler.PluginTask;

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
public class CheckGameTimeout extends PluginTask<DontTapTheWhiteTile> {

    public CheckGameTimeout(DontTapTheWhiteTile owner) {
        super(owner);
    }

    public static int time;

    public void onRun(int t){
        if(DontTapTheWhiteTile.getPlugin().player != null) {
            if(time >= 200){
                this.getOwner().GameTimeout();
                time=0;
            }else{
                time++;
            }
        }
    }

}
