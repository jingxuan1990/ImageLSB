package com.github.is.jingxuan1990;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;

/**
 * test case
 *
 * @author hzliwenhao
 * @date 2017/12/6
 */
public class ImageSteganographyTest {

  private static final String MSG = "liwenhao123~";

  private File imageFile() throws URISyntaxException {
    URL url = ImageSteganographyTest.class.getResource("test.png");
    return new File(url.toURI());
  }

  @Test
  public void toImg() throws Exception {
    ImageSteganography.toImg(MSG, imageFile().getPath());
  }

  @Test
  public void fromImg() throws Exception {
    String msg = ImageSteganography.fromImg(imageFile().getPath());
    Assert.assertTrue(MSG.equals(msg));
  }

}
