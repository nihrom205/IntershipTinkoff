package islands;

import java.util.*;

/**
 * Поиск островов.
 *
 * @author Alexey Rastorguev (rastorguev00@gmail.com)
 * @version 0.1
 * @since 16.04.2018
 */
public class MapIslands {

    private Set<Node> set = new HashSet<>(); // результат
    private volatile boolean finish = false; // завершение 1 потока

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
    private Node findNeighbors(Node curentNode) {
        storePosition.add(curentNode.getPosition());
        Position position;

        while(!storePosition.isEmpty()) {
            position = storePosition.poll();
            enviromenPosition(position, curentNode);
            curentNode.addPosition(position);
            oldPosition.add(position);
        }
        return curentNode;
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
     * Метод выводит не аечать кол-во остравов
     */
    private void prinIntership() {
        System.out.println("Количество остравов: " + set.size());

        // выводит координаты остарвова и их координаты
//==============================================================================
//        for (Node node : set) {
//            for (Position pos : node.getStorePositions()) {
//                System.out.print(pos + " ");
//            }
//            System.out.println();
//
//        }
//==============================================================================
    }

    /**
     * метод запуска потоков.
     */
    public void runProg() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < map[0].length; i++) {
                    for (int j = 0; j < map.length; j++) {
                        if (map[i][j] == 1) {
                            Position position = new Position(i, j);
                            if (!oldPosition.contains(position)) {
                                synchronized (storegeIntership) {
                                    storegeIntership.add(new Node(position));
                                }

                            }
                        }
                        synchronized (storegeIntership) {
                            storegeIntership.notifyAll();
                        }
                    }
                }
                finish = true;

            }
        }.start();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (storegeIntership.isEmpty()) {
                        try {
                            synchronized (storegeIntership) {
                                storegeIntership.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        synchronized (storegeIntership) {
                            Node node = findNeighbors(storegeIntership.get(0));
                            storegeIntership.remove(0);
                            set.add(node);
                        }
                    }
                    if (storegeIntership.isEmpty() && finish) {
                        break;
                    }
                    if (set.size() == 10) {
                        break;
                    }

                }
            }
        };

        // запуск 2х потоков
//        t1.start();
        t2.start();

        try {
            // главный поток ожидает завершения потока t2
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
