package com.gomad.springboot_basic;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    public static class HomeResponse{
        private String message;

        public HomeResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @GetMapping("/home")
    public String homePage(){
        return "Hello Home..!";
    }

    @GetMapping("/homeAsJson")
    public HomeResponse homePageAsJson(){
        return new HomeResponse("Hello Home as Json..!");
    }

    @PostMapping("/post-home-json")
    public HomeResponse postHomePage(@RequestBody String message){
        return new HomeResponse(message);
    }

    @GetMapping("/home/{name}")
    public HomeResponse homePageWithPathParam(@PathVariable String name){
        return new HomeResponse("Hello " + name + "..!");
    }

    @GetMapping("/home-path-param")
    public HomeResponse homePutResponse(@RequestParam("name") String name) {
        return new HomeResponse("Hello " + name + "..!");
    }
}
