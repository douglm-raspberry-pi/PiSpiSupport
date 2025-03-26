/* ********************************************************************
    Appropriate copyright notice
*/
package org.douglm.piSpi;

import org.bedework.base.ToString;

import java.util.List;

/**
 * User: mike Date: 3/26/25 Time: 11:42
 */
public class PiSpi8AIConfig<T extends PiSpi8AIChannelConfig> {
  private int spiAddress;
  private List<T> channels;

  public int getSpiAddress() {
    return spiAddress;
  }

  public void setSpiAddress(final int val) {
    spiAddress = val;
  }

  public List<T> getChannels() {
    return channels;
  }

  public void setChannels(final List<T> val) {
    channels = val;
  }

  public ToString toStringSegment(final ToString ts) {
    ts.append("spiAddress", spiAddress)
      .append("channels", channels);

    return ts;
  }

  public String toString() {
    return toStringSegment(new ToString(this)).toString();
  }
}
