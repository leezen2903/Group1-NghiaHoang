package kase.aptechsaigon.projectsem2.controller;

import java.util.Date;

public class JobValidation {
    // kiểm tra startDate phải nhỏ hơn endDate và lớn hơn ngày hiện tại
    public boolean checkDate(Date startDate, Date endDate) {
        Date currentDate = new Date(); // lấy ngày hiện tại

        // startDate phải lớn hơn ngày hiện tại và nhỏ hơn endDate
        if (startDate.after(currentDate) && endDate.after(startDate)) {
            return true;
        }
        return false;
    }
}
