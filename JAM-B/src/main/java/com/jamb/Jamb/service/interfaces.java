package com.jamb.Jamb.service;

import com.jamb.Jamb.model.Jobid;

public interface interfaces {

    public Jobid search(int jobid);
    public int applyjob(String userid, int jobid);

}
