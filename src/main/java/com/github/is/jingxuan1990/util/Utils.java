package com.github.is.jingxuan1990.util;

import com.github.is.jingxuan1990.constant.Constants;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Public utility class holding a lot of useful methods
 *
 * @author hzliwenhao
 */
public class Utils {

  /**
   * Private constructor preventing outer initialization
   */
  private Utils() {
    // Prevent initialization
  }

  /**
   * Checks if the given String is empty. A String is considered as empty only if it is equal to
   * <b>null</b> or it is has length of <b>0</b>
   *
   * @return <b>true</b> if the String is empty, otherwise - <b>false</b>
   */
  public static boolean isEmpty(final String value) {
    return (null == value || Constants.EMPTY_STRING.equalsIgnoreCase(value.trim()));
  }

  /**
   * Gets an image from the given stream
   *
   * @param stream Stream to get the image from
   * @return The extracted image
   */
  public static BufferedImage streamToImage(InputStream stream) throws IOException {
    return ImageIO.read(stream);
  }

  /**
   * Convertes the given image to array of bytes
   *
   * @param image Image to be converted
   * @return Array of bytes
   */
  public static byte[] getImageAsBytes(BufferedImage image) {
    WritableRaster raster = image.getRaster();
    DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
    return buffer.getData();
  }

  /**
   * Compares two images byte by byte
   *
   * @param original The original image
   * @param transformed The transformed image
   */
  public static void compareImages(BufferedImage original, BufferedImage transformed) {
    byte[] originalBytes = Utils.getImageAsBytes(original);
    byte[] transformedBytes = Utils.getImageAsBytes(transformed);

    long length = Math.min(originalBytes.length, transformedBytes.length);

    boolean different = false;

    for (int i = 0; i < length; i++) {
      if (originalBytes[i] != transformedBytes[i]) {
        different = true;
      }
    }
  }

  /**
   * Calculates the index of the starting byte
   *
   * @param password The password given from the user
   * @param maxValue The length of the image
   * @return The index of the starting byte
   */
  public static int calculateStartingOffset(final String password, final long maxValue) {
    int offset = 0;

    if (!Utils.isEmpty(password)) {
      for (char c : password.toCharArray()) {
        offset += c;
      }

      offset %= maxValue;
    }

    return offset;
  }
}
