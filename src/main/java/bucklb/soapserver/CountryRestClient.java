package bucklb.soapserver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import hello.wsdl.Country;

//
// Do we want this as an entity?
//
@Component
public class CountryRestClient {

    // Do stuff ..
    public ResponseEntity<Country> getCountry(String country) {

        System.out.println("CountryRestClient called : country = " + country);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Country> responseEntityCountry = restTemplate.getForEntity(
           "http://localhost:9090/rest",
            Country.class);

        System.out.println("CountryRestClient called : responseEntity = " + responseEntityCountry.toString());

        return responseEntityCountry;
    }

}
