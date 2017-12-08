package com.kabasakalis.springifyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Properties;
 import java.net.URL;
import java.net.URLClassLoader;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;


@EnableEncryptableProperties
// @PropertySource(name="EncryptedProperties", value = "classpath:encrypted.properties")
@SpringBootApplication
public class SpringifyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringifyApiApplication.class, args);

   Properties props = System.getProperties();
   props.list(System.out);

ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	System.out.println(url.getFile());
        }
	}
}
