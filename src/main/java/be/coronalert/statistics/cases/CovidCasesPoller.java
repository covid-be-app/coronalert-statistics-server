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

package be.coronalert.statistics.cases;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CovidCasesPoller {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private StatisticsServiceConfig statisticsServiceConfig;


  /**
   * Retrieves the sciensano cases data:
   *  Aug 28 : In de week van 18 tot 24 augustus werden per dag gemiddeld 477 besmettingen vastgesteld.
   *           Dat is 7 procent minder dan de week ervoor, toen waren het er nog 513 per dag.
     *  Aug 31 : In de week van 21 tot 27 augustus werden 430 besmettingen vastgesteld.
   *           Dat is 14% minder dan de voorgaande week.
   *           (580 + 512 + 207 + 116 + 610 + 524 + 512) / 7 = 437*
   * @return
   */
  public Result pollResults() throws IOException {

    URL url = new URL(statisticsServiceConfig.getCases().getUrl());
    CasesPerProvince[] entries = objectMapper.readValue(url, CasesPerProvince[].class);

    Map<CombinedNumberPerDay, List<CasesPerProvince>> collectedCombinedCasesPerDay =
      Arrays.stream(entries)
        .filter(e -> Objects.nonNull(e.getDate()))
        .collect(Collectors.groupingBy(CasesPerProvince::getDate))
        .entrySet()
        .stream()
        .sorted(new Comparator<Map.Entry<LocalDate, List<CasesPerProvince>>>() {
          @Override
          public int compare(Map.Entry<LocalDate, List<CasesPerProvince>> o1,
                             Map.Entry<LocalDate, List<CasesPerProvince>> o2) {
            return o2.getKey().compareTo(o1.getKey());
          }
        })
        .filter(e -> e.getKey().isBefore(LocalDate.now().minusDays(3)))
        .limit(14)
        .collect(Collectors.toMap(x -> {
          int sumCases = x.getValue()
            .stream()
            .mapToInt(CasesPerProvince::getCases)
            .sum();
          return new CombinedNumberPerDay(x.getKey(), sumCases);
        }, Map.Entry::getValue));

    TreeSet<CombinedNumberPerDay> combinedCasesPerDay = new TreeSet<>(collectedCombinedCasesPerDay.keySet());

    List<Integer> last2weeks = combinedCasesPerDay
      .stream()
      .map(CombinedNumberPerDay::getNumber)
      .collect(Collectors.toList());

    List<Integer> lastweek = last2weeks.subList(0,7);
    List<Integer> weekbefore = last2weeks.subList(7,14);


    double v1 = lastweek.stream().mapToInt(val -> val).average().orElse(0.0);
    double v2 = weekbefore.stream().mapToInt(val -> val).average().orElse(0.0);

    return new Result(v1,v2);

  }
}
