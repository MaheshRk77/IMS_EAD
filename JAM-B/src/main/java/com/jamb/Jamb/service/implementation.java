package com.jamb.Jamb.service;

import com.jamb.Jamb.dao.dao;
import com.jamb.Jamb.model.Jobid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class implementation implements interfaces{

    dao d;
@Autowired
    public implementation(dao d)
    {
        this.d = d;
    }

    @Override
    public Jobid search(int jobid) {
        Jobid id = new Jobid();
        id = d.jobdao(jobid);
        return id;

    }


    public int applyjob(String userid, int jobid){
    int status = 0;
    status= d.applyjob(userid,jobid);
    return status;
    }
}
