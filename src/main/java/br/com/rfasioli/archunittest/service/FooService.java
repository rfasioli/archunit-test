package br.com.rfasioli.archunittest.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import br.com.rfasioli.archunittest.persistence.FooDao;

public class FooService {

    FooDao dao = new FooDao();

    public static void main(String[] args) {
        System.out.println("Hello");

        Boolean var1 = Boolean.parseBoolean("TrUe");
        System.out.println(var1);

        StringBuilder sb = new StringBuilder("abc");
        sb.append("def");
        System.out.println(sb.toString());

        System.out.println(sb + "gh");        

        List<String> lst = Arrays.asList("A", "B", "C");

        Iterator<String> it = lst.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }

        lst.forEach(s -> System.out.println(s));

        lst.forEach(System.out::println);

        lst.stream().forEach(System.out::println);

        String s = "Hello World!";
        System.out.println(s.substring(6, 12));
        System.out.println(s.substring(6, 12)+s.substring(12, 5));

    }
    
}
