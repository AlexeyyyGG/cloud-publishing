package com.cloud.publishing.common.constants.employee;

import com.cloud.publishing.common.constants.Urls;

public class EmployeePages {
    private EmployeePages() {
    }

    public static final String LIST = "employees/employees";
    public static final String NEW = "employees/new";
    public static final String EDIT = "employees/edit";
    public static final String REDIRECT_EMPLOYEES = "redirect:" + Urls.WEB_EMPLOYEES;
}
