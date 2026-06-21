package com.itheima.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MultipartConfig(maxFileSize = 10 * 1024 * 1024, maxRequestSize = 20 * 1024 * 1024)
public class UploadFileServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        String targetPath = req.getServletContext().getRealPath("/images");
        Part part = req.getPart("clothesImage");
        String fileName = part.getSubmittedFileName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestampPrefix = formatter.format(LocalDateTime.now());
        String newFileName = timestampPrefix + "_" + fileName;
        String TomcatPath = targetPath + "/" + newFileName;
        part.write(TomcatPath);
        pw.println(newFileName);
    }
}