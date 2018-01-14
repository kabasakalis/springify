package springifyapi.genre;

import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/genre", tags = "~@ignore")
public class GenreRunner extends TestBase {

}
