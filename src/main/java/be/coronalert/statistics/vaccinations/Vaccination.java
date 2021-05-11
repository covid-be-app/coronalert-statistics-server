package be.coronalert.statistics.vaccinations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Vaccination {
  @JsonProperty("DOSE")
  private String dose;

  @JsonProperty("COUNT")
  private String count;
}
