package com.github.stupremee.mela.command.discord;

import com.github.stupremee.mela.command.provider.ConsumingProvider;
import com.github.stupremee.mela.command.provider.EmptySuggestionProvider;

import java.util.regex.Pattern;

public interface TextChannelProvider<T> extends EmptySuggestionProvider<T>, ConsumingProvider<T> {

  Pattern MENTION = Pattern.compile("<#?([0-9]+)>");

}
