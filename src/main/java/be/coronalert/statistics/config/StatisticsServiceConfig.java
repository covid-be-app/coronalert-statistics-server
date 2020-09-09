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

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "statistics")
@Data
public class StatisticsServiceConfig {

  private DataSource cases;
  private DataSource hospitalisations;
  private DataSource mortality;
  private int movingAverageSize;
  private S3 s3;

  @Data
  public static class DataSource {
    private String url;
  }

  @Data
  public static class S3 {
    private String bucket;
    private String key;
  }
}
