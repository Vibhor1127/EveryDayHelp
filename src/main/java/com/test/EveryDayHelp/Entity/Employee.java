package com.test.EveryDayHelp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

        @Id
            private String employee_id;
        @Column
            private String name;
    @Column
                            private  String phone;
    @Column                                private  String qualification;
    @Column                 private String experience;
    @Column                 private  String gender;
    @Column                         private String employee_type; // dropdown (gardener,...)
    @Column
    private String service_type; // long term service (LTS) , STS // radiobutton
        private String employee_pic;
            private  String charges;
                private  String description;

    public Employee() {
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmployee_type() {
        return employee_type;
    }

    public void setEmployee_type(String employee_type) {
        this.employee_type = employee_type;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getEmployee_pic() {
        return employee_pic;
    }

    public void setEmployee_pic(String employee_pic) {
        this.employee_pic = employee_pic;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee(String employee_id, String name, String phone, String qualification, String experience, String gender, String employee_type, String service_type, String employee_pic, String charges, String description) {
        this.employee_id = employee_id;
        this.name = name;
        this.phone = phone;
        this.qualification = qualification;
        this.experience = experience;
        this.gender = gender;
        this.employee_type = employee_type;
        this.service_type = service_type;
        this.employee_pic = employee_pic;
        this.charges = charges;
        this.description = description;
    }
}
