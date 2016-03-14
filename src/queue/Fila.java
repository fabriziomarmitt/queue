package queue;

import java.util.Iterator;

public class Fila<Item> implements Iterable<Item> {
    private int N; // número de elementos na fila
    private Node first; // primeiro elemento da fila
    private Node last; // último elemento da fila

    // classe para criação de uma fila com elementos vinculados
    private class Node {
        private Item item;
        private Node next;
    }

    // criação da fila vazia
    {
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public Item peek() {
        if (isEmpty()) throw new RuntimeException("Fila está vazia");
        return first.item;
    }

    public Fila enqueue(Item item) {
        Node node = new Node();
        node.item = item;
        if (isEmpty()) {
            first = node;
            last = node;
        } else {
            last.next = node;
            last = node;
        }
        N++;
        return this;
    }

    public Item dequeue() {
        if (isEmpty()) throw new RuntimeException("Fila está vazia");
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;
        return item;
    }


    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new RuntimeException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Fila<String> q = new Fila<>();
        String item = "a";
        q.enqueue(item);

        System.out.println(q.first.item);
        System.out.println(q.last.item);

        item = "b";
        q.enqueue(item);

        System.out.println(q.first.next.item);
        System.out.println(q.last.item);

        item = "c";
        q.enqueue(item);

        System.out.println(q.first.next.item);
        System.out.println(q.last.item);


        System.out.println("(" + q.size() + " left on queue)");

        StringBuilder s = new StringBuilder();
        for (String string : q)
            s.append(string + " ");
        System.out.println(s.toString());
    }
}
