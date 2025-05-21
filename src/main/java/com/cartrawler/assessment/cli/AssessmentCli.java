package com.cartrawler.assessment.cli;

import com.cartrawler.assessment.appcontext.AppContext;
import com.cartrawler.assessment.service.CarResultService;

public class AssessmentCli {

  public static void main(String[] args) {
    CarResultService carResultService = AppContext.INSTANCE.getComponent(CarResultService.class);
    carResultService.process();
  }

}
