package com.github.stupremee.mela.command;

public @interface Command {
  String[] aliases();
  String desc() default "N/A";
  String help() default "N/A";
  String usage() default "N/A";
}
