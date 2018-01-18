package springifyapi.login;

import org.junit.Ignore;
import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/login", tags = "~@ignore")
public class LoginRunner extends TestBase {

}
