/* ********************************************************************
    Appropriate copyright notice
*/
package org.douglm.piSpi;

import org.bedework.base.ToString;

import org.douglm.spi.SpiDeviceConfig;

import java.util.List;

/** Configuration for the PiSpi8Di widgetlords board.
 * This board only allows inputs though the chip itself (MCP23S08)
 * allows both input and output.
 * <br/>
 * User: mike Date: 3/25/25 Time: 23:22
 */
public class PiSpi8DIConfig<InputConfig extends PiSpi8DIInputConfig>
        extends SpiDeviceConfig {
  private int hardwareAddress; // 0 to 3
  private boolean hardwareAddressEnabled = true;
  private boolean multiRead = true;
  private List<InputConfig> inputs;
  private String notes;

  public int getHardwareAddress() {
    return hardwareAddress;
  }

  public void setHardwareAddress(final int val) {
    hardwareAddress = val;
  }

  public boolean isHardwareAddressEnabled() {
    return hardwareAddressEnabled;
  }

  public void setHardwareAddressEnabled(final boolean val) {
    hardwareAddressEnabled = val;
  }

  public boolean isMultiRead() {
    return multiRead;
  }

  public void setMultiRead(final boolean val) {
    multiRead = val;
  }

  public List<InputConfig> getInputs() {
    return inputs;
  }

  public void setInputs(final List<InputConfig> val) {
    inputs = val;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(final String val) {
    notes = val;
  }

  public ToString toStringSegment(final ToString ts) {
    return super.toStringSegment(ts)
                .append("hardwareAddress", hardwareAddress)
                .append("multiRead", multiRead)
                .append("inputs", inputs)
                .append("notes", notes);
  }

  public String toString() {
    return toStringSegment(new ToString(this)).toString();
  }
}
