package com.github.stupremee.mela.command.discord;

import com.github.stupremee.mela.command.providers.ConsumingProvider;
import com.github.stupremee.mela.command.providers.EmptySuggestionProvider;

import java.util.regex.Pattern;

public interface UserProvider<T> extends EmptySuggestionProvider<T>, ConsumingProvider<T> {

  Pattern MENTION = Pattern.compile("<@!?([0-9]+)>");

}
