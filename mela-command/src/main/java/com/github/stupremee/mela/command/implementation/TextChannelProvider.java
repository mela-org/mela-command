package com.github.stupremee.mela.command.implementation;

import com.sk89q.intake.parametric.Provider;

import java.util.regex.Pattern;

public interface TextChannelProvider<T> extends Provider<T> {

  Pattern MENTION = Pattern.compile("<#?([0-9]+)>");

}
