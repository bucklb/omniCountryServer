package bucklb.soapserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountryConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml.  NOTE - PACKAGE (not file). Confusing, much?
        marshaller.setContextPath("hello.wsdl");
        return marshaller;
    }

    @Bean
    public CountrySoapClient countrySoapClient(Jaxb2Marshaller marshaller) {
        CountrySoapClient client = new CountrySoapClient();
        // Having the port and path hard-coded feels bad.
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}