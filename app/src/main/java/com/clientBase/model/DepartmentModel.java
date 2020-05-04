package com.clientBase.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/4/14.
 */

public class DepartmentModel implements Serializable {

    private String departmentsId;
    private String departmentsName;


    public String getDepartmentsId() {
        return departmentsId;
    }

    public void setDepartmentsId(String departmentsId) {
        this.departmentsId = departmentsId;
    }

    public String getDepartmentsName() {
        return departmentsName;
    }

    public void setDepartmentsName(String departmentsName) {
        this.departmentsName = departmentsName;
    }
}
