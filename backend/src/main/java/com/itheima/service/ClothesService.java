package com.itheima.service;

import com.itheima.dao.ClothesDao;
import com.itheima.model.Clothes;
import com.itheima.model.Size;
import com.itheima.model.Type;

import java.util.List;

public class ClothesService {
    ClothesDao clothesDao = new ClothesDao();
    public List<Clothes> getAllClothes(String style,String type) {
        try{
            return clothesDao.getAllClothes(style,type);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getAllStyles() {
        try{
            return clothesDao.getStyles();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Type> getAllTypes() {
        try{
            return clothesDao.getTypes();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
public List<Clothes> getClothesByName(String name) {
        try{
            return clothesDao.getClothesByName(name);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
}
public Clothes getClothDetails(int id){
        try{
            Clothes clothById = clothesDao.getClothById(id);
            if (clothById == null) {
                return null;
            }
            return clothById;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
}
public List<Clothes>getAllClothesData(){
    try{
        List<Clothes>allClothes = clothesDao.getClothesByParams(null,null,null);
        for(Clothes c:allClothes){
            List<Size>sizeList = clothesDao.getSizeByType(c.getTypeId());
            c.setSizeList(sizeList);
        }
        return allClothes;
    }catch(Exception e){
        throw new RuntimeException(e);
    }
}
public String addClothes(Clothes clothes){
    try{
        int count=clothesDao.addClothes(clothes);
        if(count>0){
            return"上架成功";
        }else{
            return"上架失败";
        }
    }catch(Exception e){
        throw new RuntimeException(e);
    }
}
public List<Clothes>getClothesByParams(String clothName,String style,String type){
    try{
        List<Clothes> clothes = clothesDao.getClothesByParams(clothName,style,type);
        for(Clothes c : clothes){
            List<Size>sizeList = clothesDao.getSizeByType(c.getTypeId());
            c.setSizeList(sizeList);
        }
        return clothes;
    }catch(Exception e){
        throw new RuntimeException(e);
    }
}
public String delClothes(int id){
    try{
        int count = clothesDao.deleteClothes(id);
        if(count>0){
            return"下架服装成功！";
        }else{
            return"下架失败！";
        }
    }catch(Exception e){
        throw new RuntimeException(e);
    }
}
public String editClothes(Clothes editClothes){
    try{
        int count=clothesDao.editClothes(editClothes);
        if(count>0){
            return"修改成功！";
        }else{
            return"修改失败！";
        }
    }catch(Exception e){
        throw new RuntimeException(e);
    }
}
}
