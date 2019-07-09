package com.github.stupremee.mela.command.bind.tree;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class RecursiveCommandTree<T extends Group> implements CommandTree {

  protected final T root;
  protected T currentNode;

  protected RecursiveCommandTree(T root) {
    this.root = root;
    this.currentNode = root;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void stepDown(@Nonnull Group child) {
    checkArgument(currentNode.getChildren().contains(child), "Provided group is not a child of the current node");
    currentNode = (T) child;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void stepUp() {
    checkState(!isAtRoot(), "Tree is at root node");
    currentNode = (T) currentNode.getParent();
  }

  @Override
  public boolean isAtRoot() {
    return currentNode == root;
  }

  @Nonnull
  @Override
  public Group getCurrent() {
    return currentNode;
  }
}
