/* ********************************************************************
    Appropriate copyright notice
*/
package org.douglm.piSpi;

import org.bedework.base.ToString;

/**
 * User: mike Date: 3/26/25 Time: 11:43
 */
public class PiSpi8AIChannelConfig {
  public enum Mode {
    thermistor,
    milliAmp,
    dc5,
    dc10
  }

  private Mode mode;
  private int channel;
  private String name;
  private int beta; // for thermistors
  private int inputReference;
  private int inputRange;

  public Mode getMode() {
    return mode;
  }

  public void setMode(final Mode val) {
    mode = val;
  }

  public int getChannel() {
    return channel;
  }

  public void setChannel(final int val) {
    channel = val;
  }

  public String getName() {
    return name;
  }

  public void setName(final String val) {
    name = val;
  }

  public int getBeta() {
    return beta;
  }

  public void setBeta(final int val) {
    beta = val;
  }

  public int getInputReference() {
    return inputReference;
  }

  public void setInputReference(final int val) {
    inputReference = val;
  }

  public int getInputRange() {
    return inputRange;
  }

  public void setInputRange(final int val) {
    inputRange = val;
  }

  public ToString toStringSegment(final ToString ts) {
    ts.append("mode", mode)
      .append("channel", channel)
      .append("name", name)
      .append("beta", beta)
      .append("inputReference", inputReference)
      .append("inputRange", inputRange);

    return ts;
  }

  public String toString() {
    return toStringSegment(new ToString(this)).toString();
  }
}
