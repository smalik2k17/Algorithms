
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * DESCRIPTION HERE
 *
 @author malik2sx <EMAIL@dukes.jmu.edu>
 *
 *         This complies with JMU honor code
 */
public class ArrayCollisionSet<E extends BoxIntersectable> implements CollisionSet<E> {

  // Store the sprites in an array list
  ArrayList<E> items;

  public ArrayCollisionSet() {
    items = new ArrayList<E>();
  }

  @Override
  public void addAll(List<E> items) {
    this.items.addAll(items);
  }

  @Override
  public void add(E items) {
    this.items.add(items);
  }

  @Override
  public boolean remove(E items) {
    return this.items.remove(items);
  }

  @Override
  public void clear() {
    items.clear();
  }

  // create new set, instantiate iterate over all items in arraylist to check
  // if
  // intersecting if so add to hashmap
  // return set
  @Override
  public Set<E> findIntersecting(Box box) {
    Set<E> set = new HashSet<>();
    // iterate thru arraylist to check sprites
    for (E e : items) {
      if (box.intersects(e.boundingBox())) {
        set.add(e);
      }
    }
    return set;
  }

  @Override
  public boolean contains(E item) {
    return items.contains(item);

  }

  @Override
  public Iterator<E> iterator() {
    return items.iterator();

  }

  @Override
  public int size() {
    return items.size();
  }

}
