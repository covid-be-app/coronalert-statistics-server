package be.coronalert.statistics.vaccinations;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum VaccinationDose {
  @JsonProperty("A")
  PARTIALLY_VACCINATED_WITH_ONE_DOSE,
  @JsonProperty("B")
  FULLY_VACCINATED_WITH_TWO_DOSES,
  @JsonProperty("C")
  FULLY_VACCINATED_WITH_ONE_DOSE,
  @JsonEnumDefaultValue
  UNKNOWN_VACCINATION_DOSE

}
