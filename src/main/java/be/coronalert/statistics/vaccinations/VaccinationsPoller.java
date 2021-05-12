package be.coronalert.statistics.vaccinations;

import be.coronalert.statistics.config.StatisticsServiceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

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

    Map<String, Integer> collect = Arrays.stream(entries)
      .collect(groupingBy(
        Vaccination::getDose,
        summingInt(Vaccination::getCount)));

    Integer atLeastPartiallyVaccinated = collect.get("A") + collect.get("C");
    Integer fullyVaccinated = collect.get("B") + collect.get("C");

    return Map.of(VaccinationLevel.PARTIALLY, atLeastPartiallyVaccinated, VaccinationLevel.FULLY, fullyVaccinated);
  }

  private Integer getNumberOfVaccinationsForDose(Vaccination[] entries, String dose) {
    return Arrays.stream(entries)
      .filter(e -> e.getDose().equals(dose))
      .map(Vaccination::getCount)
      .map(Integer::valueOf)
      .reduce(0, Integer::sum);
  }
}
