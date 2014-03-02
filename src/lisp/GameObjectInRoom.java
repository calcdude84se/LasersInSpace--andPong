package lisp;


public abstract class GameObjectInRoom implements GameObject {
	
	
	protected GameRoom room;
	
	GameObjectInRoom(GameRoom room){
		this.room = room;
	}
	@Override
	public void destroy() {
		room.removeObject(this);

	}

}
