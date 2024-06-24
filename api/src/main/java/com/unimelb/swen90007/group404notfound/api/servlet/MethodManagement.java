package com.unimelb.swen90007.group404notfound.api.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MethodManagement {
    public static Map<String, Method> methodMap = new HashMap<>();

    static {
        try {
            methodMap.put("/404notfound-api/login", LoginController.class.getMethod("doPost", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/viewCustomers", AdminViewAllCustomerController.class.getMethod("doGet", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/purchase", PurchaseController.class.getMethod("doPost", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/portfolioDetails", PortfolioDetailsController.class.getMethod("doGet", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/register", RegisterController.class.getMethod("doPost", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/viewCompanies", ViewCompaniesController.class.getMethod("doGet", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/viewCompanyListings", ViewCompanyListingController.class.getMethod("doGet", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/addCompany", AddCompanyController.class.getMethod("doPost", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/addCompanyListing", AddCompanyListingController.class.getMethod("doPost", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/addShare", AddShareController.class.getMethod("doPost", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/sell", SellController.class.getMethod("doPost", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/deleteCompanyListing", DeleteCompanyListingController.class.getMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/companyViewListings", CompanyViewListingController.class.getMethod("doGet", HttpServletRequest.class, HttpServletResponse.class));
            methodMap.put("/404notfound-api/updateCompanyListing", UpdateCompanyListingController.class.getMethod("doPost", HttpServletRequest.class, HttpServletResponse.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public static Method getMethodByURI(String uri) {
        return methodMap.get(uri);
    }
}
