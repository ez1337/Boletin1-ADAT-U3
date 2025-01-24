package com.carballeira;

public class DepartmentModel {
    private int deptNum;
    private String deptName;
    private String location;

    public DepartmentModel(){}

    public DepartmentModel(int deptNum, String deptName, String location) {
        this.deptNum = deptNum;
        this.deptName = deptName;
        this.location = location;
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

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    @Override
    public String toString() {
        return "Número de departamento: "+deptNum+ "\n" +
                "Nombre de departamento: "+deptName+ "\n" +
                "Localización: "+location;
    }
}
