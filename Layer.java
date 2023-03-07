package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Layer {

  List<Image> imagesOnLayer;
  List<List<Pixel>> background;
  List<String> commandList;
  String layerName;
  int width;
  int height;

  public Layer(String layerName, int width, int height) {
    this.width = width;
    this.height = height;
    this.layerName = layerName;
    this.background = new ArrayList<>();
    this.imagesOnLayer = new ArrayList<>();
    this.commandList = new ArrayList<>();
    makeBackground(height, width);
  }

  private void makeBackground(int height, int width) {
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        Pixel curr = new Pixel(255, 255, 255, 0);
        row.add(curr);
      }
      background.add(row);
    }
    System.out.println("Background width : " + background.get(0).size());
    System.out.println("Background height : " + background.size());
  }

   //creates a white background.

  public void addImageToLayer(Image image, int xPos, int yPos) throws IOException {
    applyFilterCommands(image);
    List<List<Pixel>> imageData = image.getImage();
    for (int i = 0; i < imageData.size(); i++) {
      for (int j = 0; j < imageData.get(0).size(); j++) {
        int canvasX = xPos + i;
        int canvasY = yPos + j;
        if (canvasX >= 0 && canvasX < height && canvasY >= 0 && canvasY < width) {
          Pixel curr = image.getPixelAt(i, j);
          background.get(canvasX).get(canvasY).setRed(curr.getRed());
          background.get(canvasX).get(canvasY).setBlue(curr.getBlue());
          background.get(canvasX).get(canvasY).setGreen(curr.getGreen());
        }
      }
    }
    imagesOnLayer.add(image);
  }

  private void applyFilterCommands(Image image) {
    if (commandList.size() > 0) {
      if (commandList.contains("red")) {
        image.setRedComponent();
      }
      if (commandList.contains("blue")) {
        image.setBlueComponent();
      }
      if (commandList.contains("green")) {
        image.setGreenComponent();
      }

      for (int i = 0; i < commandList.size(); i++) {
        if (commandList.get(i).contains("dark")) {
          String[] words = commandList.get(i).split(" ");
          int intensity = Integer.parseInt(words[words.length -1]);
          image.darken(intensity);
        }

        if (commandList.get(i).contains("brighten")) {
          String[] words = commandList.get(i).split(" ");
          int intensity = Integer.parseInt(words[words.length - 1]);
          image.brighten(intensity);
        }
      }
    }
  }

  public void lookAtLayerData() {
    for (int i = 0; i < background.size(); i++) {
      for (int j = 0; j < background.get(0).size(); j++) {
        Pixel curr = background.get(i).get(j);
        System.out.println("red " + curr.red + "blue " + curr.blue + " green" + curr.green);
      }
    }
  }

  public void saveLayerAsPPM(String filepath) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

    // Write the header
    writer.write(String.format("P3 %d %d %d\n", background.get(0).size(), background.size(), 255));

    for (List<Pixel> row : background) {
      for (Pixel pixel : row) {
        writer.write(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue() + " ");
      }
      writer.write("\n");
    }

    writer.close();
  }

  public void addCommand(String s) {
    commandList.add(s);
  }

  public String getLayerName() {
    return layerName;
  }

  public String getFilter() {
    //it seems as though a layer can only have one filter at the moment
    if (commandList.isEmpty()) {
      return "normal";
    }

    return commandList.get(0);
  }

  public int getBackgroundSize() {
    return background.size();
  }

  public int getBackground0Size() {
    return background.get(0).size();
  }
}