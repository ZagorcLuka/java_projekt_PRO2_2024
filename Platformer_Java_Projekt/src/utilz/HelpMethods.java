package utilz;

import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height) {
		if (!IsSolid(x, y))
			if (!IsSolid(x + width, y + height))
				if (!IsSolid(x + width, y))
					if (!IsSolid(x, y + height))
						return true; //ce se noben rob hitboxa ne dotika mej vrne true
		return false;                //tj. se lahko premaknemo v to smer
	}

	private static boolean IsSolid(float x, float y) {
		
		if (x < 0 || x >= Game.GAME_WIDTH) //meje 
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT-28)
			return true;
		else
			return false;
		
	}
}