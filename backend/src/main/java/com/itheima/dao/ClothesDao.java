package com.itheima.dao;

import com.itheima.model.Clothes;
import com.itheima.model.Size;
import com.itheima.model.Type;
import com.itheima.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.eclipse.jdt.internal.compiler.ast.Literal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClothesDao {
    public List<String> getStyles() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT style FROM t_clothes GROUP BY style";
        List<String> styles = qr.query(sql,new ColumnListHandler<>());
        return styles;
    }
    public List<Type> getTypes() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT id, type_name AS typeName FROM t_type";
        List<Type> types = qr.query(sql,
                new BeanListHandler<>(Type.class));
        return types;
    }
    public List<Clothes> getAllClothes(String style, String type) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        StringBuilder sql = new StringBuilder(
                "SELECT t_clothes.id, cloth_name AS clothName, TRIM(image) AS image, " +
                        "type_name AS typeName, style, price " +
                        "FROM t_clothes JOIN t_type ON t_clothes.type_id = t_type.id " +
                        "WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();
        if (style != null && !"".equals(style)) {
            sql.append("AND style = ? ");
            params.add(style);
        }
        if (type != null && !"".equals(type)) {
            sql.append("AND type_name = ? ");
            params.add(type);
        }
        return qr.query(sql.toString(), new BeanListHandler<>(Clothes.class),
                params.toArray());
    }
    public List<Clothes>getClothesByName(String name){
        try{
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT t_clothes.id, cloth_name AS clothName, TRIM(image) AS image, " +
                    "type_name AS typeName, style, price FROM t_clothes JOIN t_type" +
                    " ON t_clothes.type_id = t_type.id WHERE cloth_name LIKE ?";
            List<Clothes> clothes = qr.query(sql,new BeanListHandler<>(Clothes.class), "%" + name + "%");
            return clothes;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Size> getSizeByType(int typeId) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT t_size.id, size_name AS sizeName " +
                "FROM t_size JOIN t_type ON t_size.type_id = t_type.id " +
                "WHERE t_size.type_id = ?";
        List<Size> sizeList = qr.query(sql,new BeanListHandler<>(Size.class),typeId);
        return sizeList;
    }
    public Clothes getClothById(int id) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT t_clothes.id, cloth_name AS clothName, TRIM(image) AS image, " +
                "t_type.id AS typeId, t_type.type_name AS typeName, " +
                "t_clothes.style, t_clothes.price " +
                "FROM t_clothes JOIN t_type ON t_clothes.type_id = t_type.id " +
                "WHERE t_clothes.id = ?";
        Clothes cloth = qr.query(sql, new BeanHandler<>(Clothes.class), id);
        if (cloth == null) {
            return null;
        }
        int typeId = cloth.getTypeId();
        List<Size> sizeByType = getSizeByType(typeId);
        cloth.setSizeList(sizeByType);
        return cloth;
    }
    public int addClothes(Clothes clothes) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "INSERT INTO t_clothes(`cloth_name`, image, type_id, " +
                "style, price) VALUES(?, ?, ?, ?, ?)";
        Object[]params={clothes.getClothName(),clothes.getImage(),clothes.getTypeId(),clothes.getStyle(),clothes.getPrice()};
        return qr.update(sql,params);
    }
    public List<Clothes>getClothesByParams(String clothName,String style,String type) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        StringBuilder sql = new StringBuilder().append(
                "SELECT t_clothes.id, cloth_name AS clothName, " +
                "TRIM(image) AS image, type_id AS typeId, type_name AS typeName, " +
                "style, price FROM t_clothes JOIN t_type ON " +
                "type_id = t_type.id WHERE 1=1 ");
        List<Object>params = new ArrayList<>();
        if(clothName!=null&&!clothName.isEmpty()){
            sql.append(" AND cloth_name LIKE ? ");
            params.add("%"+clothName+"%");
        }
        if(style!=null&&!style.isEmpty()){
            sql.append(" AND style = ? ");
            params.add(style);
        }
        if(type!=null&&!type.isEmpty()){
            sql.append(" AND type_name = ? ");
            params.add(type);
        }
        return qr.query(sql.toString(),new BeanListHandler<>(Clothes.class),params.toArray());
    }
    public int editClothes(Clothes editClothes) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "UPDATE t_clothes SET cloth_name = ?, image = ?, " +
                "type_id = ?, style = ?, price = ? WHERE id = ?";
        Object[]params={editClothes.getClothName(),editClothes.getImage(),editClothes.getTypeId(),editClothes.getStyle(),editClothes.getPrice(),editClothes.getId()};
        return qr.update(sql,params);
    }
    public int deleteClothes(int id) throws SQLException{
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "DELETE FROM t_clothes WHERE id = ?";
        return qr.update(sql,id);
    }

    }
