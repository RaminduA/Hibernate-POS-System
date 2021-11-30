package dataAccessObject.custom.impl;

import dataAccessObject.custom.ItemDAO;
import entity.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?)",item.getItemCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice(),item.getDiscountPercent());

    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, qtyOnHand=?, unitPrice=?, discountPercent=? WHERE itemCode=?",item.getDescription(),item.getQtyOnHand(),item.getUnitPrice(),item.getDiscountPercent(),item.getItemCode());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?",id);
    }

    @Override
    public Item get(String id) throws SQLException, ClassNotFoundException {
        Item item=null;
        ResultSet resultSet=CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?",id);
        if(resultSet.next()){
            item=new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            );
        }
        return item;
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items=new ArrayList<>();
        ResultSet resultSet=CrudUtil.executeQuery("SELECT * FROM Item");
        while(resultSet.next()){
            items.add(
                    new Item(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5)
                    )
            );
        }
        return items;
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1");
        if(resultSet.next()){
            int index=Integer.parseInt(resultSet.getString(1).split("-")[1]);
            if(index<9){
                return "I-000"+ ++index;
            }else if(index<99){
                return "I-00"+ ++index;
            }else if(index<999){
                return "I-0"+ ++index;
            }else{
                return "I-"+ ++index;
            }
        }else{
            return "I-0001";
        }
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> items=new ArrayList<>();
        ResultSet resultSet=CrudUtil.executeQuery("SELECT itemCode FROM Item");
        while(resultSet.next()){
            items.add(resultSet.getString(1));
        }
        return items;
    }

}
