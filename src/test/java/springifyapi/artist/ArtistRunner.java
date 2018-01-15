package springifyapi.artist;

import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/artist", tags = "~@ignore")
public class ArtistRunner extends TestBase {

}
