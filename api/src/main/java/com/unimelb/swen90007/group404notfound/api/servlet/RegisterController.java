package com.unimelb.swen90007.group404notfound.api.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unimelb.swen90007.group404notfound.api.domain.CompanyUser;
import com.unimelb.swen90007.group404notfound.api.domain.Customer;
import com.unimelb.swen90007.group404notfound.api.service.IUserService;
import com.unimelb.swen90007.group404notfound.api.service.impl.UserServiceImpl;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = false;
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader bufferedReader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }
        JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        String roleType = jsonObject.get("roletype").getAsString();
        String lastname = jsonObject.get("lastname").getAsString();
        String firstname = jsonObject.get("firstname").getAsString();
        JsonObject jsonResponse = new JsonObject();
        IUserService userService = new UserServiceImpl();
        if (userService.checkUserExist(username, roleType)){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("status", "fail");
            jsonResponse.addProperty("message", "username already exist");
        }
        else{
            UnitOfWork.newCurrent();
            if (roleType.equals("User")){
                Customer customer = new Customer(null, username, password, lastname, firstname, null, null);
                System.out.println(customer);

                userService.addUser(customer);
                result = UnitOfWork.getCurrent().commit();
            }
            if (roleType.equals("Company")){
                Long companyId = jsonObject.get("companyid").getAsLong();
                CompanyUser companyUser = new CompanyUser(null, username, password, lastname, firstname, null, null, null);
                companyUser.setCompanyId(companyId);
                System.out.println(companyUser);
                userService.addUser(companyUser);
                result = UnitOfWork.getCurrent().commit();
            }

            if (result){
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "Register successful");
            }
            else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.addProperty("status", "fail");
                jsonResponse.addProperty("message", "Register fail");
            }

        }
        resp.getWriter().write(jsonResponse.toString());

    }
}
