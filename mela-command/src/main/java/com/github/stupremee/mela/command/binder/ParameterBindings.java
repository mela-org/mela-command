package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.provider.ArgumentProvider;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// @ImplementedBy
public interface ParameterBindings {

  <T> ArgumentProvider<T> getProvider(ParameterBindingKey<T> key);

}
