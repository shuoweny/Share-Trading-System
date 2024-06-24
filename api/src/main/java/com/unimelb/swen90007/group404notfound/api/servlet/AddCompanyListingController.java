package com.unimelb.swen90007.group404notfound.api.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unimelb.swen90007.group404notfound.api.annotation.NormalToken;
import com.unimelb.swen90007.group404notfound.api.annotation.RolesAllowed;
import com.unimelb.swen90007.group404notfound.api.domain.Company;
import com.unimelb.swen90007.group404notfound.api.domain.CompanyListing;
import com.unimelb.swen90007.group404notfound.api.domain.Share;
import com.unimelb.swen90007.group404notfound.api.mapper.ShareMapper;
import com.unimelb.swen90007.group404notfound.api.service.ICompanyListingService;
import com.unimelb.swen90007.group404notfound.api.service.ICompanyService;
import com.unimelb.swen90007.group404notfound.api.service.impl.CompanyListingServiceImpl;
import com.unimelb.swen90007.group404notfound.api.service.impl.CompanyServiceImpl;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

//ToDo
@WebServlet("/addCompanyListing")
public class AddCompanyListingController extends HttpServlet {
    @NormalToken
    @RolesAllowed({"Company"})
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        synchronized (this) {
            JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
            Long shareId = jsonObject.get("shareId").getAsLong();
            Integer numShare = jsonObject.get("numShare").getAsInt();
            ICompanyListingService companyListingService = new CompanyListingServiceImpl();
            ShareMapper shareMapper = new ShareMapper();
            Share existingShare = shareMapper.findShareById(shareId);
            CompanyListing checkExistListing = companyListingService.checkExistListing(shareId);
            JsonObject companyListingObject = new JsonObject();

            if (existingShare != null && checkExistListing == null) {
                UnitOfWork.newCurrent();
                companyListingService.addCompanyListing(shareId, numShare);

                if (UnitOfWork.getCurrent().commit()) {
                    companyListingObject.addProperty("status", "success");
                    companyListingObject.addProperty("companyListingId", UnitOfWork.storeResult.get("listingid"));
                    UnitOfWork.storeResult.clear();
                }
//            } else if (existingShare != null && checkExistListing != null) {
////                UnitOfWork.newCurrent();
////                UnitOfWork.storeResult.put("shareid", checkExistListing.getShareId());
////                companyListingService.UpdateAmount(checkExistListing.getListingId(), numShare);
////                if (UnitOfWork.getCurrent().commit()) {
////                    companyListingObject.addProperty("status", "success");
////                    companyListingObject.addProperty("companyListingId", checkExistListing.getListingId());
////                    UnitOfWork.storeResult.clear();
//                }
                else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    companyListingObject.addProperty("status", "fail");
                    companyListingObject.addProperty("message", "added failed");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                companyListingObject.addProperty("status", "fail");
                companyListingObject.addProperty("message", "Already exist");
            }


            resp.getWriter().write(companyListingObject.toString());
        }
    }
}
