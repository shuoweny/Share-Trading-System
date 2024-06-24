package com.unimelb.swen90007.group404notfound.api.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unimelb.swen90007.group404notfound.api.annotation.NormalToken;
import com.unimelb.swen90007.group404notfound.api.annotation.RolesAllowed;
import com.unimelb.swen90007.group404notfound.api.domain.CompanyListing;
import com.unimelb.swen90007.group404notfound.api.domain.Customer;
import com.unimelb.swen90007.group404notfound.api.domain.Portfolio;
import com.unimelb.swen90007.group404notfound.api.mapper.CompanyListingMapper;
import com.unimelb.swen90007.group404notfound.api.service.*;
import com.unimelb.swen90007.group404notfound.api.service.impl.*;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

//ToDo

//listing id && amount
@WebServlet("/sell")
public class SellController extends HttpServlet {
    @NormalToken
    @RolesAllowed({"Customer"})
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = req.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine())!= null){
                stringBuilder.append(line);
            }
            JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
            Long lid = jsonObject.get("listingid").getAsLong();
            Integer amount = jsonObject.get("amount").getAsInt();
            IPortfolioService portfolioService = new PortfolioServiceImpl();
            Portfolio portfolio = portfolioService.findPortfolioByListingId(lid);
            Long uid = portfolio.getUserId();
            Long sid = portfolio.getShareId();
            Long pid = portfolio.getPortfolioId();
            Long cid = portfolio.getCompanyId();
            ICompanyListingService companyListingService = new CompanyListingServiceImpl();
            BigDecimal totalPrice = companyListingService.getTotalPrice(sid,cid, amount);
            JsonObject jsonRes = new JsonObject();
            UnitOfWork.newCurrent();
            if(totalPrice.compareTo(BigDecimal.ZERO)>=0){
                IInvestListingService investListingService = new InvestListingServiceImpl();
                int res = investListingService.removeOrUpdate(lid, amount);
                if(res == 0 || res == 1){
                    boolean removedPort = true;
                    boolean removedInvest = true;
                    if(res == 0){
                        removedPort = portfolioService.removePortfolio(portfolio);
                        removedInvest = investListingService.removeInvestListing(lid);
                    }
                    if(removedInvest && removedPort){
                        ICustomerService customerService = new CustomerServiceImpl();
                        if(customerService.addBalance(uid, totalPrice)){
                            if(companyListingService.addNumShareToCompanyListing(sid,cid, amount)){
                                ICompanyService companyService = new CompanyServiceImpl();
                                if(companyService.decreaseBalance(cid,totalPrice)){
                                    if (UnitOfWork.getCurrent().commit()){
                                        BigDecimal balance = customerService.getBalanceById(uid);
                                        UnitOfWork.storeResult.clear();
                                        jsonRes.addProperty("status", "success");
                                        jsonRes.addProperty("message", "The customer has successfully sold the share.");
                                        jsonRes.addProperty("balance", balance);
                                    }
                                    else{
                                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                        jsonRes.addProperty("status", "fail");
                                        jsonRes.addProperty("message", "The customer has failed to sold the share.");
                                    }
                                   
                                }else{
                                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                    jsonRes.addProperty("status", "fail");
                                    jsonRes.addProperty("message", "The balance cannot be added to company.");
                                }
                            }else{
                                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                jsonRes.addProperty("status", "fail");
                                jsonRes.addProperty("message", "The amount does not added to company listing");
                            }
                        }else{
                            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            jsonRes.addProperty("status", "fail");
                            jsonRes.addProperty("message", "The money does not added to customer balance.");
                        }
                    }else {
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        jsonRes.addProperty("status", "fail");
                        jsonRes.addProperty("message", "The num of share is empty but fail to delete portfolio.");
                    }
                }else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    jsonRes.addProperty("status", "fail");
                    jsonRes.addProperty("message", "Portfolio or invest listing failed to remove.");
                }
            }else{
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonRes.addProperty("status", "fail");
                jsonRes.addProperty("message", "There does not exist this share type in company.");
            }
            resp.getWriter().write(jsonRes.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
