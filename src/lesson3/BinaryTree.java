package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        T value = (T) o;
        Node<T> elem = find(value);
        size--;
        return removeElem(elem);
    }

    public boolean removeElem(Node node) {
        if (node == null) return false;
        Node<T> parent = parent(node);

        if (parent == null) {
            if (node.left == null) {
                if (node.right == null) {
                    root = null;
                }
                else {
                    root = node.right;
                }
            } else {
                if (node.right == null) {
                    root = node.left;
                } else {
                    Node minLeaf = minLeaf(node.right);

                    Node newNode = new Node(minLeaf.value);
                    newNode.left = node.left;
                    newNode.right = node.right;
                    root = newNode;
                    removeElem(minLeaf);
                }
            }
        } else {
            if (node.left == null) {
                if (node.right == null) {
                    if (parent.left == node) parent.left = null;
                    else parent.right = null;
                }
                else {
                    Node newNode = new Node(node.right.value);
                    if (node.right.left != null) newNode.left = node.right.left;
                    if (node.right.right != null) newNode.right = node.right.right;
                    if (parent.left == node) parent.left = newNode;
                    else parent.right = newNode;
                }
            } else {
                if (node.right == null) {
                    Node newNode = new Node(node.left.value);
                    if (node.left.left != null) newNode.left = node.left.left;
                    if (node.left.right != null) newNode.right = node.left.right;
                    if (parent.left == node) parent.left = newNode;
                    else parent.right = newNode;
                } else {
                    Node minLeaf = minLeaf(node.right);
                    removeElem(minLeaf);
                    Node newNode = new Node(minLeaf.value);
                    newNode.left = node.left;
                    newNode.right = node.right;
                    if (parent.left == node) parent.left = newNode;
                    else parent.right = newNode;
                }
            }
        }

        return true;
    }

    public Node<T> minLeaf(Node<T> node) {
        if (node.left == null) return node;
        else return minLeaf(node.left);
    }

    public Node<T> parent(Node<T> node) {
        if (root == null || root == node) return null;
        return parent(root, node.value);
    }

    private Node<T> parent(Node<T> start, T value) {
        if (start.left != null && start.left.value == value) return start;
        if (start.right != null && start.right.value == value) return start;

        int comparison = value.compareTo(start.value);
        if (comparison < 0) {
            assert start.left != null;
            return parent(start.left, value);
        }
        else {
            assert start.right != null;
            return parent(start.right, value);
        }
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {
        LinkedList<Node<T>> nodes = new LinkedList<>();
        Node<T> curNode = null;

        private BinaryTreeIterator(Node<T> node) {
            addNode(node);
        }

        private void addNode(Node<T> node) {
            while (node != null) {
                nodes.addFirst(node);
                node = node.left;
            }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return !nodes.isEmpty();
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public T next() {
            curNode = nodes.getFirst();

            nodes.removeFirst();
            addNode(curNode.right);
            return curNode.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            removeElem(curNode);
            size--;
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator(root);
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
