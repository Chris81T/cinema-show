package de.geeksession.javaservice;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;

@Stateless
@Startup
public class JavaService {

    public JavaService() {
        // TODO Auto-generated constructor stub
    }

    public int add(int a, int b) {
        return a + b;
    }

    @PostConstruct
    public void javaDoIt() {
        //call(add(2, 3), ""); -- do
    }

}
