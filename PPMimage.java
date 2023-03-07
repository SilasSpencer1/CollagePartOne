package model;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PPMimage implements Image {

  List<List<Pixel>> image = new ArrayList<>();
  String filename;

  HashMap<String, List<List<Pixel>>> hm = new HashMap<>();

  PPMimage(String filename) throws IOException {
    this.filename = filename;
    this.image = new ArrayList<>();

    readPPM();
  }

  private void readPPM() {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File "+filename+ " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: "+width);
    int height = sc.nextInt();
    System.out.println("Height of image: "+height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);

    // Read the pixel data
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
        Pixel pixel = new Pixel(red, green, blue, 255); // alpha is set to 255
        row.add(pixel);
      }
      image.add(row);
    }

  }

  @Override
  public void darken(int intensity) {
    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(0).size(); j++) {
        Pixel curr = image.get(i).get(j);
        int blueUpdate = curr.blue - intensity <= 0 ? 0 : curr.blue - intensity;
        int redUpdate = curr.red - intensity <= 0 ? 0 : curr.red - intensity;
        int greenUpdate = curr.green - intensity <= 0 ? 0 : curr.green - intensity;
        curr.setRed(redUpdate);
        curr.setBlue(blueUpdate);
        curr.setGreen(greenUpdate);
      }
    }
  }

  @Override
  public void brighten(int intensity) throws IllegalArgumentException {
    if (intensity < 0 || intensity > 255) throw new IllegalArgumentException();

    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(0).size(); j++) {
        Pixel curr = image.get(i).get(j);
        int blueUpdate = curr.blue + intensity >= 255 ? 255 : curr.blue + intensity;
        int redUpdate = curr.red + intensity >= 255 ? 255 : curr.red + intensity;
        int greenUpdate = curr.green + intensity >= 255 ? 255 : curr.green + intensity;
        curr.setRed(redUpdate);
        curr.setBlue(blueUpdate);
        curr.setGreen(greenUpdate);
      }
    }
  }

  @Override
  public void saveImage(String filepath) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

    // Write the header
    writer.write(String.format("P3 %d %d %d\n", image.get(0).size(), image.size(), 255));

    for (List<Pixel> row : image) {
      for (Pixel pixel : row) {
        writer.write(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue() + " ");
      }
      writer.write("\n");
    }

    writer.close();
  }

  public void setBlueComponent() {
    for (List<Pixel> pixels : image) {
      for (Pixel pixel : pixels) {
        pixel.setGreen(0);
        pixel.setRed(0);
      }
    }
  }

  @Override
  public void setGreenComponent() {
    for (List<Pixel> pixels : image) {
      for (Pixel pixel : pixels) {
        pixel.setRed(0);
        pixel.setBlue(0);
      }
    }
  }

  @Override
  public void setRedComponent() {
    for (List<Pixel> pixels : image) {
      for (Pixel pixel : pixels) {
        pixel.setGreen(0);
        pixel.setBlue(0);
      }
    }
  }




  @Override
  public List<List<Pixel>> getImage() {
    return image;
  }

  @Override
  public Pixel getPixelAt(int x, int y) {
    return this.image.get(x).get(y);
  }
}
