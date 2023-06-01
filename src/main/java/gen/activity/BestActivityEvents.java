package gen.activity;

import emu.grasscutter.Grasscutter;

import com.google.protobuf.InvalidProtocolBufferException;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.game.ReceivePacketEvent;


import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq;

import gen.BestActivityCore;

import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.server.event.player.PlayerJoinEvent;
import emu.grasscutter.server.event.player.PlayerQuitEvent;
import emu.grasscutter.server.event.player.PlayerMoveEvent;

public final class BestActivityEvents {
	public static void packets(ReceivePacketEvent event) {
		switch (event.getPacketId()) {
	    case 875:
	        try {
	            GadgetInteractReq req = GadgetInteractReq.parseFrom(event.getPacketData());
	            Player player = event.getGameSession().getPlayer();
	            MPActivity mpActivity = BestActivityCore.getMPActivity(player.getUid());
	            if (mpActivity.getButton(1) == req.getGadgetEntityId()) {
	                mpActivity.upBox(player);
	            }
	        } catch (InvalidProtocolBufferException e) {

	        }
	        
	        break;
	    case 855:
	        try {
	            SelectWorktopOptionReq req = SelectWorktopOptionReq.parseFrom(event.getPacketData());
	            Grasscutter.getLogger().info("----------- 855");
	            Grasscutter.getLogger().info(Integer.toString(req.getGadgetEntityId()));
	        } catch (InvalidProtocolBufferException e) {

	        }
	        
	        break;
	    case 5626:
	        Player player = event.getGameSession().getPlayer();
            MPActivity mpActivity = BestActivityCore.getMPActivity(player.getUid());
	        
            int playerScene = player.getSceneId();
            
            if (playerScene != mpActivity.getLastScene() && playerScene == 3) mpActivity.scene3(player);
	        
            mpActivity.setLastScene(playerScene);
            
	        break;
	    default:

	        break;
	   }

			
			
			//if (event.getPacketId() != 55 && event.getPacketId() != 310 && event.getPacketId() != 2375 && event.getPacketId() != 16) Grasscutter.getLogger().info(Integer.toString(event.getPacketId()));
		}

    public static void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        MPActivity mpActivity = new MPActivity();
        
        mpActivity.setLastScene(player.getSceneId());
        
        mpActivity.create(player);
        
        if (player.getSceneId() == 3) {
        	mpActivity.scene3(player);
        };
        
        BestActivityCore.addMPActivity(player.getUid(), mpActivity);
    }
    
    public static void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        BestActivityCore.removeMPActivity(player.getUid());
        
        
    }
    
    public static void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        
        if (player.getSceneId() == 3) {
	        MPActivity mpActivity = BestActivityCore.getMPActivity(player.getUid());
	        	        
	        switch (mpActivity.getState()) {
	        case 1:        	
	        	if (mpActivity.getFinish().getPosition().computeDistance(player.getPosition()) <= 1.2)
	        	{   
	        		
	            	mpActivity.downBox(player);
	            }
	        	
	        	
		    	break;
	        case 2:
		    	break;
		    	
		    default:

		        break;
		   }
	        
        }
        
    }
}
