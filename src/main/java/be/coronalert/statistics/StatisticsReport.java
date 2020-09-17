/*
 * Coronalert / coronalert-statistics-server
 *
 * (c) 2020 Devside SRL
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

package be.coronalert.statistics;

import be.coronalert.statistics.data.Result;
import java.time.LocalDate;
import lombok.Data;

@Data
public class StatisticsReport {

  private Integer averageInfected;
  private Integer averageInfectedChangePercentage;
  private Integer averageHospitalised;
  private Integer averageHospitalisedChangePercentage;
  private Integer averageDeceased;
  private Integer averageDeceasedChangePercentage;
  private LocalDate startDate;
  private LocalDate endDate;


  /**
   * Creates the statisticsreport.
   */
  public StatisticsReport(
    LocalDate endDate,
    Result cases,
    Result hospitalisations,
    Result mortalities

  ) {

    this.endDate = endDate;
    this.startDate = this.endDate.minusDays(6);

    this.averageDeceased = mortalities.getCurrentValue().intValue();
    this.averageDeceasedChangePercentage = mortalities.getDifference();

    this.averageHospitalised = hospitalisations.getCurrentValue().intValue();
    this.averageHospitalisedChangePercentage = hospitalisations.getDifference();

    this.averageInfected = cases.getCurrentValue().intValue();
    this.averageInfectedChangePercentage = cases.getDifference();

  }

}
