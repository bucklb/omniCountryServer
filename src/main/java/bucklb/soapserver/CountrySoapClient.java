package bucklb.soapserver;


import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import hello.wsdl.GetCountryRequest;
import hello.wsdl.GetCountryResponse;

public class CountrySoapClient extends WebServiceGatewaySupport {

    // Not sure this is right place, but will do for now
    public String getCountryString(GetCountryResponse response){
        return String.format("name: %s, capital: %s, currency: %s, population: %d",
                response.getCountry().getName(),
                response.getCountry().getCapital(),
                response.getCountry().getCurrency(),
                response.getCountry().getPopulation());
    }

    // Pinched from client stuff
    public GetCountryResponse getCountry(String country) {

        GetCountryRequest request = new GetCountryRequest();
        request.setName(country);

        GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/countries", request,
                        new SoapActionCallback(
                                "http://spring.io/guides/gs-producing-web-service/GetCountryRequest"));

        return response;
    }
}
