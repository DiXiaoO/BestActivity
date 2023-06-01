package gen;

import java.util.HashMap;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.server.event.EventHandler;
import emu.grasscutter.server.event.HandlerPriority;
import emu.grasscutter.server.event.game.ReceivePacketEvent;
import emu.grasscutter.server.event.player.PlayerJoinEvent;
import emu.grasscutter.server.event.player.PlayerQuitEvent;
import gen.activity.BestActivityEvents;
import gen.activity.MPActivity;
import emu.grasscutter.server.event.player.PlayerMoveEvent;

public class BestActivityCore extends Plugin {
	
	private Map<Integer, MPActivity> MPActivityMap;
    private static BestActivityCore instance;
    
    @Override
    public void onLoad() {
        Grasscutter.getLogger().info("[BestActivityCore] Loading...");
        instance = this;
        MPActivityMap = new HashMap<>();
        //this.act = new BestActivity();
        Grasscutter.getLogger().info("[BestActivityCore] Loaded!");
    }

	@Override
    public void onEnable() {
		
		new EventHandler<>(ReceivePacketEvent.class).priority(HandlerPriority.LOW).listener(BestActivityEvents::packets).register(instance);
		
		new EventHandler<>(PlayerJoinEvent.class).priority(HandlerPriority.LOW).listener(BestActivityEvents::onJoin).register(instance);
		
		new EventHandler<>(PlayerQuitEvent.class).priority(HandlerPriority.LOW).listener(BestActivityEvents::onQuit).register(instance);
		
		new EventHandler<>(PlayerMoveEvent.class).priority(HandlerPriority.LOW).listener(BestActivityEvents::onMove).register(instance);
		
    }

    @Override
    public void onDisable() {
        CommandMap.getInstance().unregisterCommand("bestactivity");
    }

    public static BestActivityCore getInstance() {
        return instance;
    }
    
    public static MPActivity getMPActivity(int uid) {
        return instance.MPActivityMap.get(uid);
    }
    
    public static MPActivity addMPActivity(int uid, MPActivity mpactivity) {
        return instance.MPActivityMap.put(uid, mpactivity);
    }
    
    public static MPActivity removeMPActivity(int uid) {
        return instance.MPActivityMap.remove(uid);
    }
}
