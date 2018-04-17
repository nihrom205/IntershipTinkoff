package islands;

import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.List;
import java.util.Set;

/**
 * Class <Name class>.
 *
 * @author Alexey Rastorguev (rastorguev00@gmail.com)
 * @version 0.1
 * @since 16.04.2018
 */
public class Node {
    private Position position;

    private Set<Position> storePositions = new HashSet<>();

    public Node(Position position) {
        this.position = position;
        storePositions.add(position);

    }

    public void addPosition(Position position) {
        storePositions.add(position);
    }

    /**
     * геттер
     * @return координаты
     */
    public Position getPosition() {
        return position;
    }

    public Set<Position> getStorePositions() {
        return storePositions;
    }
}
