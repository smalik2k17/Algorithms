
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author malik2sx <EMAIL@dukes.jmu.edu>
 *
 *         This complies with JMU honor code
 */
public class QuadTreeNode<E extends BoxIntersectable> {

  protected Box region;

  protected QuadTreeNode<E> northEast;
  protected QuadTreeNode<E> northWest;
  protected QuadTreeNode<E> southEast;
  protected QuadTreeNode<E> southWest;

  protected int depth;

  public static final int MAX_SPRITES = 20;
  public static final int MAX_DEPTH = 20;

  // The sprites stored
  HashSet<E> sprites;

  public QuadTreeNode(Box bbox, int depth) {
    sprites = new HashSet<>();
    northEast = northWest = southEast = southWest = null;
    this.region = bbox;
    this.depth = depth;
  }

  /**
   * Add the item to this quadtree node.
   * 
   * <p>
   * If this node is an internal node, recursively add the item to any children of this node whose
   * regions intersect the item's box.
   * </p>
   * 
   * <p>
   * If this is a leaf node, store the item in a set that is contained within the node. If the
   * number of items stored within the node exceeds some threshold (e.g. MAX_SPRITES in
   * QuadTreeSpriteNode), but is not at some maximum depth (e.g. MAX_DEPTH in QuadTreeSpriteNode),
   * then split this node by dividing its region evenly among four new child nodes and adding all of
   * its nodes to the children.
   * </p>
   * 
   * @param item The item to add.
   */
  // if its a leaf, greater than max depth
  public void add(E item) {
    if (item == null) {
      return;
    }
    if (isLeaf()) {
      sprites.add(item);
      if ((sprites.size() > MAX_SPRITES) && (depth < MAX_DEPTH)) {
        splitNode();
        for (E sprite : sprites) {
          if (sprite.boundingBox().intersects(northEast.region)) {
            northEast.sprites.add(sprite);
          }
          if (sprite.boundingBox().intersects(southEast.region)) {
            southEast.sprites.add(sprite);
          }
          if (sprite.boundingBox().intersects(southWest.region)) {
            southWest.sprites.add(sprite);
          }
          if (sprite.boundingBox().intersects(northWest.region)) {
            northWest.sprites.add(sprite);
          }
        }
        this.sprites = null;
      }

    } else if (!isLeaf()) { // recursing down to leaf nodes
      if (northEast.region.intersects(item.boundingBox())) {
        northEast.add(item);
      }
      if (southEast.region.intersects(item.boundingBox())) {
        southEast.add(item);
      }
      if (southWest.region.intersects(item.boundingBox())) {
        southWest.add(item);
      }
      if (northWest.region.intersects(item.boundingBox())) {
        northWest.add(item);
      }
    }
  }

  /**
   * Find all items intersecting a given bounding box.
   * 
   * <p>
   * Hints: Use a recursive algorithm with a helper. Create a HashSet to store the items and pass it
   * to the recursive calls.
   * </p>
   * 
   * <p>
   * Recurse on any child node whose region intersects the box. At a leaf node test each item stored
   * in the node to see if it intersects the box. If it does, add it to the HashSet that is
   * returned.
   * </p>
   * 
   * @param box The box to intersect objects with.
   * @return A set containing all of the items.
   */

  public Set<E> findIntersecting(Box box) {
    Set<E> intersecting = new HashSet<>();
    findIntersecting(box, intersecting);

    return intersecting;
  }

  // helper method to findIntersecting(Box box)
  private void findIntersecting(Box box, Set<E> intersecting) {
    if (!isLeaf()) {
      // if not a leaf, recursively find intersecting in child nodes
      northWest.findIntersecting(box, intersecting);
      northEast.findIntersecting(box, intersecting);
      southWest.findIntersecting(box, intersecting);
      southEast.findIntersecting(box, intersecting);
    } else {
      // if this is a leaf, find the intersecting items in the Set
      for (E item : sprites) {
        if (box.intersects(item.boundingBox())) {
          intersecting.add(item);
        }
      }
    }
  }

