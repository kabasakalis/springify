package springifyapi.root;

import org.junit.Ignore;
import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/root", tags = "~@ignore")
public class RootRunner extends TestBase {

}
