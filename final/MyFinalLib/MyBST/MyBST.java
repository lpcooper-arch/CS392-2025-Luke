package MyFinalLib.MyBST;

//
// HX-2025-11-20: 50 points
// A partial implementation of
// randomized doubly linked binary search tree
// 30 points for reroot and 20 points for insert
//

import java.util.Random;
import java.util.function.BiConsumer;

public class MyBST<K extends Comparable<K>, V>{
    Node root = null;
    
    public class Node {
        K key; // key stored in the node
        V value; // value associated with the key
        int size; // size of the tree rooted as the node
        Node parent; // parent of the node
        Node lchild; // left-child of the node
        Node rchild; // right-child of the node
        
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
            this.parent = null;
            this.lchild = null;
            this.rchild = null;
        }
    }
    
    private void updateSize(Node node) {
        if (node == null) return;
        node.size = 1;
        if (node.lchild != null) node.size += node.lchild.size;
        if (node.rchild != null) node.size += node.rchild.size;
    }
    
    private void rotateLeft(Node node) {
        if (node == null || node.rchild == null) return;
        
        Node rightChild = node.rchild;
        Node parent = node.parent;
        
        node.rchild = rightChild.lchild;
        if (rightChild.lchild != null) {
            rightChild.lchild.parent = node;
        }
        
        rightChild.lchild = node;
        node.parent = rightChild;
        
        rightChild.parent = parent;
        if (parent == null) {
            root = rightChild;
        } else if (parent.lchild == node) {
            parent.lchild = rightChild;
        } else {
            parent.rchild = rightChild;
        }
        
        updateSize(node);
        updateSize(rightChild);
        
        Node ancestor = rightChild.parent;
        while (ancestor != null) {
            updateSize(ancestor);
            ancestor = ancestor.parent;
        }
    }
    
    private void rotateRight(Node node) {
        if (node == null || node.lchild == null) return;
        
        Node leftChild = node.lchild;
        Node parent = node.parent;
        
        node.lchild = leftChild.rchild;
        if (leftChild.rchild != null) {
            leftChild.rchild.parent = node;
        }
        
        leftChild.rchild = node;
        node.parent = leftChild;
        
        leftChild.parent = parent;
        if (parent == null) {
            root = leftChild;
        } else if (parent.lchild == node) {
            parent.lchild = leftChild;
        } else {
            parent.rchild = leftChild;
        }
        
        updateSize(node);
        updateSize(leftChild);
        
        Node ancestor = leftChild.parent;
        while (ancestor != null) {
            updateSize(ancestor);
            ancestor = ancestor.parent;
        }
    }
    
    private Node getNodeAtIndex(Node node, int index) {
        if (node == null) return null;
        
        int leftSize = (node.lchild != null) ? node.lchild.size : 0;
        
        if (index < leftSize) {
            return getNodeAtIndex(node.lchild, index);
        } else if (index == leftSize) {
            return node;
        } else {
            return getNodeAtIndex(node.rchild, index - leftSize - 1);
        }
    }
    
    public void reroot() {
        // HX-2025-11-20: 30 points
        // [reroot] picks a node RANDOMLY and
        // uses rotations to turn this picked node
        // into the root of a new binary search tree
        // (containing the same set of keys)
        if (root == null || root.size <= 1) return;
        
        Random rand = new Random();
        int randomIndex = rand.nextInt(root.size);
        
        Node targetNode = getNodeAtIndex(root, randomIndex);
        
        while (targetNode.parent != null) {
            Node parent = targetNode.parent;
            if (parent.lchild == targetNode) {
                rotateRight(parent);
            } else {
                rotateLeft(parent);
            }
        }
    }

    public V search(K key) {
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.lchild;
            } else if (cmp > 0) {
                current = current.rchild;
            } else {
                return current.value;
            }
        }
        return null;
    }
    
    public boolean insert(K key, V value) {
        // HX-2025-11-20: 20 points
        // If key is in the tree stored at [root],
        // [insert] does no nothing and just returns false
        // If key is not in the tree stored at [root],
        // the key is inserted as a leaf node and the new
        // tree is still a binary search tree and [insert]
        // returns true (to indicate insertion is done).
        if (root == null) {
            root = new Node(key, value);
            return true;
        }
        Node current = root;
        Node parent = null;
        while (current != null) {
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.lchild;
            } else if (cmp > 0) {
                current = current.rchild;
            } else {
                current.value = value;
                return false;
            }
        }
        Node newNode = new Node(key, value);
        newNode.parent = parent;
        int cmp = key.compareTo(parent.key);
        if (cmp < 0) {
            parent.lchild = newNode;
        } else {
            parent.rchild = newNode;
        }
        Node ancestor = parent;
        while (ancestor != null) {
            updateSize(ancestor);
            ancestor = ancestor.parent;
        }

        return true;
    }
    
    private void inorderPrint(Node node) {
        if (node == null) return;
        inorderPrint(node.lchild);
        System.out.print(node.key + " ");
        inorderPrint(node.rchild);
    }

    public void inorderTraverse(BiConsumer<K, V> consumer) {
        inorderHelper(root, consumer);
    }
    
    private void inorderHelper(Node node, BiConsumer<K, V> consumer) {
        if (node == null) return;
        inorderHelper(node.lchild, consumer);
        consumer.accept(node.key, node.value);
        inorderHelper(node.rchild, consumer);
    }
    
    private boolean isBST(Node node, K min, K max) {
        if (node == null) return true;
        if ((min != null && node.key.compareTo(min) <= 0) || (max != null && node.key.compareTo(max) >= 0)) {
            return false;
        }
        return isBST(node.lchild, min, node.key) && isBST(node.rchild, node.key, max);
    }
    
    public static void main (String[] args) {
        // Please add minimal testing code for reroot()
        // Please add minimal testing code for insert()
        MyBST tree = new MyBST();
        
        System.out.println("Testing insert:");
        for (int i = 1; i <= 10; i++) {
            int randomKey = (int)(Math.random() * 20);
            System.out.println("Insert " + randomKey + ": " + tree.insert(randomKey, "value" + randomKey));
        }
        
        System.out.println();
        
        System.out.print("Inorder traversal: ");
        tree.inorderPrint(tree.root);
        System.out.println();
        System.out.println("Root key: " + tree.root.key);
        System.out.println("Root size: " + tree.root.size);
        System.out.println("Is BST: " + tree.isBST(tree.root, null, null));
        
        System.out.println();
        
        System.out.println("Testing reroot:");
        for (int i = 0; i < 5; i++) {
            tree.reroot();
            System.out.print("After reroot " + (i+1) + ", root key: " + tree.root.key + ", inorder: ");
            tree.inorderPrint(tree.root);
            System.out.println();
            System.out.println("Is BST: " + tree.isBST(tree.root, null, null));
			System.out.println();
        }
        
        return /*void*/;
    }
}

// contains Quiz02_06