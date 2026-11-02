package learning.collections;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// Learning experiment, not part of the application. Run main() from IDEA.
public class HashCodeDemo {

    // 1. Every client goes to the same box, but equals still compares passports
    static class SameBoxClient {
        String passport;

        SameBoxClient(String passport) {
            this.passport = passport;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof SameBoxClient other && passport.equals(other.passport);
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

    // 2. equals is overridden, hashCode is forgotten
    static class NoHashCodeClient {
        String passport;

        NoHashCodeClient(String passport) {
            this.passport = passport;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof NoHashCodeClient other && passport.equals(other.passport);
        }
    }

    // 3. Box is chosen by id, and id changes after the object is put into the set
    static class IdAccount {
        Long id;

        @Override
        public boolean equals(Object o) {
            return o instanceof IdAccount other && Objects.equals(id, other.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Same hashCode does not mean equal ===");
        SameBoxClient ali = new SameBoxClient("AA1111111");
        SameBoxClient vali = new SameBoxClient("BB2222222");
        System.out.println("ali box:  " + ali.hashCode());
        System.out.println("vali box: " + vali.hashCode());
        System.out.println("ali.equals(vali): " + ali.equals(vali));

        System.out.println();
        System.out.println("=== 2. equals without hashCode ===");
        NoHashCodeClient first = new NoHashCodeClient("AA1234567");
        NoHashCodeClient second = new NoHashCodeClient("AA1234567");
        System.out.println("first box:  " + first.hashCode());
        System.out.println("second box: " + second.hashCode());
        System.out.println("first.equals(second): " + first.equals(second));
        Set<NoHashCodeClient> clients = new HashSet<>();
        clients.add(first);
        System.out.println("clients.contains(second): " + clients.contains(second));
        clients.add(second);
        System.out.println("clients.size() after adding the same passport twice: " + clients.size());

        System.out.println();
        System.out.println("=== 3. hashCode from a field that changes ===");
        IdAccount account = new IdAccount();
        Set<IdAccount> accounts = new HashSet<>();
        accounts.add(account);
        System.out.println("box when added (id = null): " + account.hashCode());
        account.id = 42L;
        System.out.println("box when searched (id = 42): " + account.hashCode());
        System.out.println("accounts.contains(account): " + accounts.contains(account));
        System.out.println("accounts.size(): " + accounts.size());
    }
}
