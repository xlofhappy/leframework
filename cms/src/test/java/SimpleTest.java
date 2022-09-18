import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleTest {


    @Test
    public void a() {
        List<Integer> numbers = new ArrayList<>();
        for ( int i = 0; i < 100000; i++ ) {
            numbers.add(i);
        }
        numbers.parallelStream().forEach(System.out::println);
    }
}
