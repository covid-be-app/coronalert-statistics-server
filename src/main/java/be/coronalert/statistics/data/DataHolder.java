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

package be.coronalert.statistics.data;

import be.coronalert.statistics.cases.CombinedCasesPerDay;
import be.coronalert.statistics.hospitalisations.CombinedHospitalisationsPerDay;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class DataHolder {

  public Set<CombinedCasesPerDay> combinedCasesPerDay;
  public Set<CombinedHospitalisationsPerDay> combinedHospitalisationsPerDay;

  public Set<CombinedCasesPerDay> getCombinedCasesPerDay() {
    return combinedCasesPerDay;
  }

  public void setCombinedCasesPerDay(Set<CombinedCasesPerDay> combinedCasesPerDay) {
    this.combinedCasesPerDay = combinedCasesPerDay;
  }

  public Set<CombinedHospitalisationsPerDay> getCombinedHospitalisationsPerDay() {
    return combinedHospitalisationsPerDay;
  }

  public void setCombinedHospitalisationsPerDay(Set<CombinedHospitalisationsPerDay> combinedHospitalisationsPerDay) {
    this.combinedHospitalisationsPerDay = combinedHospitalisationsPerDay;
  }
}
