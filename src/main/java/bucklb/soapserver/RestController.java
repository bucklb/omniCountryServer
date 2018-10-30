package bucklb.soapserver;

import hello.wsdl.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

// Basic rest controller stuff.
@RequestMapping("")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    CountrySoapClient countrySoapClient;


    // in case no link is requested, give them something to look at.  A clue as to what will respond would be good
    @RequestMapping(value="",method = RequestMethod.GET)
    public String doHome() {
        String s = "soapServer";
        return s;
    }

    @RequestMapping(value="help",method = RequestMethod.GET)
    public String doHelp() {
        String s = "soapServer - intended as basic framework to do SOAP stuff around";
        return s;
    }

    @RequestMapping(value="soap",method = RequestMethod.GET)
    public String doSoap(@RequestParam(value="country", required = false) String country) {
//    public String doSoap() {

        if(country==null){
            System.out.println("country is null");
            country="Spain";
        }


        // Cover our ass if no country name supplied
        if ( "\"\"".equals(country) ){
            System.out.println("country is empty");
            country="Spain";
        }






        GetCountryResponse response = countrySoapClient.getCountry(country);
        System.err.println(response.getCountry().getCurrency());

        return countrySoapClient.getCountryString(response);
    }



}
