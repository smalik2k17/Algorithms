import java.util.ArrayList;

public class ArrayCollisionSetTest extends AbstractCollisionSetTest {

	@Override
	public CollisionSet<Sprite> newCollisionSet() {
		return new ArrayCollisionSet<Sprite>();
	}

	@Override
	public ArrayList<Sprite> getItemsIn(CollisionSet<Sprite> spriteSet) {
		@SuppressWarnings("unchecked")
		ArrayCollisionSet<Sprite> acSet = (ArrayCollisionSet<Sprite>) spriteSet;
		ArrayList<Sprite> items = new ArrayList<>();
		items.addAll(acSet.items);
		return items;
	}

}
