package com.jamb.Jamb.dao;

import com.jamb.Jamb.model.Jobid;
import com.jamb.Jamb.model.users;
import com.jamb.Jamb.util.ConnectDB;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class dao {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public Jobid jobdao(int jobid) {
        Jobid j = new Jobid();
        try {
            ConnectDB.getInstance();
            connection = ConnectDB.getConnection();
            statement = connection.prepareStatement("select * from jobs where jobid = ?");
            statement.setInt(1, jobid);
            resultSet = statement.executeQuery();
            System.out.println("result" + resultSet.toString());
            while (resultSet.next()) {

                j.setJobid(resultSet.getInt("jobid"));
                j.setJobrole(resultSet.getString("jobrole"));
                j.setJobdesc(resultSet.getString("jobdesc"));
            }
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        return j;
    }


public int applyjob(String userid, int jobid) {
    int status = 0;
    boolean b = true;
    try {
        ConnectDB.getInstance();
        connection = ConnectDB.getConnection();
        statement = connection.prepareStatement("insert into jobapplication(jobid, userid) values (?,?)");
        statement.setInt(1, jobid);
        statement.setString(2, userid);
        b = statement.execute();
        if (b)
            status = 1;
    } catch (Exception e) {
        System.out.println("exception" + e);
    }
return status;
}
}



