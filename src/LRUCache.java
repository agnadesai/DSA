import java.util.HashMap;

public class LRUCache {
    private final int capacity;
    private final HashMap<Integer, Node> map;
    private final DoublyLinkedList cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.cache = new DoublyLinkedList();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        cache.moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            cache.moveToHead(node);
        } else {
            if (map.size() >= capacity) {
                Node tail = cache.removeTail();
                map.remove(tail.key);
            }
            Node newNode = new Node(key, value);
            cache.addToHead(newNode);
            map.put(key, newNode);
        }
    }

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class DoublyLinkedList {
        private final Node head;
        private final Node tail;

        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        void moveToHead(Node node) {
            removeNode(node);
            addToHead(node);
        }

        Node removeTail() {
            Node res = tail.prev;
            removeNode(res);
            return res;
        }
    }
}