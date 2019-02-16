package com.example.whiteboardsp19likithponnanna.services;

import com.example.whiteboardsp19likithponnanna.model.Course;
import com.example.whiteboardsp19likithponnanna.model.Lesson;
import com.example.whiteboardsp19likithponnanna.model.Module;
import com.example.whiteboardsp19likithponnanna.model.Topic;
import com.example.whiteboardsp19likithponnanna.model.User;
import com.example.whiteboardsp19likithponnanna.model.Widget;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

@RestController
public class UserService {
  private Widget startWidget  = new Widget((long)123, "HEADING", "1", "Doc obj model",
          "Heading Name","Paragraph text","Paragraph Name","UNORDERED","Nodes,Nodes1", "List Name",
          "https://picsum.photos/200", "Image Name", "Link Text",
          "https://en.wikipedia.org/wiki/Document_Object_Model","Link Name");
  private List<Widget> widgets = new ArrayList <>(Arrays.asList(startWidget));

  private Topic startTopic = new Topic((long) 123,"Topic Title", widgets);

  private List<Topic> topics = new ArrayList <>(Arrays.asList(startTopic));

  private Lesson startLesson  = new Lesson((long) 123, "Lesson Title", topics);

  private List<Lesson> lessons = new ArrayList <>(Arrays.asList(startLesson));

  private Module startModule = new Module((long)123, "Module List", lessons );

  private List<Module> modules = new ArrayList <>(Arrays.asList(startModule));

  private Course startCourse = new Course((long) 123,"Course Test 1", modules);
  private Course startCourse1 = new Course((long) 234,"Course Test 2", modules);

  private List<Course> courses = new ArrayList <>(Arrays.asList(startCourse,startCourse1));



  private User alice = new User((long) 123, "alice", "pass123", "Alice", "Wonderland", "FACULTY",courses);
  private User bob = new User((long) 234, "bob", "pass245", "Bob", "Marley", "FACULTY", courses);
  private ArrayList <User> users = new ArrayList <>(Arrays.asList(alice, bob));


  @PostMapping("/api/register")
  public User register(@RequestBody User user,
                       HttpSession session) {
    session.setAttribute("currentUser", user);
    users.add(user);
    return user;
  }

  @GetMapping("/api/profile")
  public User profile(HttpSession session) {
    User currentUser = (User)
            session.getAttribute("currentUser");
    return currentUser;
  }



  @PostMapping("/api/login")
  public User login(	@RequestBody User credentials,
                      HttpSession session) {
    for (User user : users) {
      if( user.getUsername().equals(credentials.getUsername())
              && user.getPassword().equals(credentials.getPassword())) {
        session.setAttribute("currentUser", user);
        return user;
      }
    }
    return null;
  }

  @PostMapping("/api/logout")
  public void logout
          (HttpSession session) {
    session.invalidate();
  }








  @GetMapping("/api/users")
  public ArrayList <User> findAllUsers() {
    return users;
  }

  @GetMapping("/api/users/{id}")
  public User findUserById(@PathVariable("id") Long id) {
    for (int i = 0; i < users.size(); i++) {
      if (id.equals(users.get(i).getUserId())) {
        return users.get(i);
      }
    }
    return null;
  }

  @PostMapping("/api/users")
  public User createUser(@RequestBody User user) {
    users.add(user);
    return user;
  }

  @DeleteMapping("/api/delete/{userId}")
  public void deleteUser(@PathVariable("userId") Long id) {
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getUserId().equals(id)) {
        users.remove(i);
      }
    }
  }

  @PutMapping("/api/update/{id}")
  public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
    for (int i = 0; i <= users.size(); i++) {
      if (users.get(i).getUserId().equals(id)) {
        if (user.getPassword() != null) {
          users.get(i).setPassword(user.getPassword());
        }
        if (user.getFirstName() != null) {
          users.get(i).setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
          users.get(i).setLastName(user.getLastName());
        }
        if (user.getUsername() != null) {
          users.get(i).setUsername(user.getUsername());
        }
        if (user.getRole() != null) {
          users.get(i).setRole(user.getRole());
        }


        return users.get(i);
      }
    }
    return null;
  }

  @PostMapping("/api/user/search")
  public ArrayList <User> searchUsers(@RequestBody User user) {
    ArrayList <User> usernameList = new ArrayList <>();
    ArrayList <User> passwordList = new ArrayList <>();
    ArrayList <User> firstNameList = new ArrayList <>();
    ArrayList <User> lastNameList = new ArrayList <>();
    ArrayList <User> roleList = new ArrayList <>();

    ArrayList <User> tempUser = new ArrayList <>();

    tempUser.addAll(users);

    if (!user.getUsername().equals("")) {
      for (int i = 0; i < tempUser.size(); i++) {
        if (tempUser.get(i).getUsername().equals(user.getUsername())) {
          usernameList.add(tempUser.get(i));
        }
      }
      tempUser = usernameList;
    }


    if (!user.getPassword().equals("")) {
      for (int i = 0; i < tempUser.size(); i++) {
        if (tempUser.get(i).getPassword().equals(user.getPassword())) {
          passwordList.add(tempUser.get(i));
        }
      }
      tempUser = passwordList;
    }

    if (!user.getFirstName().equals("")) {
      for (int i = 0; i < tempUser.size(); i++) {
        if (tempUser.get(i).getFirstName().equals(user.getFirstName())) {
          firstNameList.add(tempUser.get(i));
        }
      }
      tempUser = firstNameList;
    }


    if (!user.getLastName().equals("")) {
      for (int i = 0; i < tempUser.size(); i++) {
        if (tempUser.get(i).getLastName().equals(user.getLastName())) {
          lastNameList.add(tempUser.get(i));
        }
      }
      tempUser = lastNameList;
    }


    if (!user.getRole().equals("")) {
      for (int i = 0; i < tempUser.size(); i++) {
        if (tempUser.get(i).getRole().equals(user.getRole())) {
          roleList.add(tempUser.get(i));
        }
      }
      tempUser = roleList;
    }


    System.out.println(tempUser);

    return tempUser;
  }

}
