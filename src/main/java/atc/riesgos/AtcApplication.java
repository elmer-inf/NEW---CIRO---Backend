package atc.riesgos;

import atc.riesgos.auth.util.StringPassword;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableAutoConfiguration
@EnableEncryptableProperties
@SpringBootApplication
@ComponentScan(basePackages = {"atc.riesgos"})
public class AtcApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtcApplication.class, args);
	}
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	/**
	 *
	 * @return StringEncryptor
	 */
	@Bean(name = "encryptor")
	public StringEncryptor stringEncryptor() {

		return StringPassword.get();
	}
}
