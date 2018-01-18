 package springifyapi;

 import cucumber.api.CucumberOptions;
 import org.junit.Ignore;
 import springifyapi.TestBase;

 @Ignore
 @CucumberOptions(tags = {"~@ignore"})
 public class SpringifyTest extends TestBase {
     // this class will automatically pick up all *.feature files
     // in src/test/java/springify and even recurse sub-directories
     // even though the class name ends with 'Test', the maven 'pom.xml'
     // has set 'SpringifyTestParallel' to be the default 'test suite' for the whole project
 }
