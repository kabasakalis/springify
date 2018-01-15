package springifyapi.user;

import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/user", tags = "~@ignore")
public class UserRunner extends TestBase {

}
