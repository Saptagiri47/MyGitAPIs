package com.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/feature/GitAPIS.feature",
        glue = {"com.stepDefinitions", "com.resources"}, plugin = {"pretty", "html:reports-cucumber.html"},
        tags = "@retrieve or @create or @partialUpdate or @delete") // @partialupdate and not @delete

public class RunnerClass {

}