  /**
   * Remove the item from this quadtree
   * 
   * <p>
   * Hints: Similar to find. Recursively find all leaf nodes that intersect the item's bounding box
   * and remove the item from the leaf node, if it exists.
   * </p>
   * 
   * @param item The item to remove.
   * @return
   */
  public boolean remove(E sprite) {
    if (sprite == null) {
      return false;
    }
    if (isLeaf()) {
      sprites.remove(sprite);
    } else {
      if (sprite.boundingBox().intersects(northEast.region)) {
        northEast.remove(sprite);
      }
      if (sprite.boundingBox().intersects(southEast.region)) {
        southEast.remove(sprite);
      }
      if (sprite.boundingBox().intersects(southWest.region)) {
        southWest.remove(sprite);
      }
      if (sprite.boundingBox().intersects(northWest.region)) {
        northWest.remove(sprite);
      }
    } // if you want to remove a sprite but cant find it return false
    return false;
  }

  /**
   * Return a set containing all of the items.
   * 
   * <p>
   * Hints: Use a recursive strategy. Create a set and pass it to a helper. The helper will recurse
   * on all children to get down to leaf nodes. At a leaf node call addAll on the HashSet to add all
   * items stored in that leaf.
   * </p>
   * 
   * @return A set containing all of the items in this quadtree.
   */
  public Set<E> allItems() {
    Set<E> set = new HashSet<>();
    return allItemsHelp(set);
  }

  public Set<E> allItemsHelp(Set<E> set) {
    if (isLeaf()) {
      set.addAll(sprites);
      return set;
    } else {
      set = northEast.allItemsHelp(set);
      set = southEast.allItemsHelp(set);
      set = northWest.allItemsHelp(set);
      set = southWest.allItemsHelp(set);
      return set;
    }
  }

  private void splitNode() {
    // It may be helpful to implement this splitNode() method
    // to handle the actual splitting of a node.
    // northwest starts from 0-15 mid returns midpoint
    // what interval is for quadrants
    northEast = new QuadTreeNode<>(
        new Box(region.xint.mid(), region.xint.max, region.yint.min, region.yint.mid()), depth + 1);
    southEast = new QuadTreeNode<>(
        new Box(region.xint.mid(), region.xint.max, region.yint.mid(), region.yint.max), depth + 1);
    northWest = new QuadTreeNode<>(
        new Box(region.xint.min, region.xint.mid(), region.yint.min, region.yint.mid()), depth + 1);
    southWest = new QuadTreeNode<>(
        new Box(region.xint.min, region.xint.mid(), region.yint.mid(), region.yint.max), depth + 1);
  }

  /**
   * Determine if this node is a leaf or not.
   * 
   * @return True if this is a leaf, false otherwise.
   */
  public boolean isLeaf() {
    // if this is a leaf node, northEast = northWest = southEast = southWest
    // = null. We check one here
    return (northWest == null);
  }

  /**
   * Hints:
   * 
   * <p>
   * Recursively test if the item is contained in the node. Similar to find.
   * </p>
   * 
   * @param item The item to test for.
   * @return True if this tree contains this item.
   */
  public boolean contains(E sprite) {
    if (!isLeaf()) {
      // if this is not a leaf node, recursively find the item in child
      // nodes
      // return if any one of the child nodes has the item
      if (northWest.contains(sprite)) {
        return true;
      }
      if (northEast.contains(sprite)) {
        return true;
      }
      if (southWest.contains(sprite)) {
        return true;
      }
      if (southEast.contains(sprite)) {
        return true;
      }
    } else {
      // if this is a leaf node, check if the item is available in the Set
      return sprites.contains(sprite);
    }
    return false;
  }

  /**
   * 
   * @return The box representing the region this quadtree node covers.
   */
  public Box getBoxRegion() {
    return region;
  }

}
