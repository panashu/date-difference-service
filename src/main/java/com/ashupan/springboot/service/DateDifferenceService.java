package com.ashupan.springboot.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;
import com.ashupan.springboot.model.DateDifference;

@Service
public class DateDifferenceService {

  public DateDifference calculateDateDifference(LocalDate inputDate){
    LocalDate dateToday = LocalDate.now();
    DateDifference dateDifference = new DateDifference();
    dateDifference.setDateDifference(ChronoUnit.DAYS.between(inputDate, dateToday));
    return dateDifference;
  }
}
