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

package be.coronalert.statistics.mortality;

import be.coronalert.statistics.config.StatisticsServiceConfig;
import be.coronalert.statistics.data.CombinedNumberPerDay;
import be.coronalert.statistics.data.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MortalityPoller {

  @Autowired
  private StatisticsServiceConfig statisticsServiceConfig;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Retrieves the sciensano mortality data.
   *
   * @return
   */
  public Result pollResults() throws IOException {
    URL url = new URL(statisticsServiceConfig.getMortality().getUrl());

    MortalityPerProvince[] entries = objectMapper.readValue(url, MortalityPerProvince[].class);

    Map<CombinedNumberPerDay, List<MortalityPerProvince>> combinedNumberPerDay =
      Arrays.stream(entries)
        .filter(e -> Objects.nonNull(e.getDate())).collect(groupingBy(MortalityPerProvince::getDate))
        .entrySet()
        .stream()
        .sorted(new Comparator<Map.Entry<LocalDate, List<MortalityPerProvince>>>() {
          @Override
          public int compare(Map.Entry<LocalDate, List<MortalityPerProvince>> o1,
                             Map.Entry<LocalDate, List<MortalityPerProvince>> o2) {
            return o2.getKey().compareTo(o1.getKey());
          }
        })
        .filter(e -> e.getKey().isBefore(LocalDate.now().minusDays(3)))
        .limit(14)
        .collect(toMap(x -> {
          int sumCases = x.getValue()
            .stream()
            .mapToInt(MortalityPerProvince::getDeaths)
            .sum();
          return new CombinedNumberPerDay(x.getKey(), sumCases);
        }, Map.Entry::getValue));

    TreeSet<CombinedNumberPerDay> combinedCasesPerDay = new TreeSet<>(combinedNumberPerDay.keySet());

    List<Integer> last2weeks = combinedCasesPerDay
      .stream()
      .map(CombinedNumberPerDay::getNumber)
      .collect(Collectors.toList());

    List<Integer> lastweek = last2weeks.subList(0, 7);
    List<Integer> weekbefore = last2weeks.subList(7, 14);


    double v1 = lastweek.stream().mapToInt(val -> val).average().orElse(0.0);
    double v2 = weekbefore.stream().mapToInt(val -> val).average().orElse(0.0);

    return new Result(v1, v2);

  }


}
