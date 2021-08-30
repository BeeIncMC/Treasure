package us.pixelgames.pixeltreasure.tasks.data;

import org.bukkit.entity.Player;
import us.pixelgames.pixeltreasure.data.Data;

public class LoadPlayerTask implements Runnable {
    private final Player player;

    public LoadPlayerTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if(!Data.playerData.containsKey(player)) {

        }
    }
}