package springifyapi.role;

import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/role", tags = "~@ignore")
public class RoleRunner extends TestBase {

}
