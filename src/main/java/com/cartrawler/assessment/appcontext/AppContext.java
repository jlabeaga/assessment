package com.cartrawler.assessment.appcontext;

import com.cartrawler.assessment.config.ProcessingOptions;
import com.cartrawler.assessment.exception.AssessmentException;
import com.cartrawler.assessment.processor.FilterDuplicatesProcessor;
import com.cartrawler.assessment.processor.RemoveAboveMedianProcessor;
import com.cartrawler.assessment.processor.SortingProcessor;
import com.cartrawler.assessment.repository.CarResultRepository;
import com.cartrawler.assessment.service.CarResultService;
import com.cartrawler.assessment.service.DisplayService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppContext {

  public static final String DEFAULT_CONFIG_FILE = "processingOptions.json";

  public static final AppContext INSTANCE = new AppContext(DEFAULT_CONFIG_FILE);

  private AppContext(String configFile) {
    try {
      init(configFile);
    } catch (IOException e) {
      throw new AssessmentException("Unable to initialize AppContext", e);
    }
  }

  private final Map<Class, Object> components = new HashMap<>();

  public <T> T getComponent(Class<T> componentClass) {
    return (T) components.get(componentClass);
  }

  private Object addComponent(Object component) {
    components.put(component.getClass(), component);
    return component;
  }

  private void init(String configFile) throws IOException {
    addComponent(ProcessingOptions.load(configFile));
    addComponent(new CarResultRepository());
    addComponent(new FilterDuplicatesProcessor(getComponent(ProcessingOptions.class)));
    addComponent(new SortingProcessor());
    addComponent(new RemoveAboveMedianProcessor(getComponent(ProcessingOptions.class)));
    addComponent(new DisplayService());
    addComponent(
      new CarResultService(
        getComponent(CarResultRepository.class),
        getComponent(DisplayService.class),
        getComponent(FilterDuplicatesProcessor.class),
        getComponent(SortingProcessor.class),
        getComponent(RemoveAboveMedianProcessor.class)));
  }
}
