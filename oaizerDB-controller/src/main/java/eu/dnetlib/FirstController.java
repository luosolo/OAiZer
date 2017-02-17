package eu.dnetlib;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/**/hi")
public class FirstController {

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Response getResponseJson(@RequestParam("name") String name) {
		Response s = new Response();
		s.setName(name);
		s.setValue("SUCA");
		return s;

	}
}
