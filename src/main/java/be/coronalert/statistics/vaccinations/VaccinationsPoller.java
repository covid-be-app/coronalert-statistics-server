package be.coronalert.statistics.vaccinations;

import be.coronalert.statistics.config.StatisticsServiceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import static be.coronalert.statistics.vaccinations.VaccinationDose.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Component
public class VaccinationsPoller {
  @Autowired
  private StatisticsServiceConfig statisticsServiceConfig;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Retrieves the sciensano vaccinations data.
   *
   * @return A map containing the amount of vaccinations for dose 1 and 2.
   */
  public Map<VaccinationLevel, Integer> pollResults() throws IOException {
    URL url = new URL(statisticsServiceConfig.getVaccinations().getUrl());

    Vaccination[] entries = objectMapper.readValue(url, Vaccination[].class);

    Map<VaccinationDose, Integer> collect = Arrays.stream(entries)
      .collect(groupingBy(
        Vaccination::getDose,
        summingInt(Vaccination::getCount)));

    Integer atLeastPartiallyVaccinated = collect.get(PARTIALLY_VACCINATED_WITH_ONE_DOSE)
      + collect.get(FULLY_VACCINATED_WITH_ONE_DOSE);
    Integer fullyVaccinated = collect.get(FULLY_VACCINATED_WITH_TWO_DOSES)
      + collect.get(FULLY_VACCINATED_WITH_ONE_DOSE);

    return Map.of(VaccinationLevel.PARTIALLY, atLeastPartiallyVaccinated, VaccinationLevel.FULLY, fullyVaccinated);
  }
}
