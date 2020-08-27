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

import java.time.LocalDate;

public class CombinedHospitalisationsPerDay implements Comparable {

  private LocalDate date;
  private int hospitalisations;

  /**
   * Constructs a CombinedHospitalisationsPerDay entity.
   */
  public CombinedHospitalisationsPerDay(LocalDate date, int hospitalisations) {
    this.date = date;
    this.hospitalisations = hospitalisations;
  }

  public LocalDate getDate() {
    return date;
  }

  public int getHospitalisations() {
    return hospitalisations;
  }

  public void setHospitalisations(int hospitalisations) {
    this.hospitalisations = hospitalisations;
  }

  @Override
  public String toString() {
    return "CombinedCasesPerDay{"
      + "date =" + date
      + ", hospitalisations=" + hospitalisations
      + '}';
  }

  @Override
  public int compareTo(Object o) {
    return date.compareTo(((CombinedHospitalisationsPerDay) o).getDate());
  }
}
