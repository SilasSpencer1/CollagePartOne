package model;

import java.io.IOException;
import java.util.List;

public interface Image {

  void darken(int intensity);

  void brighten(int intensity);

  void saveImage(String newFilename) throws IOException;

  void setBlueComponent();

  void setGreenComponent();

  void setRedComponent();

  List<List<Pixel>> getImage();


  /**
   * gets a specific pixel in the image data
   * @param x
   * @param y
   * @return
   */
  Pixel getPixelAt(int x, int y);
}
