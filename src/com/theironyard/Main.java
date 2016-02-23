package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static HashMap<String, User> users = new HashMap<>();


    public static void main(String[] args) {
        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> { //anonymous function
                    // to send where whether has logged in or not yet
                    HashMap m = new HashMap();

                    Session session = request.session();
                    String userName = session.attribute("userName"); //like 'get' method. give key, returns value
                    User user = users.get(userName);


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
                    User user = users.get(name);
                    if (user == null) {
                        user = new User(name); //creating object if doesn't exist
                        users.put(name, user); //value is user object
                    }

                    Session session = request.session(); //ability to store arbitrary key/value pair in session (special HM)
                    session.attribute("userName", name); //attr like 'put' in HM


                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );
    }
}

