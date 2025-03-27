/* ********************************************************************
    Appropriate copyright notice
*/
package org.douglm.piSpi;

import org.bedework.base.ToString;

import java.util.List;

/** Configuration for the PiSpi8Di widgetlords board.
 *
 * User: mike Date: 3/25/25 Time: 23:22
 */
public class PiSpi8DIConfig<T extends PiSpi8DIInputConfig> {
  private int spiAddress;
  private int chipAddress; // 0 to 3
  private List<T> inputs;
  private String notes;

  public int getSpiAddress() {
    return spiAddress;
  }

  public void setSpiAddress(final int val) {
    spiAddress = val;
  }

  public int getChipAddress() {
    return chipAddress;
  }

  public void setChipAddress(final int val) {
    chipAddress = val;
  }

  public List<T> getInputs() {
    return inputs;
  }

  public void setInputs(final List<T> val) {
    inputs = val;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(final String val) {
    notes = val;
  }

  public ToString toStringSegment(final ToString ts) {
    ts.append("spiAddress", spiAddress)
      .append("inputs", inputs)
      .append("notes", notes);

    return ts;
  }

  public String toString() {
    return toStringSegment(new ToString(this)).toString();
  }
}
