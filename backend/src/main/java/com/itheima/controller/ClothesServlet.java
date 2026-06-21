package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.model.Clothes;
import com.itheima.model.Type;
import com.itheima.service.ClothesService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ClothesServlet  extends HttpServlet {
    ClothesService clothesService = new ClothesService();

    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        String action = req.getServletPath();
        ObjectMapper om = new ObjectMapper();
        switch (action) {
            case "/allClothes":
                String style = req.getParameter("style");
                String type = req.getParameter("type");
                List<Clothes> allClothes =
                        clothesService.getAllClothes(style, type);
                om.writeValue(pw, allClothes);
                break;
            case "/allStyles":
                List<String> allStyles = clothesService.getAllStyles();
                om.writeValue(pw, allStyles);
                break;
            case "/allTypes":
                List<Type> allTypes = clothesService.getAllTypes();
                om.writeValue(pw, allTypes);
                break;
            case "/clothesByName":
                String name = req.getParameter("clothesName");
                List<Clothes> clothes = clothesService.getClothesByName(name);
                om.writeValue(pw,clothes);
                break;
            case "/clothDetails":
                String clothIdParam = req.getParameter("clothId");
                if (clothIdParam == null || clothIdParam.isEmpty()) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    om.writeValue(pw, "缺少clothId参数");
                    break;
                }
                int clothId;
                try {
                    clothId = Integer.parseInt(clothIdParam);
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    om.writeValue(pw, "clothId参数格式错误");
                    break;
                }
                Clothes clothDetails = clothesService.getClothDetails(clothId);
                om.writeValue(pw, clothDetails);
                break;
        }
    }
}
