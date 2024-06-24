package com.unimelb.swen90007.group404notfound.api.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.unimelb.swen90007.group404notfound.api.annotation.RolesAllowed;
import com.unimelb.swen90007.group404notfound.api.domain.Listing;

import com.unimelb.swen90007.group404notfound.api.annotation.NormalToken;

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
//For frontend provide a json with: 1."userid" of who buys it 2. The "listingid" of company listing 3. the "amount" of purchase
//please use exactly those two quoted words in json
@WebServlet("/purchase")
public class PurchaseController extends HttpServlet {

    @NormalToken
    @RolesAllowed({"Customer"})
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            BufferedReader bufferedReader = req.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine())!= null){
                stringBuilder.append(line);
            }
            JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
            //Who buys the share
            Long userId = jsonObject.get("userid").getAsLong();
            //the listing that the customer want to buy
            Long companyListingId = jsonObject.get("listingid").getAsLong();
            //the amount the customer want to purchase the share
            Integer amount = jsonObject.get("amount").getAsInt();
            JsonObject jsonRes = new JsonObject();

            ICustomerService customerService = new CustomerServiceImpl();
            UnitOfWork.newCurrent();
            if(customerService.buy(userId,companyListingId, amount)){
                ICompanyListingService companyListingService = new CompanyListingServiceImpl();
                Long sid = companyListingService.sell(companyListingId, amount);
                if(sid != -1L){
                    IShareService shareService = new ShareServiceImpl();
                    Long cid = shareService.getCompanyIdbyId(sid);
                    ICompanyService companyService = new CompanyServiceImpl();
                    if(companyService.earn(cid, sid, amount)){
                        //check whether we have a customer listing with the same uid, sid and "user"type
                        //as portfolio->lid is all from "user", we can check portfolio where uid, sid, equals.
                        IPortfolioService portfolioService = new PortfolioServiceImpl();
                        Long lid = portfolioService.checkExisted(userId,sid);
                        IInvestListingService customerListingService = new InvestListingServiceImpl();
                        if(lid == -1L) {
                            //create
                            Listing listing = customerListingService.addListing(sid, amount);

                            portfolioService.addPortfolio(userId, listing.getListingId(), sid, cid);
                            boolean result =  UnitOfWork.getCurrent().commit();
                            UnitOfWork.storeResult.clear();
                            if (result){
                                BigDecimal balance = customerService.getBalanceById(userId);
                                jsonRes.addProperty("status", "success");
                                jsonRes.addProperty("message", "The purchase is successful.");
                                jsonRes.addProperty("balance", balance);
                            }else{
                                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                    jsonRes.addProperty("status", "fail");
                                    jsonRes.addProperty("message", "Error on updating num of share->customer.");
                            }



                        }else{
                            //update customer listing balance
                            customerListingService.updateNumShare(lid,amount);
                            if(UnitOfWork.getCurrent().commit()){
                                jsonRes.addProperty("status", "success");
                                jsonRes.addProperty("message", "The purchase is successful.");
                            }else{
                                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                jsonRes.addProperty("status", "fail");
                                jsonRes.addProperty("message", "Error on updating num of share->customer.");
                            }

                        }

                    }else {
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        jsonRes.addProperty("status", "fail");
                        jsonRes.addProperty("message", "Error on transfer money to company.");
                    }


                }else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    jsonRes.addProperty("status", "fail");
                    jsonRes.addProperty("message", "There is not that much shares");
                }
            }else{
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonRes.addProperty("status", "fail");
                jsonRes.addProperty("message", "You do not have enough money");
            }


            resp.getWriter().write(jsonRes.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
