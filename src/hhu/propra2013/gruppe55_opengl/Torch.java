package hhu.propra2013.gruppe55_opengl;

public class Torch extends DungeonObject {

	public Torch(double x, double y, String[] triggerKeys) {
		super(x, y);
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.torch, false, true, true);
		state[1]	=	new State(Data_Textures.torch_lit, false, true, true);
	}
}