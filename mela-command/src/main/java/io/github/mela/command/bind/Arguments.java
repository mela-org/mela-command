package io.github.mela.command.bind;

import io.github.mela.command.core.CommandGroup;

import javax.annotation.Nonnull;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Arguments implements Iterator<String> {

  private Arguments lastSubChain;

  private boolean invalid;

  private final String raw;
  private final boolean rootChain;
  private final List<String> arguments;

  private final IndexPointer index;
  private final Deque<Runnable> undoActions;

  public Arguments(@Nonnull String arguments) {
    this(arguments, new ArrayList<>(Arrays.asList(CommandGroup.SPLIT_PATTERN.split(checkNotNull(arguments)))),
        new IndexPointer(), true);
  }

  private Arguments(@Nonnull String raw, @Nonnull List<String> arguments, IndexPointer index, boolean rootChain) {
    this.raw = raw;
    this.rootChain = rootChain;
    this.arguments = arguments;
    this.index = index;
    this.undoActions = new ArrayDeque<>();
    this.invalid = false;
  }

  @Override
  public boolean hasNext() {
    checkValidity();
    return index.value < arguments.size();
  }

  @Nonnull
  @Override
  public String next() {
    checkValidity();
    checkIndex();
    undoActions.addFirst(() -> --index.value);
    return arguments.get(index.value++);
  }

  @Override
  public void remove() {
    checkValidity();
    back();
    String removedValue = arguments.remove(index.value);
    undoActions.addFirst(() -> arguments.set(index.value, removedValue));
  }

  @Nonnull
  public String peek() {
    checkValidity();
    checkIndex();
    return arguments.get(index.value);
  }

  @Nonnull
  public String getRaw() {
    return raw;
  }

  public void reset() {
    checkValidity();
    undoActions.forEach(Runnable::run);
  }

  private void checkIndex() {
    if (index.value >= arguments.size()) {
      throw new NoSuchElementException("No argument present for index " + index);
    }
  }

  private void back() {
    checkValidity();
    checkState(index.value > 0, "iterator was not advanced");
    undoActions.addFirst(() -> ++index.value);
    --index.value;
  }

  private void checkValidity() {
    checkState(!invalid, "This chain was invalidated and cannot be used anymore.");
  }

  private void invalidate() {
    invalid = true;
  }

  private static final class IndexPointer {
    int value = 0;
  }

  @Override
  public String toString() {
    return raw;
  }

  public Arguments subChain() {
    checkState(rootChain, "You can't create sub chains of this argument chain");
    checkValidity();
    if (lastSubChain != null) {
      lastSubChain.invalidate();
    }
    lastSubChain = new Arguments(raw, arguments, index, false);
    return lastSubChain;
  }

}
