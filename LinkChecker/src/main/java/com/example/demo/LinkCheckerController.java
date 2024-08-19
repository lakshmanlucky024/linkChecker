package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/link")
public class LinkCheckerController {
	@Autowired
	LinkService linkService;
	
	
	LinkCheckModel linkCheckModel = new LinkCheckModel() ;
	
    @GetMapping("/check")
    public Map<String,String> checkLinks(@RequestParam String url) {
    	System.out.println("Start");
        List<String> invalidLinks = new ArrayList<>();
        LocalDateTime myObj = LocalDateTime.now(); 
        linkCheckModel.setId(0);
        linkCheckModel.setUrl(url);
        linkCheckModel.setTime(String.valueOf(myObj));
        Map<String, String> map= new HashMap<>();
        try {
            URL inputUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) inputUrl.openConnection();
            connection.setRequestMethod("GET");
           // LocalDate myObj = LocalDate.now(); 
            int responseCode = connection.getResponseCode();
            System.out.println("Status Code"+responseCode);
            linkCheckModel.setStatusCode(String.valueOf(responseCode));
  
            if (responseCode != 200) {
            	map.put("valid", "false");
            	map.put("url", url + " - Broken (HTTP " + responseCode + ")");
                invalidLinks.add(url + " - Broken (HTTP " + responseCode + ")");
            }
            if(responseCode == 200) {
            	invalidLinks.add(url + "");
            	map.put("valid", "true");
            	map.put("url", url);
            	 linkCheckModel.setStatus("Success");
            }
            else {
            	 linkCheckModel.setStatus("Failure");
            }
            // Check for third-party links
            String host = inputUrl.getHost();
            host=host.trim();
            String inputHost = new URL(url).getHost();
           // System.out.println("inputHost "+inputHost);
            System.out.println("host "+host);
            if (!host.endsWith("veltech.edu.in") ) {
            	map.put("valid", "false");
            	map.put("url", url + " - Third-party link");
                invalidLinks.add(url + " - Third-party link");
                linkCheckModel.setStatusCode("Third-party link");
            }
           // System.out.println(invalidLinks);
            linkService.create(linkCheckModel);
        } catch (IOException e) {
            e.printStackTrace();
        	linkCheckModel.setStatusCode("unknown");
        	linkCheckModel.setStatus("Failure");
        	map.put("valid", "false");
        	map.put("url", url + " -Broken link");
            invalidLinks.add(url + " - Broken link");
            linkService.create(linkCheckModel);
        }
        
        return map;
    }
    
    @PostMapping("/store")
    public LinkCheckModel saveLink(@RequestBody LinkCheckModel linkCheckModel ) {
    	return linkService.create(linkCheckModel);
    }


    @GetMapping("/data")
    public List<LinkCheckModel> getAllEmployees() {
        return linkService.findAll();
    }
     
    private static class LinkCheckResult {
        private final boolean valid;
        private final List<String> invalidLinks;

        public LinkCheckResult(boolean valid, List<String> invalidLinks) {
            this.valid = valid;
            this.invalidLinks = invalidLinks;
        }

        public boolean isValid() {
            return valid;
        }

        public List<String> getInvalidLinks() {
            return invalidLinks;
        }
    }
}
