/* ********************************************************************
    Appropriate copyright notice
*/
package org.douglm.piSpi;

import com.pi4j.context.Context;
import org.douglm.spi.SpiDevice;

import java.security.ProviderException;

/** This board has a problem if the inputs are AC in that it will
 * register the zero volt transitions. This leads to intermittent
 * incorrect readings which turn out to be multiples of 8 1/3 millis
 * apart - the time between zero transitions at 60Hz.
 * <p>
 * The states method will read multiple times and OR the results - in
 * effect smoothing out the transitions.
 * <p>
 * This can be disabled with multiRead = false in the config.
 * <p>
 * User: mike Date: 3/18/25 Time: 22:41
 */
public class PiSpi8DI extends SpiDevice {
  private final PiSpi8DIConfig<?> config;

  /**
   *
   * @param context Pi4j
   * @param config of board
   */
  public PiSpi8DI(final Context context,
                  final PiSpi8DIConfig<?> config) {
    super(context, config);
    this.config = config;

    // Write to the first 2 registers

    final var haAddress = config.getHardwareAddress();
    final byte haenVal;
    if (config.isHardwareAddressEnabled()) {
      haenVal = 0x08;
    } else {
      haenVal = 0;
    }

    // Enable/disable hardware addressing based on config
    final byte[] setHaen = {
            (byte)(0x40 | (haAddress << 1)),	// Write command
            (byte)0x05,	   // IOCON register
            haenVal};	// HAEN
    var setRes = transfer(setHaen);
    if (setRes < 0) {
      throw new RuntimeException("Bad result from setHaen " + setRes);
    }

    int ipol = 0;
    for (final var input: config.getInputs()) {
      if (input.isHighTrue()) {
        ipol = ipol | (1 << input.getIndex());
      }
    }

    final byte[] setDir = {
            (byte)(0x40 | (haAddress << 1)), // Address
            (byte)0x00, // IODIR register
            (byte)0xFF,	// IODIR is input
            (byte)ipol}; // IPOL - set based on input configs
    setRes = transfer(setDir);
    if (setRes < 0) {
      throw new RuntimeException("Bad result from setDir " + setRes);
    }
  }

  /** Return state of all inputs on device.
   *
   * @return boolean array.
   */
  public boolean[] states() {
    final var res =  new boolean[8];
    var b = readByte();

    if (config.isMultiRead()) {
      for (int i = 0; i < 4; i++) {
        b = b | readByteDelay();
      }
    }

    for (var i = 0; i < 8; i++) {
      res[i] = ((byte)(b >> i) & 1) == 1;
    }

    return res;
  }

  public boolean state(final int input) {
    assert (input >= 0 && input < 8);
    return states()[input];
  }

  private int readByteDelay() {
    try {
      Thread.sleep(1);
    } catch (final InterruptedException e) {
      throw new RuntimeException(e);
    }
    return readByte();
  }

  public PiSpi8DIInputConfig getInputConfig(final int input) {
    for (final var dic: config.getInputs()) {
      if (dic.getIndex() == input) {
        return dic;
      }
    }

    throw new RuntimeException("Input " + input + " not found");
  }

  private int readByte() {
    final byte[] data = new byte[3];
    data[0] = (byte)(0x41 | (config.getHardwareAddress() << 1)); // Address
    data[1] = (byte)(0x09);    // GPIO register

    final var res = transfer(data);
    if (res >= 0) {
      return Byte.toUnsignedInt(data[2]);
    }

    throw new ProviderException("Bad result " + res);
  }
}
