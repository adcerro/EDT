
package laboratorio;

public class NaryTree {
    private TreeNode root;
    private final Queue<TreeNode> iterationQueue;

    public NaryTree() {
        root = null;
        iterationQueue = new Queue<>();
    }

    public void setTitle(String title) {
        if (root == null) {
            root = new PackageTreeNode(title);
            return;
        }

        root.setValue(title);
    }

    public String getTitle() {
        if (root == null) return null;

        return root.getValue();
    }

    public void insert(String parentValue, String value, NodeType type) {
        TreeNode newNode = getNodeInstance(type, value);
        insert(parentValue, value, newNode);
    }

    private void insert(String parentValue, String value, TreeNode node) {
        // If the tree is empty, it will set the value as the root element
        if (parentValue == null && root == null) {
            setTitle(value);
            return;
        }

        if (parentValue == null) {
            throw new NullPointerException();
        }

        if (value == null) {
            throw new IllegalTreeNode();
        }

        internal_insert(parentValue, node);
    }

    public void insertPackageNode(String parentValue, String value) {
        insert(parentValue, value, new PackageTreeNode(value));
    }

    public void insertDerivableNode(String parentValue, String value, String fileContent) {
        insert(parentValue, value, new DeliverableTreeNode(value, fileContent));
    }

    private TreeNode getNodeInstance(NodeType type, String nodeValue) {
        return type == NodeType.DELIVERABLE_NODE ? new DeliverableTreeNode(nodeValue) : new PackageTreeNode(nodeValue);
    }

    private void internal_insert(String parentValue, TreeNode newNode) {
        if (root.getValue().equals(parentValue)) {
            root.insertChild(newNode);
            return;
        }

        boolean isNodeInserted = false;
        iterationQueue.reset();
        iterationQueue.add(root);

        PackageTreeNode parentNodeCache = new PackageTreeNode(parentValue);
        while (!iterationQueue.isEmpty() && !isNodeInserted) {
            TreeNode currentTreeNode = iterationQueue.poll();

            TreeNode parentInsertion = currentTreeNode.find(new ILinkedHelper<TreeNode>() {
                @Override
                public boolean compare(TreeNode a, TreeNode b) {
                    iterationQueue.insert(a);
                    return a.getValue().equals(b.getValue());
                }
            }, parentNodeCache);

            if (parentInsertion != null) {
                parentInsertion.insertChild(newNode);
                isNodeInserted = true;
            }
        }

        System.out.println("Node was inserted: " + isNodeInserted);
        System.out.println("Remaining queue: " + iterationQueue.size());
        System.out.println();
    }

    @Override
    public String toString() {
        if (root == null) return "Empty";

        iterationQueue.reset();
        iterationQueue.add(root);

        StringBuilder data = new StringBuilder();
        final int[] idx = new int[1];
        while (!iterationQueue.isEmpty()) {
            TreeNode currentTreeNode = iterationQueue.poll();

            data.append("Parent: [").append(currentTreeNode.getValue()).append("]\n");
            idx[0] = 1;
            currentTreeNode.forEachChild(new ILinkedHelper<TreeNode>() {
                @Override
                public void handle(TreeNode node) {
                    iterationQueue.add(node);
                    data.append("\t").append(idx[0]).append(". ");
                    data.append(node.toString(2)).append("\n");
                    idx[0] += 1;
                }
            });

            data.append("\n");
        }

        return data.toString();
    }

    public void forEachNode(ILinkedHelper<TreeNode> func) {
        if (root == null) return;

        iterationQueue.reset();
        iterationQueue.add(root);
        while (!iterationQueue.isEmpty()) {
            TreeNode currentTreeNode = iterationQueue.poll();
            currentTreeNode.forEachChild(new ILinkedHelper<TreeNode>() {
                @Override
                public void handle(TreeNode node) {
                    iterationQueue.add(node);
                    func.handle(node);
                }
            });
        }
    }

    public enum NodeType {
        PACKAGE_NODE,
        DELIVERABLE_NODE,
    }
}
