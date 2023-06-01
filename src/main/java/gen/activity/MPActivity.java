package gen.activity;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;


import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.world.Position;

public final class MPActivity {
	
	private int lastScene;
	
	private int state = 0;
	
	private GameEntity Box;
	private GameEntity BoxAct;
	private GameEntity Finish;
	
	 public void create(Player targetPlayer) {
			
	        Box = createGadget(targetPlayer,targetPlayer.getScene(), 70310099, new Position(-599,204.75f,145), new Position(0,41,0));
	        BoxAct = createGadget(targetPlayer,targetPlayer.getScene(), 71700629, new Position(-599,204.5f,145), new Position(0,0,0));
	        
	        Finish = createGadget(targetPlayer,targetPlayer.getScene(), 70360025, new Position(-575,204.75f,142), new Position(0,0,0));
	        removeGadget(Finish);
	        
    }
    
	 public int getButton(int num) {
		 switch (num) {
		    case 1:
		    	return BoxAct.getId();
		    default:
		    	return 0;
		}
	 }
	 
	 public void upBox(Player targetPlayer) {
		 removeGadget(Box);
		 removeGadget(BoxAct);
		 targetPlayer.getScene().addEntity(Finish);
        
        state = 1;        
	 }
	 
	 public void downBox(Player targetPlayer) {
		 targetPlayer.getScene().addEntity(Box);
		 targetPlayer.getScene().addEntity(BoxAct);
		 removeGadget(Finish);
		 targetPlayer.getInventory().addItem(201, 5);
		 
		 state = 2;
	 }
	 
	 public void scene3(Player targetPlayer) {
		 targetPlayer.getScene().addEntity(Box);
		 targetPlayer.getScene().addEntity(BoxAct);
		 state = 2;
	 }
	 
	 public int getLastScene() {
		 return lastScene;
	 }
	 
	 public void setLastScene(int sceneId) {
		 lastScene = sceneId;
	 }
	 
	 public int getState() {
		 return state;
	 }
	 
	 public void setState(int num) {
		 state = num;
	 }
	 
	 public GameEntity getFinish() {
		 return Finish;
	 }
	 
	 public GameEntity getBox() {
		 return Box;
	 }
	 
    private static GameEntity createGadget (Player player, Scene scene, int id, Position pos, Position rot){
    	
    	GameEntity entity = new EntityVehicle(scene, player.getSession().getPlayer(), id, 0, pos, rot);
        return entity;
    
    }
    
	private void removeGadget(GameEntity entity){
		entity.getScene().killEntity(entity);
    }
}
