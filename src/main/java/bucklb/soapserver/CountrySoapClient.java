package bucklb.soapserver;


import hello.wsdl.Country;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import hello.wsdl.GetCountryRequest;
import hello.wsdl.GetCountryResponse;

public class CountrySoapClient extends WebServiceGatewaySupport {

    // Not sure this is right place, but will do for now
    private String stringify(GetCountryResponse response) {
        return String.format("name: %s, capital: %s, currency: %s, population: %d",
                response.getCountry().getName(),
                response.getCountry().getCapital(),
                response.getCountry().getCurrency(),
                response.getCountry().getPopulation());
    }

    // Need to clean up the parameter value(s) so avoid repeating code.  Only needed for country
    private String cleanParameter(String parameterValue, String defaultValue) {
        return (parameterValue == null || "\"\"".equals(parameterValue) || "".equals(parameterValue)) ? defaultValue : parameterValue;
    }

    // Pinched from client stuff
    public GetCountryResponse getSoapCountry(String country) {

        // Dodge some easy bullets
        country = cleanParameter(country, "Poland");

        // Create a request
        GetCountryRequest request = new GetCountryRequest();
        request.setName(country);

        // Get a response from the rather hard-coded URL
        GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/countries", request,
                        new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/GetCountryRequest"));

        // Return a rather soap response
        return response;
    }

    // Quite likely we'll just want the payload object
    public Country getCountry(String countryName) {

        // Use SOAP to get our response
        GetCountryResponse response = getSoapCountry(countryName);

        // Pass back the payload (let someone else worry about details ??)
        return response.getCountry();
    }

    // Avoid cluttering upp the Endpoint.  Do we need to decide if it's been a success??
    public ResponseEntity<Country> getCountryResponse(String countryName){

        // Assume for now it's all good
        Country country=getCountry(countryName);
        return new ResponseEntity<>(country,HttpStatus.OK);
    }


    // Return in string format
    public String getCountryString(String country) {
        return stringify(getSoapCountry(country));
    }

    // Return as "raw" object


}
