package com.ashupan.springboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ashupan.springboot.model.DateDifference;
import com.ashupan.springboot.service.DateDifferenceService;

@RestController
@RequestMapping(value = "/api")
@Api(value = "This is the controller that handles web requests to calculate the date difference")
public class DateDifferenceController {

  @Autowired
  private DateDifferenceService dateDifferenceService;

  @RequestMapping(path = "/datedifference")
  @GetMapping(params = "inputDate")
  @ApiOperation(value = "Calculates the date difference")
  public DateDifference calculateDateDifference(@RequestParam @DateTimeFormat(
      pattern = "yyyy-MM-dd") final LocalDate inputDate) {

    return dateDifferenceService.calculateDateDifference(inputDate);
  }

}
