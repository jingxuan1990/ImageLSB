package com.github.is.jingxuan1990;

import com.github.is.jingxuan1990.algorithm.Steganography;
import com.github.is.jingxuan1990.exception.SteganographyException;
import com.github.is.jingxuan1990.util.ImageUtils;
import com.github.is.jingxuan1990.util.Utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Image LSB
 *
 * @author hzliwenhao
 */
public class ImageSteganography {

  /**
   * Save text into image.
   *
   * @param msg text
   * @param imagePath image path(only support png and jpg)
   * @return true: succeed false: failed
   */
  public static boolean toImg(String msg, String imagePath) {
    File imageFile = new File(imagePath);
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(imageFile);
      BufferedImage image = Utils.streamToImage(inputStream);

      final int imageLength = image.getHeight() * image.getWidth();
      final int startingOffset = Utils.calculateStartingOffset(null, imageLength);

      // hide text
      Steganography steganography = new Steganography();
      steganography.encode(image, msg, startingOffset);
      return ImageIO.write(image, ImageUtils.getFileExt(imageFile), imageFile);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Couldn't find file " + imagePath, e);
    } catch (IOException | SteganographyException e) {
      throw new RuntimeException(e);
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException ignored) {
        }
      }
    }
  }

  /**
   * Get text from image.
   *
   * @param imagePath image path.
   * @return String text
   */
  public static String fromImg(String imagePath) {
    File imageFile = new File(imagePath);
    InputStream inputStream = null;

    try {
      inputStream = new FileInputStream(imageFile);
      BufferedImage image = Utils.streamToImage(inputStream);

      final int imageLength = image.getWidth() * image.getHeight();
      final int startingOffset = Utils.calculateStartingOffset(null, imageLength);

      Steganography steganography = new Steganography();
      return steganography.decode(image, startingOffset);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Couldn't find file " + imagePath, e);
    } catch (IOException | SteganographyException e) {
      throw new RuntimeException(e);
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException ignored) {
        }
      }
    }
  }

}
