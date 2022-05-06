package com.yfh.controller;

import com.yfh.bean.Employee;
import com.yfh.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired // 默认byType，不行会根据byName
    private EmployeeDao employeeDao;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String getEmpList(Model model) {
        Collection<Employee> empList = employeeDao.getAll();
        model.addAttribute("empList", empList);
        return "employee_list";
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public String deleteEmpById(@PathVariable Integer id) {
        employeeDao.delete(id);
        return "redirect:/employee"; // 删除成功后，与原来的请求没关系了，应该用重定向
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public String addEmp(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/employee";
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public String getEmpById(@PathVariable Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee", employee);
        return "update_index";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public String updateEmp(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/employee";
    }

}
