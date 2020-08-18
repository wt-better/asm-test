package fifth;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/5/24
 */
public class Main {

    static class User {

        private int age;

        User(int age) {
            this.age = age;
        }

        int getAge() {
            return age;
        }

        void setAge(int age) {
            this.age = age;
        }
    }

    private static void test(User user) {
        user.setAge(100);

        user = new User(1000);
    }


    public static void main(String[] args) {
        User user = new User(10);
        test(user);
        System.out.println(user.getAge());
    }
}
