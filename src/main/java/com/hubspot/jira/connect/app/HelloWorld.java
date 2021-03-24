package com.hubspot.jira.connect.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;

import com.atlassian.connect.spring.AtlassianHostUser;
import com.atlassian.connect.spring.IgnoreJwt;

@Controller
public class HelloWorld {

    @Autowired
    ServletContext servletContext;

    @Autowired
    private HttpServletRequest request;

    @Value("classpath:crmforjira.svg")
    Resource resourceFile;

    @IgnoreJwt
    @RequestMapping(value = "/hello-world", method = RequestMethod.GET)
    @ResponseBody
    public String helloWorld(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        return "hello-world";
    }

    @RequestMapping(value = "/installed", method = RequestMethod.POST)
    @ResponseBody
    public String installed(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        System.out.println("123123");
        return "installed endpoint text";
    }

    @RequestMapping(value = "/my-general-page", method = RequestMethod.GET)
    @ResponseBody
    public String myGeneralPage(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        return "my general page text";
    }

    @IgnoreJwt
    @RequestMapping(value = "/icon", method = RequestMethod.GET, produces = "image/svg+xml")
    @ResponseBody
    public byte[] getIcon(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        System.out.println("Requesting icon..");
        File serveFile = new File("/Users/pkolesnyk/src/hubspot/JiraConnectApp/src/main/resources/hubspot-logo.svg");

        try {
            return Files.readAllBytes(serveFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[1];
//        return resourceFile;
    }

    @RequestMapping(value = "/panel_url", method = RequestMethod.GET)
    public String getHtml(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        System.out.println("At least I'm invoked");
        return "index.html";
    }

    @IgnoreJwt
    @RequestMapping(value = "/panel_url1", method = RequestMethod.GET)
    public String getTest(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        System.out.println("At least I'm invoked1");
        return "test.html";
    }
}