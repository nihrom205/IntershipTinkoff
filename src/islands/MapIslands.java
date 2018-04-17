package islands;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Поиск островов.
 *
 * @author Alexey Rastorguev (rastorguev00@gmail.com)
 * @version 0.1
 * @since 16.04.2018
 */
public class MapIslands {

    // хранилище "узлов" остравов
    private List<Node> storegeIntership = new LinkedList<>();
    // карта островов
    private int[][] map;
    // посещенные позиции
    private List<Position> oldPosition = new LinkedList<>();

    //временное хранение позиций
    private Queue<Position> storePosition = new LinkedList<>();


    // конструктор
    public MapIslands(int[][] map) {
        this.map = map;
    }

    /**
     * метод поиска соседних островов.
     * @param curentNode текущий узел.
     */
    private void findNeighbors(Node curentNode) {
        storePosition.add(curentNode.getPosition());
        Position position;

        while(!storePosition.isEmpty()) {
            position = storePosition.poll();
            enviromenPosition(position, curentNode);
            curentNode.addPosition(position);
            oldPosition.add(position);
        }
    }

    /**
     * метод ищет в округ позиции острова и если находит добавляет в хранилище storePosition.
     * @param currentPosition текущая позиция
     */
    private void enviromenPosition(Position currentPosition, Node curentNode) {
        int y = currentPosition.getY();
        int x = currentPosition.getX();


        if (x - 1 >= 0 && map[y][x - 1] == 1) {
            Position leftPosition = new Position(y, x - 1);
            if (!curentNode.getStorePositions().contains(leftPosition)) {
                storePosition.add(leftPosition);
            }
        }
        if (x + 1 < map[0].length && map[y][x + 1] == 1) {
            Position rightPosition = new Position(y, x + 1);
            if (!curentNode.getStorePositions().contains(rightPosition)) {
                storePosition.add(rightPosition);
            }
        }
        if (y - 1 >= 0 && map[y - 1][x] == 1) {
            Position topPosition = new Position(y - 1, x);
            if (!curentNode.getStorePositions().contains(topPosition)) {
                storePosition.add(topPosition);
            }
        }
        if (y + 1 < map.length && map[y + 1][x] == 1) {
            Position downPosition = new Position(y + 1, x);
            if (!curentNode.getStorePositions().contains(downPosition)) {
                storePosition.add(downPosition);
            }
        }
    }

    /**
     * метод пробегается по массиву и ищет острова "1", создаятся узел в который помещается координаты.
     *
     * @param map массив
     */
    private void intership(int[][] map) {
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == 1) {
                    Position position = new Position(i, j);
                    if (!oldPosition.contains(position)) {
                        Node newNode = new Node(position);
                        findNeighbors(newNode); // ищем соседние острова
                        // если координат больше 1 то это остров, сохраняем storegeIntership
                        if (newNode.getStorePositions().size() > 1) {
                            storegeIntership.add(newNode);
                        }

                    }
                }
            }
        }

        System.out.println();
    }

    private void prinIntership() {

        // выводит координаты всех остарвов
//==============================================================================
//        for (Node node : storegeIntership) {
//            for (Position position : node.getStorePositions()) {
//                System.out.print(position + " ");
//
//            }
//            System.out.println();
//        }
//==============================================================================

        System.out.println("Количество остравов: " + storegeIntership.size());
    }

    public void runProg() {
//        System.out.println(Runtime.getRuntime().availableProcessors());
        intership(map);
        prinIntership();
    }

    /**
     * точка входа в программу
     * @param args массив аргументов
     */
    public static void main(String[] args) {
        // карта
        int[][] mapIsland = {{0, 1, 1, 0, 0, 1},
                            {0, 1, 0, 0, 0, 1},
                            {0, 0, 1, 1, 0, 0},
                            {1, 1, 0, 0, 0, 0},
                            {1, 1, 0, 1, 1, 1},
                            {1, 1, 0, 0, 1,0}};

        MapIslands mapIs = new MapIslands(mapIsland);
        mapIs.runProg();
    }
}
