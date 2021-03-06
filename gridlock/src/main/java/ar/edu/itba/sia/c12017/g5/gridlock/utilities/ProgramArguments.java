package ar.edu.itba.sia.c12017.g5.gridlock.utilities;

import gps.SearchStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramArguments {

  public final String boardPath;
  public final SearchStrategy strategy;
  public final boolean showSolution;
  public final boolean showStats;
  public final boolean plot;

  private static final String PLOT = "plot";
  private static final String LEVEL_KEY = "level";
  private static final String SHOW_STATS = "showstats";
  private static final String STRATEGY_KEY = "strategy";
  private static final String SHOW_SOLUTION = "showsolution";

  /**
   * Class that parses program arguments.
   * @param args argument array.
   * @return an instance of ProgramArguments.
   */
  public static ProgramArguments build(String[] args) {
    if (args == null) {
      throw new IllegalArgumentException("Missing all arguments.");
    }
    List<String> arguments = Arrays.asList(args);
    Map<String, String> argMap = new HashMap<>();
    arguments.forEach(arg -> {
      String[] keyVal = arg.split("=");
      if (keyVal.length != 2) {
        throw new IllegalArgumentException("Illegal argument: " + arg + ". Expected 'key=value'");
      }
      argMap.put(keyVal[0].toLowerCase(), keyVal[1].toLowerCase());
    });
    ProgramArguments out = new ProgramArguments(
        argMap.get(LEVEL_KEY),
        strategyFrom(argMap.get(STRATEGY_KEY)),
        Boolean.valueOf(argMap.getOrDefault(SHOW_SOLUTION, "true")),
        Boolean.valueOf(argMap.getOrDefault(SHOW_STATS, "true")),
        Boolean.valueOf(argMap.getOrDefault(PLOT, "false"))
    );
    out.assertValid();
    return out;
  }

  private static SearchStrategy strategyFrom(String strategy) {
    if (strategy == null) {
      throw new IllegalArgumentException("Strategy required");
    }
    switch (strategy) {
      case "iddfs": return SearchStrategy.IDDFS;
      case "bfs": return SearchStrategy.BFS;
      case "dfs": return SearchStrategy.DFS;
      case "greedy": return SearchStrategy.GREEDY;
      case "astar": return SearchStrategy.ASTAR;
      case "fiddfs": return SearchStrategy.FIDDFS;
      default: throw new IllegalArgumentException("Illegal strategy: " + strategy);
    }
  }

  private ProgramArguments(String boardPath, SearchStrategy strategy,
                           Boolean showSolution, Boolean showStats, Boolean plot) {
    this.boardPath = boardPath;
    this.strategy = strategy;
    this.showSolution = showSolution;
    this.showStats = showStats;
    this.plot = plot;
  }

  public void assertValid() {
    if (this.boardPath == null) {
      throw new IllegalArgumentException("Level path is required: level=PATH");
    }
  }

  @Override
  public String toString() {
    return "ProgramArguments{"
        + "boardPath='" + boardPath + '\''
        + ", strategy=" + strategy
        + ", showSolution=" + showSolution
        + ", showStats=" + showStats
        + ", plot=" + plot
        + '}';
  }
}
