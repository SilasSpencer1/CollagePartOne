package model;

public class Pixel {

  int red;
  int green;
  int blue;
  int alpha;

  Pixel(int red, int green, int blue, int alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  public void setAlpha(int alpha) {
    this.alpha = alpha;
  }

  public void setGreen(int green) {
    this.green = green;
  }

  public void setBlue(int blue) {
    this.blue = blue;
  }

  public void setRed(int red) {
    this.red = red;
  }

  public int getRed() {
    return red;
  }

  public int getBlue() {
    return blue;
  }

  public int getGreen() {
    return green;
  }

  public String toString() {
     return "Blue " + getBlue() + "Green " + green + "Red " + red;
  }
}
