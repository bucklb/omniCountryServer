package bucklb.soapserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

// Basic rest controller stuff.
@RequestMapping("")
@org.springframework.web.bind.annotation.RestController
public class RestController {

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

}
