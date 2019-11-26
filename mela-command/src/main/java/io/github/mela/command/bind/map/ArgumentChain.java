package io.github.mela.command.bind.map;

import io.github.mela.command.core.CommandGroup;

import javax.annotation.Nonnull;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ArgumentChain implements Iterator<String> {

  private ArgumentChain lastSubChain;

  private boolean invalid;
  private Runnable resetAction;

  private final String raw;
  private final boolean subChainPermission;
  private final List<String> arguments;
  private final IndexPointer index;

  public ArgumentChain(@Nonnull String arguments) {
    this(arguments, new ArrayList<>(Arrays.asList(CommandGroup.SPLIT_PATTERN.split(checkNotNull(arguments)))),
        new IndexPointer(), true);
  }

  private ArgumentChain(@Nonnull String raw, @Nonnull List<String> arguments, IndexPointer index, boolean subChainPermission) {
    this.raw = raw;
    this.subChainPermission = subChainPermission;
    this.arguments = arguments;
    this.index = index;
    this.resetAction = () -> {};
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
    addResetAction(() -> --index.value);
    return arguments.get(index.value++);
  }

  @Override
  public void remove() {
    checkValidity();
    back();
    String removedValue = arguments.remove(index.value);
    addResetAction(() -> arguments.set(index.value, removedValue));
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
    resetAction.run();
    resetAction = () -> {};
  }

  private void checkIndex() {
    if (index.value >= arguments.size()) {
      throw new NoSuchElementException("No argument present for index " + index);
    }
  }

  private void back() {
    checkValidity();
    checkState(index.value > 0, "iterator was not advanced");
    addResetAction(() -> ++index.value);
    --index.value;
  }

  private void checkValidity() {
    checkState(!invalid, "This chain was invalidated and cannot be used anymore.");
  }

  private void invalidate() {
    invalid = true;
  }

  private void addResetAction(Runnable action) {
    resetAction = () -> {
      action.run();
      resetAction.run();
    };
  }

  private static final class IndexPointer {
    int value = 0;
  }

  @Override
  public String toString() {
    return "[" + String.join(", ", arguments) + "]";
  }

  public ArgumentChain subChain() {
    checkState(subChainPermission, "You can't create sub chains of this argument chain");
    checkValidity();
    if (lastSubChain != null) {
      lastSubChain.invalidate();
    }
    lastSubChain = new ArgumentChain(raw, arguments, index, false);
    return lastSubChain;
  }

}
