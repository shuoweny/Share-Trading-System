package com.unimelb.swen90007.group404notfound.api.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.unimelb.swen90007.group404notfound.api.annotation.NormalToken;
import com.unimelb.swen90007.group404notfound.api.annotation.RolesAllowed;
import com.unimelb.swen90007.group404notfound.api.dto.ListingDetailDTO;
import com.unimelb.swen90007.group404notfound.api.service.ICompanyListingService;
import com.unimelb.swen90007.group404notfound.api.service.impl.CompanyListingServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/viewCompanyListings")
public class ViewCompanyListingController extends HttpServlet {

    @NormalToken
    @RolesAllowed({"Admin","Customer"})
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JsonArray companiesArray = new JsonArray();
        List<ListingDetailDTO> companyListings = null;
        ICompanyListingService companyListingService = new CompanyListingServiceImpl();

        companyListings = companyListingService.findAllCompanyListing();
        String companyNameFilter = request.getParameter("companyname");
        String shareTypeFilter = request.getParameter("shareType");

        if (companyNameFilter != null && !companyNameFilter.isEmpty()) {
            companyListings = companyListings.stream()
                    .filter(listing -> listing.getCompanyName().equalsIgnoreCase(companyNameFilter))
                    .collect(Collectors.toList());
        }

        if (shareTypeFilter != null && !shareTypeFilter.isEmpty()) {
            companyListings = companyListings.stream()
                    .filter(listing -> listing.getShareType().toString().equalsIgnoreCase(shareTypeFilter))
                    .collect(Collectors.toList());
        }

        for (ListingDetailDTO companyListing : companyListings) {
            JsonObject companyObject = new JsonObject();
            companyObject.addProperty("companyname", companyListing.getCompanyName());
            companyObject.addProperty("category", companyListing.getCategory());
            companyObject.addProperty("shareType", companyListing.getShareType());
            companyObject.addProperty("numShare", companyListing.getNumShare());
            companyObject.addProperty("price", companyListing.getPrice());
            companyObject.addProperty("listingid", companyListing.getId());
            companiesArray.add(companyObject);
        }

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("status", "success");
        jsonResponse.add("companyListings", companiesArray);

        response.getWriter().write(jsonResponse.toString());
    }

}
