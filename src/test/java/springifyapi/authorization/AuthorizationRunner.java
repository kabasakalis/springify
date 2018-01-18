
package springifyapi.authorization;

import org.junit.Ignore;
import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/authorization", tags = "~@ignore")
public class AuthorizationRunner extends TestBase {

}
