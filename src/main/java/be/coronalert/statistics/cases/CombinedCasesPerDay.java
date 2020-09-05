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

import java.time.LocalDate;

public class CombinedCasesPerDay implements Comparable {

  private LocalDate date;
  private int cases;

  /**
   * Constructs a CombinedCasesPerDay entity.
   */
  public CombinedCasesPerDay(LocalDate date, int cases) {
    this.date = date;
    this.cases = cases;
  }

  public LocalDate getDate() {
    return date;
  }

  public int getCases() {
    return cases;
  }

  public void setCases(int cases) {
    this.cases = cases;
  }

  @Override
  public String toString() {
    return "CombinedCasesPerDay{"
      +  "date=" + date
      +  ", cases=" + cases
      + '}';
  }

  @Override
  public int compareTo(Object o) {
    return date.compareTo(((CombinedCasesPerDay) o).getDate());
  }
}
