package com.unimelb.swen90007.group404notfound.api.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unimelb.swen90007.group404notfound.api.domain.Company;
import com.unimelb.swen90007.group404notfound.api.service.ICompanyService;
import com.unimelb.swen90007.group404notfound.api.service.impl.CompanyServiceImpl;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/addCompany")
public class AddCompanyController extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
        String companyname = jsonObject.get("companyname").getAsString();
        String category = jsonObject.get("category").getAsString();
        ICompanyService companyService = new CompanyServiceImpl();
        JsonObject companyObject = new JsonObject();
        if (companyService.checkCompanyExist(companyname)){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            companyObject.addProperty("status", "fail");
            companyObject.addProperty("message", "exist");
        }
        else{
            UnitOfWork.newCurrent();
            companyService.addCompany(companyname, category);
            if (UnitOfWork.getCurrent().commit()){
                companyObject.addProperty("status", "success");
                companyObject.addProperty("companyId", UnitOfWork.storeResult.get("companyid"));
                UnitOfWork.storeResult.clear();
            }
            else{
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                companyObject.addProperty("status", "fail");
                companyObject.addProperty("message", "add fail");
            }
        }
        resp.getWriter().write(companyObject.toString());
    }
}
