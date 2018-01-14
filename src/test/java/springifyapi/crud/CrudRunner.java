package springifyapi.crud;

import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/crud", tags = "~@ignore")
public class CrudRunner extends TestBase {

}
