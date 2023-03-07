package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Collage {
  HashMap<String, Layer> layerMap;
  int width;
  int height;
  List<List<Pixel>> canvas;

  /**
   * Creates the overall canvas for our project.
   * @param height the height of our true canvas.
   * @param width the width of our true canvas.
   */
  Collage(int height, int width) {
    this.height = height;
    this.width = width;
    this.canvas = new ArrayList<>();
    this.layerMap = new HashMap<>();
    createCanvas(height, width);
  }

  public void addLayer(Layer layer) {
    layerMap.put(layer.getLayerName(), layer);

    for (int i = 0; i < layer.getBackground0Size(); i++) {
      for (int j = 0; j < layer.getBackground0Size(); j++) {
        Pixel curr = layer.background.get(i).get(j);
        canvas.get(i).get(j).setRed(curr.getRed());
        canvas.get(i).get(j).setBlue(curr.getBlue());
        canvas.get(i).get(j).setGreen(curr.getGreen());
      }
    }
  }

  private void createCanvas(int height, int width) {
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        Pixel curr = new Pixel(255, 255, 255, 0);
        row.add(curr);
      }
      canvas.add(row);
    }
  }

  void saveCollage(String newFilename) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(newFilename));

    writer.write("C1" + "\n");
    writer.write(this.getWidth() + " " + this.getHeight());

    for (Map.Entry<String, Layer> set : layerMap.entrySet()) {

      writer.write(set.getKey() + set.getValue().getFilter());
      writer.write("LAYER CONTENT FORMAT");
    }
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
}
