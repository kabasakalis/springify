package springifyapi.album;

import springifyapi.TestBase;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:springifyapi/album", tags = "~@ignore")
public class AlbumRunner extends TestBase {

}
