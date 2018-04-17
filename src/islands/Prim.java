package islands;

import java.util.LinkedList;
import java.util.List;

/**
 * Class <Name class>.
 *
 * @author Alexey Rastorguev (rastorguev00@gmail.com)
 * @version 0.1
 * @since 17.04.2018
 */
public class Prim {
    List<Integer> list = new LinkedList<>();

    public static void main(String[] args) {
        Prim p = new Prim();
        p.list.add(1);
        p.list.add(2);
        p.list.add(3);

        for (Integer i : p.list) {
            System.out.println(i);
            p.list.add(6);

        }
    }
}
