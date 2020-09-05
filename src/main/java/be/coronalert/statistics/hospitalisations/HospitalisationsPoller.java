/*
 * Coronalert / coronalert-statistics-server
 *
 * (C) 2020, Ixor CVBA
 *
 * Ixor CVBA and all other contributors /
 * copyright owners license this file to you under the Apache
 * License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package be.coronalert.statistics.hospitalisations;

import be.coronalert.statistics.average.MovingAverageInSlidingWindow;
import be.coronalert.statistics.config.StatisticsServiceConfig;
import be.coronalert.statistics.data.DataHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HospitalisationsPoller {

  @Autowired
  private DataHolder dataHolder;

  @Autowired
  StatisticsServiceConfig statisticsServiceConfig;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Retrieves the sciensano hospitalisation data.
   */
  @Scheduled(fixedDelayString = "${statistics.hospitalisations.poll-rate}")
  public void pollResults() throws IOException {
    URL url = new URL(statisticsServiceConfig.getHospitalisations().getUrl());
    HospitalisationsPerProvince[] entries = objectMapper.readValue(url, HospitalisationsPerProvince[].class);

    Map<CombinedHospitalisationsPerDay, List<HospitalisationsPerProvince>> collectedCombinedHospitalisationsPerDay =
      Arrays.stream(entries)
        .filter(e -> Objects.nonNull(e.getDate())).collect(groupingBy(HospitalisationsPerProvince::getDate))
        .entrySet()
        .stream()
        .collect(toMap(x -> {
          int sumCases = x.getValue().stream().mapToInt(HospitalisationsPerProvince::getNewIn).sum();
          return new CombinedHospitalisationsPerDay(x.getKey(), sumCases);
        }, Map.Entry::getValue));

    Set<CombinedHospitalisationsPerDay> combinedHospitalisationsPerDay =
      new TreeSet<>(collectedCombinedHospitalisationsPerDay.keySet());

    MovingAverageInSlidingWindow m = new MovingAverageInSlidingWindow(statisticsServiceConfig.getMovingAverageSize());

    combinedHospitalisationsPerDay.forEach((v) -> {
      int sumCases = v.getHospitalisations();
      int movingAverage = m.findMovingAverage(sumCases);
      v.setHospitalisations(movingAverage);
    });

    dataHolder.setCombinedHospitalisationsPerDay(combinedHospitalisationsPerDay);

  }


}
