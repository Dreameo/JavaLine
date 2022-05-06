package com.yfh.beans;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Student {
    // 1. 数组类型的属性
    private String[] course;

    // 2. 集合类型的属性
    private List<String> list;

    // 3. map类型的属
    private Map<String, String> map;

    // 4. set类型的属性
    private Set<String> set;

    public void setCourse(String[] course) {
        this.course = course;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    @Override
    public String toString() {
        return "Student{" +
                "course=" + Arrays.toString(course) +
                ", list=" + list +
                ", map=" + map +
                ", set=" + set +
                '}';
    }
}
