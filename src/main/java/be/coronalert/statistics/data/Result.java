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

package be.coronalert.statistics.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

  private Double currentValue;
  private Double previousValue;

  public Integer getDifference() {
    Double diff = (currentValue - previousValue) / previousValue * 100;
    return diff.intValue();
  }

  @Override
  public String toString() {
    return "Result{"
      + "currentValue=" + currentValue
      + ", previousValue=" + previousValue
      + ", difference=" + getDifference()
      + '}';
  }
}
