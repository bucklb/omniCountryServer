package hello;

import hello.wsdl.Country;

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
    /soap       - via soap and returns http/json response
    /soapString - via soap, but returned as string rather than object
    /rest       - via rest and returns http/json response
    /restString - via rest, but returned as string rather than object
*/


// Basic rest controller stuff.
@RequestMapping("")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    // Ease the pain of using clients
    @Autowired
    private CountryRestClient countryRestClient;

    @Autowired
    private CountrySoapClient countrySoapClient;

    private static String mode="rest";

    // Need to better understand what to do if country not found / server down / etc
    @RequestMapping(value="fail",method = RequestMethod.GET)
    public ResponseEntity<Country> doFail() {
        Country country=null;
        return new ResponseEntity<>(country,HttpStatus.NOT_FOUND); }


    // Helpful style ==============================================================================================
    // in case no link is requested, give them something to look at.  A clue as to what will respond would be good
    @RequestMapping(value="",method = RequestMethod.GET)
    public String doHome() { return "soapServer"; }

    @RequestMapping(value="help",method = RequestMethod.GET)
    public String doHelp() { return "soapServer - intended as basic framework to do SOAP stuff around"; }

    // SOAP style    ==============================================================================================
    @RequestMapping(value="soapString",method = RequestMethod.GET)
    public String doSoapString(@RequestParam(value="country", required = false) String countryName) {

        // soapClient looks after the details now
        return countrySoapClient.getCountryString(countryName);
    }

    @RequestMapping(value="soap",method = RequestMethod.GET)
    public ResponseEntity<Country> doSoap(@RequestParam(value="country", required = false) String countryName) {

        // soapClient looks after the details now
        return countrySoapClient.getRestCountry(countryName);
    }

    // Restful style ==============================================================================================
    @RequestMapping(value="rest",method = RequestMethod.GET)
    public ResponseEntity<Country> doRest(@RequestParam(value="country", required = false) String countryName) {

        // restClient looks after stuff
        return countryRestClient.getRestCountry(countryName);
    }

    @RequestMapping(value="restString",method = RequestMethod.GET)
    public String doRestString(@RequestParam(value="country", required = false) String countryName) {

        // restClient looks after stuff
        return countryRestClient.getCountryString(countryName);
    }

    // Setting style ==============================================================================================
    // want the option of setting the system in to SOAP/REST/EXCEL/OFF modes
    // seems to work with localhost:8081/setMode?mode=soap
    @RequestMapping(value="setMode", method = RequestMethod.POST)
    public ResponseEntity<String> postMode(@RequestParam("mode") final String newMode) {

        // Basically, screw around with the quote.  Perhaps just set type = "Plagiarism"
        System.out.println("Mode setting : " + newMode);

        // Record for later
        mode=newMode;


        // Ought to pass something back
        return new ResponseEntity<String>("mode set as : " + mode,HttpStatus.OK);
    }

    @RequestMapping(value="mode",method = RequestMethod.GET)
    public String doModeString(@RequestParam(value="country", required = false) String countryName) {

        // allow outside world to see what mode we're in
        return mode;
    }



    // Want to honour the "mode" setting somehow
    @RequestMapping(value="country",method = RequestMethod.GET)
    public ResponseEntity<Country> doCountry(@RequestParam(value="country", required = false) String countryName) {

        ResponseEntity<Country> response;
        // soapClient looks after the details now
        switch(mode.toUpperCase()) {
            case "SOAP":
                response= countrySoapClient.getRestCountry(countryName);
                break;
            case "REST":
                response=  countryRestClient.getRestCountry(countryName);
                break;
            default:
                Country country=null;
                response=  new ResponseEntity<>(country,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    // Want to honour the "mode" setting somehow
    @RequestMapping(value="countryString",method = RequestMethod.GET)
    public String doCountryString(@RequestParam(value="country", required = false) String countryName) {

        String response;
        // soapClient looks after the details now
        switch(mode.toUpperCase()) {
            case "SOAP":
                response= countrySoapClient.getCountryString(countryName);
                break;
            case "REST":
                response=  countryRestClient.getCountryString(countryName);
                break;
            default:
                response=  "Not found";
        }
        return response;
    }

}
