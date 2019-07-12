module com.github.stupremee.mela.command {

  requires com.google.guice;
  requires com.google.common;
  requires jsr305;

  exports com.github.stupremee.mela.command;
  exports com.github.stupremee.mela.command.bind;
  exports com.github.stupremee.mela.command.bind.tree;
  exports com.github.stupremee.mela.command.compile;
  exports com.github.stupremee.mela.command.handle;
  exports com.github.stupremee.mela.command.inject;
  exports com.github.stupremee.mela.command.intercept;
  exports com.github.stupremee.mela.command.map;
  exports com.github.stupremee.mela.command.util;

}