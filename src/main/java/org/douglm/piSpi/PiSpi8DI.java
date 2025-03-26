/* ********************************************************************
    Appropriate copyright notice
*/
package org.douglm.piSpi;

import com.pi4j.context.Context;
import org.douglm.spi.SpiDevice;

import java.security.ProviderException;

/**
 * User: mike Date: 3/18/25 Time: 22:41
 */
public class PiSpi8DI extends SpiDevice {
  private final PiSpi8DIConfig<?> config;

  /**
   *
   * @param context Pi4j
   * @param address of chip
   */
  public PiSpi8DI(final PiSpi8DIConfig<?> config,
                  final Context context,
                  final int address) {
    super(context, address);
    this.config = config;
  }

  /** Return state of all inputs on device - note NOT corrected for
   * high/low true.
   *
   * @return boolean array.
   */
  public boolean[] states() {
    final var res =  new boolean[8];
    final var b = readByte();

    for (var i = 0; i < 8; i++) {
      res[i] = ((byte)(b >> i) & 1) == 1;
    }

    return res;
  }

  public boolean state(final int input) {
    final var b = readByte();
    final var inputConfig = getInputConfig(input);
    return inputConfig.isHighTrue() ^ (((byte)(b >> input) & 1) == 1);
  }

  public PiSpi8DIInputConfig getInputConfig(final int input) {
    for (final var dic: config.getInputs()) {
      if (dic.getIndex() == input) {
        return dic;
      }
    }

    throw new RuntimeException("Input " + input + " not found");
  }

  private int readByte() throws ProviderException {
    final byte[] data = new byte[3];
    data[0] = (byte)(0x41 | (config.getChipAddress() << 1)); // Address
    data[1] = (byte)(0x09);    // GPIO register
    dumpBytes("before", data);

    final var res = getSpi().transfer(data, 3);
    if (res >= 0) {
      dumpBytes("after (" + res + ")", data);
      return Byte.toUnsignedInt(data[2]);
    } else {
      throw new ProviderException("Bad result " + res);
    }
  }
}
