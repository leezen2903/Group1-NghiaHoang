/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kase.aptechsaigon.projectsem2;

public class DeptDTO {
    private int departmentID;
    private String departmentName;
    
    public DeptDTO(int id, String name) {
        this.departmentID = id;
        this.departmentName = name;
    }
    
    public int getDepartmentID() {
        return departmentID;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    @Override
    public String toString() {
        return departmentName;
    }
}
