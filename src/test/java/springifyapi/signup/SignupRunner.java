package springifyapi.signup;

import org.junit.Ignore;
import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/signup", tags = "~@ignore")
public class SignupRunner extends TestBase {

}
