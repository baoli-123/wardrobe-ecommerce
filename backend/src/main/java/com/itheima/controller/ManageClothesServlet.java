package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.model.Clothes;
import com.itheima.service.ClothesService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ManageClothesServlet  extends HttpServlet {
    ClothesService clothesService = new ClothesService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw=resp.getWriter();
        String action=req.getServletPath();
        ObjectMapper om = new ObjectMapper();
        BufferedReader reqReader=req.getReader();
        switch(action){
            case"/getAllClothesData":
                List<Clothes> allClothes = clothesService.getAllClothesData();
                om.writeValue(pw,allClothes);
                break;
            case"/addClothes":
                Clothes clothes=om.readValue(reqReader,Clothes.class);
                String addMsg=clothesService.addClothes(clothes);
                om.writeValue(pw,addMsg);
                break;
            case"/searchClothes":
                String clothName=req.getParameter("clothName");
                String style=req.getParameter("style");
                String type=req.getParameter("type");
                List<Clothes>searchClothes = clothesService.getClothesByParams(clothName,style,type);
                om.writeValue(pw,searchClothes);
                break;
            case"/editClothes":
                Clothes editClothes=om.readValue(reqReader,Clothes.class);
                String editMsg=clothesService.editClothes(editClothes);
                om.writeValue(pw,editMsg);
                break;
            case"/delClothes":
                Clothes delClothes=om.readValue(reqReader,Clothes.class);
                String delMsg= clothesService.delClothes(delClothes.getId());
                om.writeValue(pw,delMsg);
                break;

        }
    }
}
