package be.coronalert.statistics.vaccinations;

import be.coronalert.statistics.config.StatisticsServiceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

@Component
public class VaccinationsPoller {
  @Autowired
  private StatisticsServiceConfig statisticsServiceConfig;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Retrieves the sciensano vaccinations data.
   *
   * @return
   */
  public Map<String, Integer> pollResults() throws IOException {
    URL url = new URL(statisticsServiceConfig.getVaccinations().getUrl());

    Vaccination[] entries = objectMapper.readValue(url, Vaccination[].class);

    int firstDose = Arrays.stream(entries)
      .filter(e -> e.getDose().equals("A"))
      .map(Vaccination::getCount)
      .map(Integer::valueOf)
      .reduce(0, Integer::sum);

    int secondDose = Arrays.stream(entries)
      .filter(e -> e.getDose().equals("B"))
      .map(Vaccination::getCount)
      .map(Integer::valueOf)
      .reduce(0, Integer::sum);


    return Map.of("firstDose", firstDose, "secondDose", secondDose);
  }
}
