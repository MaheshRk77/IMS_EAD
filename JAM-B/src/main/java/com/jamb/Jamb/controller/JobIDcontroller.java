package com.jamb.Jamb.controller;

import com.jamb.Jamb.model.Jobid;
import com.jamb.Jamb.model.users;
import com.jamb.Jamb.service.implementation;
import com.jamb.Jamb.util.ConnectDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Controller
@SessionAttributes(value = "job", types = {Jobid.class})
public class JobIDcontroller {
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    static private String cookieval= "";


    implementation i;
    @Autowired
    public JobIDcontroller(implementation i) {
        this.i = i;
    }

    public static Boolean checkCookie(Cookie[] cookies) {
        Boolean loggedin = false;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("verify")) {
                 cookieval = cookie.getValue();
                return true;
            }
        }
        return loggedin;

    }

@RequestMapping(value = {"/", "/login.html"})
public String display(HttpServletRequest request, Model m, @RequestParam(name = "logout", defaultValue = "1" ) String Logout, HttpServletResponse response, Model model) {
    UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
    String requestedValue = builder.buildAndExpand().getPath();
    if(!model.containsAttribute("job")) model.addAttribute("job", new Jobid());

    if (requestedValue.equals("/login.html")) {
        System.out.println(Logout);
        if(Logout.equalsIgnoreCase("Logout")){
            System.out.println("hi");
            Cookie cookie = new Cookie("verify", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "login";
        }
        else {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && checkCookie(cookies)) {
            System.out.println("cookval"+ cookieval);
            m.addAttribute("loggedmsg", "User already logged in. Logout? ---->");
            return "test";
        } else
            return "login";
    }
    }
    else {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && checkCookie(cookies))
        m.addAttribute("loggedmsg", "Hello there! Welcome.");
        else m.addAttribute("loggedmsg", "Hi! Please login to continue.");
        return "test";
    }


}



    @RequestMapping(value = "/test/jobs", method = RequestMethod.GET)
    public String jobdescfetch(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer jobid, ModelMap model, @ModelAttribute("job") Jobid jc, RedirectAttributes redirectAttributes) {
        jc = i.search(jobid);
        if(jc!=null){ model.addAttribute("job",jc); }
        else { model.addAttribute("job","No data found");}
        return "test";


    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String auth(@RequestParam(name = "uname", defaultValue = "1" ) String username,@RequestParam(name = "pwd", defaultValue = "1" ) String password, Model model, HttpServletResponse response) {

            int status = 0;
            String id = "0";
            String role = "";
            String name = "";
            try {
                ConnectDB.getInstance();
                connection = ConnectDB.getConnection();
                statement = connection.prepareStatement("Select * from users where username=? and password=?");
                statement.setString(1, username);
                statement.setString(2, password);
                resultSet = statement.executeQuery();
                resultSet.next();
                if (resultSet.getString("username").equalsIgnoreCase(username) && resultSet.getString("password").equalsIgnoreCase(password)) {
                    status = 1;
                    id = Integer.toString(resultSet.getInt("userid"));
                    role = resultSet.getString("role");
                    name = resultSet.getString("username");

                }
                Cookie cookie = new Cookie("verify", id);
                response.addCookie(cookie);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }

            String s = "Login success";
            System.out.println("login"+ role + " " + name + " " + id);

            if (status == 1) {
                model.addAttribute("msg", s);
                model.addAttribute("user", username);
            } else {
                model.addAttribute("msg", "The username/password entered is incorrect or you havent't logged in yet.");
            }


        return "test";
    }

    @RequestMapping(value = "test/apply", method = RequestMethod.GET)
    public String apply(@CookieValue(value = "verify", defaultValue = "defaultCookieValue") String userid, @ModelAttribute("job") Jobid jc, @RequestParam(value = "apply", defaultValue = "1", required= false) String apply , Model model,HttpServletRequest request) {

            Cookie[] cookies = request.getCookies();
            if (cookies != null && checkCookie(cookies)) {
                try {
                    ConnectDB.getInstance();
                    connection = ConnectDB.getConnection();
                    statement = connection.prepareStatement("Select role from users where userid=?");
                    statement.setString(1, userid);
                    resultSet = statement.executeQuery();
                    resultSet.next();
                    System.out.println("role   " + resultSet.getString("role"));

                    if (resultSet.getString("role").equalsIgnoreCase("C")) {
                        int status = 0;
                        System.out.println("userid   " + userid);
                        System.out.println("jobid   " + jc.getJobid());
                        status = ((implementation) i).applyjob(userid, jc.getJobid());
                        System.out.println("stat  " + status);
                        if (status == 1) {
                            model.addAttribute("applymsg", "You have successfully applied to the job");
                        } else {
                            model.addAttribute("applymsg", "You have already applied to the job");
                        }

                    } else {
                        model.addAttribute("applymsg", "Only candidates logged in can apply, recruiters can only post jobs");

                    }

                } catch (Exception e) {
                    System.out.println("exception" + e);
                }
                return "test";
            }

            else {
                model.addAttribute("loginmsg", "Please login/signup before you apply");
                return "login";
            }
    }


}
