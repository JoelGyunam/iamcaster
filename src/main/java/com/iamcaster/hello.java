package com.iamcaster;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/hello")
@Controller
public class hello {

	@GetMapping("/hi")
	public String hi() {
		return "hello/hello";
	}
	
}
