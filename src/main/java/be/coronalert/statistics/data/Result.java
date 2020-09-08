package be.coronalert.statistics.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

  private Double currentValue;
  private Double previousValue;

  public Integer getDifference() {
    Double diff = (currentValue - previousValue) / previousValue * 100;
    return diff.intValue();
  }

  @Override
  public String toString() {
    return "Result{"
      + "currentValue=" + currentValue
      + ", previousValue=" + previousValue
      + ", difference=" + getDifference()
      + '}';
  }
}
