package ar.edu.itba.sia.c12017.g5.gridlock.heuristics.notadmisible;

import ar.edu.itba.sia.c12017.g5.gridlock.gps.GridlockState;
import ar.edu.itba.sia.c12017.g5.gridlock.heuristics.Heuristic;
import ar.edu.itba.sia.c12017.g5.gridlock.models.Board;
import gps.api.GPSState;

public class EmptySpacesTop extends Heuristic {
  /**
   * This heuristic checks how many empty spaces are in the top rectangle of the board.
   * Greedy algorithm will choose boards with the lowest H values, this means that
   * it will try to occupy the spaces in the top of the board.
   */
  @Override
  public Integer calculate(GPSState state) {
    Board board = ((GridlockState) state).getBoard();
    int[][] grid = board.getBoard();
    int count = 0;
    for (int x = 0; x < grid[0].length; x++) {
      for (int y = 0; y <= board.getExitY(); y++) {
        if (grid[y][x] == Board.EMPTY_SYMBOL) {
          count++;
        }
      }
    }
    return count;
  }
}
