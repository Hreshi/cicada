package org.aissms.cicada.avatar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// add avatars in static/avatar folder
@RestController
public class AvatarController implements CommandLineRunner {
    static List<String> avatarUrlList = new ArrayList<String>();

    @GetMapping("/avatars")
    public List<String> getAvatarList() {
        return avatarUrlList;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Resource res = new ClassPathResource("static/avatar");
            File folder = res.getFile();
            if(folder.isDirectory()) {
                String[] files = folder.list();
                for(String file : files) {
                    avatarUrlList.add("/avatar/"+file);
                }
            }
        } catch(Exception e) {
            System.out.println(e + " cannot load avatar files from avatar folder");
        }
    }
}
