package com.nonono.test._springboot.controller;

import com.nonono.test._springboot.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("thymeleaf")
public class ThymeleafController {

    @RequestMapping("/")
    public String thymeleafIndex(Model model) {
        Person single = new Person("帝国", 12, "");

        List<Person> people = new ArrayList<Person>();
        people.add(Person.builder().name("吸血鬼").age(1000).build());
        people.add(Person.builder().name("矮人").age(200).build());
        people.add(Person.builder().name("混沌").age(300).build());
        people.add(Person.builder().name("木精灵").age(30000).build());

        model.addAttribute("singlePerson", single);
        model.addAttribute("people", people);

        return "index";
    }

}
