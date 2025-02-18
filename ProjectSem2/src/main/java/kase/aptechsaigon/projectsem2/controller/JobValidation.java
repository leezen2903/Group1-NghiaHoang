/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kase.aptechsaigon.projectsem2.controller;

import java.util.Date;

/**
 *
 * @author Moiiii
 */
public class JobValidation {
    public boolean checkDate(Date startDate, Date endDate){
        return startDate.compareTo(endDate) <= 0;
    }
}
