package br.com.globalcode.exception;

/**
 * <p>Title: Academia do Arquiteto</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class GlobalcodeException extends Exception {
  public GlobalcodeException() {
  }

  public GlobalcodeException(String msg) {
    super(msg);
  }
  public GlobalcodeException(String msg, Throwable t) {
    super(msg, t);
  }
}