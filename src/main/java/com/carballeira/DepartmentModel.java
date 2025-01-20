package com.carballeira;

public class DepartmentModel {
    private int deptNum;
    private String deptName;

    public DepartmentModel(int deptNum, String deptName) {
        this.deptNum = deptNum;
        this.deptName = deptName;
    }

    public boolean validDept(){
        if(deptNum == 0 || deptName.isEmpty() || deptName.isBlank()){
            return false;
        }
        return true;
    }


    public int getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(int deptNum) {
        this.deptNum = deptNum;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
