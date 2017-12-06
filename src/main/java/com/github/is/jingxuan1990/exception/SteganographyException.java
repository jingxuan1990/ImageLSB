package com.github.is.jingxuan1990.exception;

/**
 * Generic exception thrown if something wrong occurs during steganography algorithm execution.
 *
 * @author hzliwenhao
 */
public class SteganographyException extends Exception {

  private static final long serialVersionUID = 9031373046571531684L;

  public SteganographyException() {
    super();
  }

  public SteganographyException(String message) {
    super(message);
  }

  public SteganographyException(Throwable throwable) {
    super(throwable);
  }

  public SteganographyException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
