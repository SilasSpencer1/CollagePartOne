package model;

public interface ILayer {

  void addImageToLayer(Image image, int xPos, int yPos);

  void addCommand(String s);
}
