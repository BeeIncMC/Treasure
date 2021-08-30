package us.pixelgames.pixeltreasure.data;

import us.pixelgames.pixeltreasure.items.Reward;

import java.util.HashMap;

public class PlayerData {
    private String uuid;
    private HashMap<Reward, Integer> rewardMap;

    public PlayerData(String uuid, HashMap<Reward, Integer> rewardMap) {
        this.uuid = uuid;
        this.rewardMap = rewardMap;
    }

    public void addReward(Reward reward, int count) {
        if(rewardMap.containsKey(reward)) {
            rewardMap.replace(reward, count + rewardMap.get(reward));
        } else {
            rewardMap.put(reward, count);
        }
    }

    public boolean consumeReward(Reward reward, int count) {
        if(rewardMap.containsKey(reward)) {
            if(rewardMap.get(reward) > (rewardMap.get(reward) - count)) {
                rewardMap.replace(reward, count - rewardMap.get(reward));
                return true;
            }
        }
        return false;
    }
}