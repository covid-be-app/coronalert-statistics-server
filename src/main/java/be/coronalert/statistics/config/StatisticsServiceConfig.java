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

package be.coronalert.statistics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "statistics")
public class StatisticsServiceConfig {

  private DataSource cases;
  private DataSource hospitalisations;
  private int movingAverageSize;

  public DataSource getCases() {
    return cases;
  }

  public void setCases(DataSource cases) {
    this.cases = cases;
  }

  public DataSource getHospitalisations() {
    return hospitalisations;
  }

  public void setHospitalisations(DataSource hospitalisations) {
    this.hospitalisations = hospitalisations;
  }

  public int getMovingAverageSize() {
    return movingAverageSize;
  }

  public void setMovingAverageSize(int movingAverageSize) {
    this.movingAverageSize = movingAverageSize;
  }

  public static class DataSource {
    private String url;
    private int rate;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public int getRate() {
      return rate;
    }

    public void setRate(int rate) {
      this.rate = rate;
    }
  }

}
