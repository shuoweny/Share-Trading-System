package com.unimelb.swen90007.group404notfound.api.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unimelb.swen90007.group404notfound.api.annotation.NormalToken;
import com.unimelb.swen90007.group404notfound.api.annotation.RolesAllowed;
import com.unimelb.swen90007.group404notfound.api.service.IShareService;
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

//ToDo
@WebServlet("/addShare")
public class AddShareController extends HttpServlet {
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

        JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
        Long companyId = jsonObject.get("companyId").getAsLong();
        Character shareType = jsonObject.get("shareType").getAsCharacter();
        BigDecimal price = jsonObject.get("price").getAsBigDecimal();
        synchronized (this) {
            IShareService shareService = new ShareServiceImpl();
            JsonObject shareObject = new JsonObject();
            UnitOfWork.newCurrent();
            int result = shareService.addorDup(companyId, shareType);
            if (result == 1) {
                shareService.addShare(companyId, shareType, price);
                if (UnitOfWork.getCurrent().commit()) {
                    shareObject.addProperty("status", "success");
                    shareObject.addProperty("shareId", UnitOfWork.storeResult.get("shareid"));
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    shareObject.addProperty("status", "fail");
                    shareObject.addProperty("message", "add fail");
                }
            } else if (result == 0) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                shareObject.addProperty("status", "duplicate");
                shareObject.addProperty("message", "the type of share is already exist in your company.");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                shareObject.addProperty("status", "fail");
                shareObject.addProperty("message", "unknown problem happened.");
            }


            resp.getWriter().write(shareObject.toString());
        }
    }
}
