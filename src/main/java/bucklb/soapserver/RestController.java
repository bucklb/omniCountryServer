package bucklb.soapserver;

import hello.wsdl.Country;
import hello.wsdl.GetCountryResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//
// Idea is that we present a restFul interface to the world, and behind the scenes will offer a number of ways of getting
// the data.  Options thus far are Soap, Rest, XL & none
/*
    /           - just says what we are
    /help       - slightly more
    /soap       - via soap and returns fully xml'ed response
    /soapString - via soap, but returned as string rather than object
    /rest       - via rest and returns fully xml'ed response
    /restString - via rest, but returned as string rather than object
*/


// Basic rest controller stuff.
@RequestMapping("")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private CountryRestClient countryRestClient;

    @Autowired
    private CountrySoapClient countrySoapClient;





    // in case no link is requested, give them something to look at.  A clue as to what will respond would be good
    @RequestMapping(value="",method = RequestMethod.GET)
    public String doHome() { return "soapServer"; }

    @RequestMapping(value="help",method = RequestMethod.GET)
    public String doHelp() { return "soapServer - intended as basic framework to do SOAP stuff around"; }

    // Get stuff from Soap
    @RequestMapping(value="soapString",method = RequestMethod.GET)
    public String doSoapString(@RequestParam(value="country", required = false) String country) {

        // soapClient looks after the details now
        return countrySoapClient.getCountryString(country);
    }

    @RequestMapping(value="soap",method = RequestMethod.GET)
    public ResponseEntity<Country> doSoap(@RequestParam(value="country", required = false) String countryName) {

        // soapClient looks after the details now
        return countrySoapClient.getCountryResponse(countryName);
    }





    @RequestMapping(value="rest",method = RequestMethod.GET)
    public ResponseEntity<Country> doRest(@RequestParam(value="country", required = false) String country) {

        if(country==null || "\"\"".equals(country) || "".equals(country)){
            System.out.println("country is null");
            country="Spain";
        }

        return countryRestClient.getCountry(country);
    }


}
