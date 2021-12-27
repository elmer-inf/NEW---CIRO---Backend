package ciro.atc;

/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
public class ConfigResource {

	
	final private String name = "callcenter.properties";

	public String baseDir() {
		if(System.getProperty("jboss.server.base.dir")!=null) {
			return System.getProperty("jboss.server.base.dir")+"/deployments";
		}else {
			return System.getProperty("user.dirr");
		}
		
	}
	
    private String  LoadPropieties() {
    	String base =baseDir();
    	
        File file = new File(base, name);
        if (!file.exists()) {

            if (!(file = new File(base, name)).exists()) {
                throw new RuntimeException("properties not found");
            }
        }
        return  file.getAbsolutePath();
    }
    
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
      PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
      
      properties.setLocation(new FileSystemResource(new File(LoadPropieties())));
      
      properties.setIgnoreResourceNotFound(false);

      return properties;
    }
    
}*/



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static ciro.atc.auth.util.Config.NAME;
import static ciro.atc.auth.util.Config.SRC_PATH;

@Configuration
public class ConfigResource {

    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {

        Source resource = new Source();

        resource.setIgnoreUnresolvablePlaceholders(true);

        return resource;
    }

    final public class Source
            extends PropertySourcesPlaceholderConfigurer
            implements EnvironmentAware {

        final private String name = "applicationConfig";

        public Source() {
            super();
        }

        @Override
        public void setEnvironment(Environment environment) {

            if (environment != null) {

                final ConfigurableEnvironment config = (ConfigurableEnvironment) environment;

                final MutablePropertySources sources = config.getPropertySources();

                sources.addLast(new PropertiesPropertySource(name, get()));
            }

            super.setEnvironment(environment);
        }

        private Properties get() {

            Properties properties = new Properties();

            try {
                File file = new File(SRC_PATH, NAME);

                FileSystemResource fsy = new FileSystemResource(file);

                PropertiesLoaderUtils.fillProperties(properties, fsy);

            } catch (IOException e) {

                logger.info(e.getMessage(), e);
            }

            return properties;
        }
    }

}

