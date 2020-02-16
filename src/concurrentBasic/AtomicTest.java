package concurrentBasic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Author Honghan Zhu
 */
public class AtomicTest {
    public static void main(String[] args) {

    }

    private void ReferenceFieldUpdaterTest(){
        AtomicReferenceFieldUpdater<User, Integer> userOldUpdater =
                AtomicReferenceFieldUpdater.newUpdater(User.class, Integer.class, "old");
        User user = new User("zhh", 22);
        userOldUpdater.compareAndSet(user, user.getOld(), user.getOld() + 1);
        System.out.println(userOldUpdater.get(user));
    }

    private void ReferenceTest() {
        AtomicReference<User> userRef = new AtomicReference<>();
        User old = new User("zhh", 22);
        userRef.set(old);
        userRef.compareAndSet(old, new User("new_zhh", 23));
        System.out.println(userRef.get().toString());
    }
}

class User {
    private String name;
    public volatile Integer old;

    public User(String name, Integer old) {
        this.name = name;
        this.old = old;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOld() {
        return old;
    }

    public void setOld(Integer old) {
        this.old = old;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", old=" + old +
                '}';
    }
}
