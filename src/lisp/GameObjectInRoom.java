package lisp;


public abstract class GameObjectInRoom extends GameObjectABC {
	
	
	public GameRoom room;
	
	public GameObjectInRoom(GameRoom room){
		this.room = room;
	}
	@Override
	public void destroy() {
		room.removeObject(this);

	}

}
