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

import be.coronalert.statistics.cases.CovidCasesPoller;
import be.coronalert.statistics.config.StatisticsServiceConfig;
import be.coronalert.statistics.hospitalisations.HospitalisationsPoller;
import be.coronalert.statistics.mortality.MortalityPoller;
import be.coronalert.statistics.vaccinations.VaccinationsPoller;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@Order(1)
public class StatisticsRunner implements ApplicationRunner {

  private static final Logger logger = LoggerFactory.getLogger(StatisticsRunner.class);

  private static final Region DEFAULT_REGION = Region.EU_CENTRAL_1;

  @Autowired
  private ConfigurableApplicationContext context;

  @Autowired
  private HospitalisationsPoller hospitalisationsPoller;

  @Autowired
  private CovidCasesPoller covidCasesPoller;

  @Autowired
  private MortalityPoller mortalityPoller;

  @Autowired
  private VaccinationsPoller vaccinationsPoller;

  @Autowired
  private StatisticsServiceConfig statisticsServiceConfig;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    StatisticsReport statisticsReport = new StatisticsReport(
      LocalDate.now().minusDays(4),
      covidCasesPoller.pollResults(),
      hospitalisationsPoller.pollResults(),
      mortalityPoller.pollResults(),
      vaccinationsPoller.pollResults()
    );

    logger.info("statisticsReport = " + statisticsReport);

    PutObjectRequest putObjectRequest = PutObjectRequest
      .builder()
      .bucket(statisticsServiceConfig.getS3().getBucket())
      .key(statisticsServiceConfig.getS3().getKey())
      .contentType("application/json")
      .build();

    RequestBody requestBody = RequestBody.fromString(objectMapper.writeValueAsString(statisticsReport));

    S3Client s3Client = S3Client
      .builder()
      .region(DEFAULT_REGION)
      .build();

    s3Client.putObject(putObjectRequest, requestBody);

    context.close();
  }
}
