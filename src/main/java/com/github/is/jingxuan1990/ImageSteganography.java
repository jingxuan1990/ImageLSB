package com.github.is.jingxuan1990;

import com.github.is.jingxuan1990.algorithm.Steganography;
import com.github.is.jingxuan1990.exception.SteganographyException;
import com.github.is.jingxuan1990.util.AesCrypt;
import com.github.is.jingxuan1990.util.ImageUtils;
import com.github.is.jingxuan1990.util.Utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import javax.imageio.ImageIO;

/**
 * Image LSB
 *
 * @author hzliwenhao
 */
public class ImageSteganography {

  /**
   * Save data into image.
   *
   * @param data - the saved data
   * @param imagePath image path(only support png and jpg)
   * @return true: succeed false: failed
   */
  public static boolean toImg(String data, String imagePath) {
    File imageFile = new File(imagePath);
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(imageFile);
      BufferedImage image = Utils.streamToImage(inputStream);

      final int imageLength = image.getHeight() * image.getWidth();
      final int startingOffset = Utils.calculateStartingOffset(null, imageLength);

      // hide text
      Steganography steganography = new Steganography();
      steganography.encode(image, data, startingOffset);
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
   * Save the encoded data using AES into image.
   *
   * @param data message
   * @param password aes secret key
   * @param imagePath image path
   * @return true or false
   * @throws GeneralSecurityException AES encoded failed
   */
  public static boolean toImgAES(String data, String password, String imagePath)
      throws GeneralSecurityException {
    String encMsg = AesCrypt.encrypt(password, data);
    return toImg(encMsg, imagePath);
  }


  /**
   * Save the aes key and data into the image.
   *
   * @param data - data
   * @param password - aes key
   * @param imagePath - image
   * @return true or false
   */
  public static boolean toImgAESWithPW(String data, String password, String imagePath)
      throws GeneralSecurityException {
    String encMsg = AesCrypt.encrypt(password, data);
    return toImg(encryptDecrypt(password + "|" + encMsg), imagePath);
  }

  /**
   * Get the data from image.
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

  /**
   * Get the decode text from image.
   *
   * @param password aes secret key
   * @param imagePath image path
   * @return origin text
   * @throws GeneralSecurityException AES decode failed
   */
  public static String fromImgAES(String password, String imagePath)
      throws GeneralSecurityException {
    String encMsg = fromImg(imagePath);
    return AesCrypt.decrypt(password, encMsg);
  }

  /**
   * Get the data from the image using the password also saved into the image.
   *
   * @param imagePath - image
   * @return the decoded data
   */
  public static String fromImgAESWithPW(String imagePath)
      throws GeneralSecurityException {
    String data = fromImg(imagePath);
    data = encryptDecrypt(data);
    int indexOf = data.indexOf("|");
    String password = data.substring(0, indexOf);
    data = data.substring(indexOf);
    return AesCrypt.decrypt(password, data);
  }

  private static String encryptDecrypt(String input) {
    char[] key = {'N', 'E', 'T', 'S', 'E', 'C', 'K', 'I', 'T'};
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < input.length(); i++) {
      output.append((char) (input.charAt(i) ^ key[i % key.length]));
    }
    return output.toString();
  }

}
