import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * DESCRIPTION HERE
 *
 * @author malik2sx <EMAIL@dukes.jmu.edu>
 *
 *         This complies with JMU honor code
 */
public class QuadTreeCollisionSet<E extends Sprite> implements CollisionSet<E> {

  protected QuadTreeNode<E> root;
  protected int size;

  // creates new quadtree node for root
  public QuadTreeCollisionSet() {
    this.size = 0;
    root = new QuadTreeNode<E>(
        new Box(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE), 0);
  }

  // recurse over list of sprites and add recursively
  @Override
  public void addAll(List<E> sprites) {
    for (E o : sprites) {
      root.add(o);
      size++;
    }
  }

  @Override
  public void add(E sprite) {
    size++;
    root.add(sprite);
  }

  @Override
  public boolean remove(E sprite) {
    return root.remove(sprite);
  }

  @Override
  public void clear() { // xs and ys
    root = new QuadTreeNode<E>(
        new Box(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE), 0);
  }

  @Override
  public Set<E> findIntersecting(Box box) {
    return root.findIntersecting(box);
  }

  @Override
  public Iterator<E> iterator() {
    return new QTreeSpriteIterator();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean contains(E sprite) {
    return root.contains(sprite);
  }

  /**
   * Hint: You will need to implement this iterator to do the remove. Suggestion: use the allItems()
   * method of the QuadTreeNode to create a java Collections object of all items and use its
   * iterator. (Store it as theIterator).
   *
   * <p>
   * The tricky part is how to handle remove. Calling remove on theIterator only removes it from the
   * Collection iterator you made, but does not actually remove it from the tree.
   * </p>
   *
   * <p>
   * To handle remove, then, you'll need to store the last item you returned each time next() is
   * called, so that when remove() is called you can use the QuadTreeNode.remove(lastItem) method to
   * actually remove the item from the QuadTree.
   * </p>
   */
  private class QTreeSpriteIterator implements Iterator<E> {

    Iterator<E> theIterator;
    E lastItem = null;

    public QTreeSpriteIterator() {
      theIterator = root.allItems().iterator();
    }

    @Override
    public boolean hasNext() {
      return theIterator.hasNext();
    }

    @Override
    public E next() {
      lastItem = theIterator.next();
      return lastItem;
    }

    @Override
    public void remove() {
      // remove the last item from the tree
      root.remove(lastItem);
      // remove the last item from the iterator
      theIterator.remove();
      size--;
    }

  }

}
