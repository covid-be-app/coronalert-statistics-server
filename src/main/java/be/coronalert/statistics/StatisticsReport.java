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
