package dataAccessObject.custom.impl;

import dataAccessObject.custom.OrderDetailDAO;
import entity.OrderDetail;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean add(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order Detail` VALUES(?,?,?,?,?)",orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getOrderQty(),orderDetail.getDiscount(),orderDetail.getPrice());
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `Order Detail` SET orderQty=?, discount=?, price=? WHERE orderId=? AND itemCode=?",orderDetail.getOrderQty(),orderDetail.getDiscount(),orderDetail.getPrice(),orderDetail.getOrderId(),orderDetail.getItemCode());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(String orderId, String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Order Detail` WHERE orderId=? AND itemCode=?",orderId,itemCode);
    }

    @Override
    public OrderDetail get(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetail get(String orderId, String itemCode) throws SQLException, ClassNotFoundException {
        OrderDetail orderDetail=null;
        ResultSet resultSet=CrudUtil.executeQuery("SELECT * FROM `Order Detail` WHERE orderId=? AND itemCode=?",orderId,itemCode);
        if(resultSet.next()){
            orderDetail=new OrderDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            );
        }
        return orderDetail;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetail> getAll(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderDetails=new ArrayList<>();
        ResultSet resultSet=CrudUtil.executeQuery("SELECT * FROM `Order Detail` WHERE orderId=?",orderId);
        while(resultSet.next()){
            orderDetails.add(
                    new OrderDetail(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5)
                    )
            );
        }
        return orderDetails;
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

}
