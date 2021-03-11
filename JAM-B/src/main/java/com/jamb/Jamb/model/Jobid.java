package com.jamb.Jamb.model;

public class Jobid {
    private int jobid;
    private String jobrole;
    private String jobdesc;

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public String getJobrole() {
        return jobrole;
    }
        public void setJobrole (String jobrole){
            this.jobrole = jobrole;
        }

        public String getJobdesc () {
            return jobdesc;
        }

        public void setJobdesc (String jobdesc){
            this.jobdesc = jobdesc;
        }
    }
