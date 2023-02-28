package org.dev.toptenplaylist;

import org.dev.toptenplaylist.model.UserCredentials;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class TopTenPlaylistApplication {
    public static void main(String[] args) {
        // TEST START
        HashMap<Integer, UserCredentials> testMap = new HashMap<>();
        UserCredentials test = new UserCredentials();
        test.setName("n1");
        test.setPassword("p1");
        testMap.put(1, test);
        test.setPassword("p2");
        System.out.println(testMap.get(1).getPassword());
        // TEST FINISH
        SpringApplication.run(TopTenPlaylistApplication.class, args);
    }
}
