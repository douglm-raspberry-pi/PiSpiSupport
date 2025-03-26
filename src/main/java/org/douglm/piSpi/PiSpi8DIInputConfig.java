/* ********************************************************************
    Appropriate copyright notice
*/
package org.douglm.piSpi;

import org.bedework.base.ToString;

/**
 * User: mike Date: 3/25/25 Time: 23:25
 */
public class PiSpi8DIInputConfig {
  private int index;
  private String name;
  private boolean highTrue; // A 1 bit is true otherwise false

  public int getIndex() {
    return index;
  }

  public void setIndex(final int val) {
    index = val;
  }

  public String getName() {
    return name;
  }

  public void setName(final String val) {
    name = val;
  }

  public boolean isHighTrue() {
    return highTrue;
  }

  public void setHighTrue(final boolean val) {
    highTrue = val;
  }

  public ToString toStringSegment(final ToString ts) {
    return ts.append("index", index)
             .append("name", name)
             .append("highTrue", highTrue);
  }

  public String toString() {
    return toStringSegment(new ToString(this)).toString();
  }
}
