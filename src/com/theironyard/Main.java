package com.theironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static User user;

    public static void main(String[] args) {
        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> { //anonymous function
                    // to send where whether has logged in or not yet
                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "login.html"); //injecting values into template
                    } else {
                        m.put("name", user.name);
                        return new ModelAndView(m, "home.html");
                    }
                }),
                new MustacheTemplateEngine()  // takes a 3rd argument, tells spark will use mustache templates
        );//click button, put in name and be redirected to server
        Spark.post( //redirects to get route
                "/login",
                ((request, response) -> { //anon fxn
                   String name = request.queryParams("loginName");
                    user = new User(name);
                    response.redirect("/");
                    return "";
                })
        );
    }
}

