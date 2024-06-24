package com.unimelb.swen90007.group404notfound.api.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unimelb.swen90007.group404notfound.api.annotation.NormalToken;
import com.unimelb.swen90007.group404notfound.api.annotation.RolesAllowed;
import com.unimelb.swen90007.group404notfound.api.domain.CompanyListing;
import com.unimelb.swen90007.group404notfound.api.domain.Share;
import com.unimelb.swen90007.group404notfound.api.service.ICompanyListingService;
import com.unimelb.swen90007.group404notfound.api.service.IShareService;
import com.unimelb.swen90007.group404notfound.api.service.impl.CompanyListingServiceImpl;
import com.unimelb.swen90007.group404notfound.api.service.impl.ShareServiceImpl;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

//listingid -- price -- amount
@WebServlet("/updateCompanyListing")
public class UpdateCompanyListingController extends HttpServlet{
    @Override
    @NormalToken
    @RolesAllowed({"Company"})
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        JsonObject jsonResponse = new JsonObject();
        JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
        Long listingid = jsonObject.get("listingid").getAsLong();
        BigDecimal price = jsonObject.get("price").getAsBigDecimal();
        Integer amount = jsonObject.get("amount").getAsInt();
        UnitOfWork.newCurrent();
        ICompanyListingService companyListingService = new CompanyListingServiceImpl();
        Long sid = companyListingService.getShareIdByListingId(listingid);
        IShareService shareService = new ShareServiceImpl();
        //check price
        Share share = shareService.getShareById(sid);

        if(!share.getPrice().equals(price)) {
            shareService.addShare(share.getCompanyId(), share.getShareType(), price);
            sid = UnitOfWork.storeResult.get("shareid");
            System.out.println("Add share check Price: " + share.getPrice() + "   "+ "getted price: " + price);
        }
        companyListingService.updateListing(listingid, sid, amount);
        if(UnitOfWork.getCurrent().commit()){
            jsonResponse.addProperty("status", "success");
            jsonResponse.addProperty("message", "Updated amount and price.");
        }else{
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("status", "fail");
            jsonResponse.addProperty("message", "failed updating amount and price.");
        }
        resp.getWriter().write(jsonResponse.toString());
    }
}
