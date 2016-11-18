import java.util.ArrayList;

public class QuadTreeCollisionSetTest extends AbstractCollisionSetTest {

	@Override
	public CollisionSet<Sprite> newCollisionSet() {
		return new QuadTreeCollisionSet<Sprite>();
	}

	@Override
	public ArrayList<Sprite> getItemsIn(CollisionSet<Sprite> spriteSet) {
		@SuppressWarnings("unchecked")
		QuadTreeCollisionSet<Sprite> qtSpriteSet = (QuadTreeCollisionSet<Sprite>) spriteSet;
		ArrayList<Sprite> returnList = new ArrayList<>();
		returnList.addAll(qtSpriteSet.root.allItems());
		return returnList;
	}

}
