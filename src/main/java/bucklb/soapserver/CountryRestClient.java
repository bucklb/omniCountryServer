package bucklb.soapserver;

import hello.wsdl.GetCountryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import hello.wsdl.Country;

// When we want to get stuff via restFul calls it'll be here
//
//
@Component
public class CountryRestClient {


    // Not sure this is right place, but will do for now
//    private String stringify(ResponseEntity<Country> response) {
//        return String.format("name: %s, capital: %s, currency: %s, population: %d",
//                response.getBody().getCountry().getName(),
//                response.getCountry().getCapital(),
//                response.getCountry().getCurrency(),
//                response.getCountry().getPopulation());
//    }

    // Not sure this is right place, but will do for now
    private String stringify(Country country) {
        return String.format("REST >> name: %s, capital: %s, currency: %s, population: %d",
                country.getName(),
                country.getCapital(),
                country.getCurrency(),
                country.getPopulation());
    }
    // Need to clean up the parameter value(s) so avoid repeating code.  Only needed for country
    private String cleanParameter(String parameterValue, String defaultValue) {
        return (parameterValue == null || "\"\"".equals(parameterValue) || "".equals(parameterValue)) ? defaultValue : parameterValue;
    }

    // Use a restful service to get hold of the data (in the form of a responseEntity)
    public ResponseEntity<Country> getRestCountry(String countryName) {

        // Dodge some easy bullets.  Different default to Soap (different set of countries)
        countryName = cleanParameter(countryName, "Italy");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Country> responseEntityCountry = restTemplate.getForEntity(
                "http://localhost:9090/rest?country=" + countryName, Country.class);

        return responseEntityCountry;
    }

    // Quite likely we'll just want the payload object
    public Country getCountry(String countryName) {

        // Use REST call to get our response
        ResponseEntity<Country> response = getRestCountry(countryName);

        // Pass back the payload (let someone else worry about details ??)
        return response.getBody();
    }

    // Quite likely we'll just want the payload object
    public String getCountryString(String countryName) {

        // Pass back the payload (let someone else worry about details ??)
        return stringify(getCountry(countryName));
    }


}
