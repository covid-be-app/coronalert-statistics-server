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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class HospitalisationsPerProvince {

  @JsonProperty("DATE")
  private LocalDate date;

  @JsonProperty("PROVINCE")
  private String province;

  @JsonProperty("NEW_IN")
  private int newIn;

  public int getNewIn() {
    return newIn;
  }

  public String getProvince() {
    return province;
  }

  public LocalDate getDate() {
    return date;
  }

  @Override
  public String toString() {
    return "StatisticalEntry{"
      +  "date=" + date
      + ", province='" + province + '\''
      + ", newIn=" + newIn
      + '}';
  }
}
