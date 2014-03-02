package lisp;


public abstract class GameObjectInRoom extends GameObjectABC {
	
	
	protected GameRoom room;
	
	GameObjectInRoom(GameRoom room){
		this.room = room;
	}
	@Override
	public void destroy() {
		room.removeObject(this);

	}

}
