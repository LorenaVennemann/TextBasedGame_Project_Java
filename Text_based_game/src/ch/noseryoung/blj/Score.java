package ch.noseryoung.blj;

public class Score {
  private int score;

  public Score() {
    this.score = 0;
  }

  public void increaseScore(int points) {
    this.score += points;
  }

  public void decreaseScore(int points) {
    this.score -= points;
  }

  public int getScore() {
    return this.score;
  }
}