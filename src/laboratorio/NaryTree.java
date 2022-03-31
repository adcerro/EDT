
package laboratorio;

public class NaryTree {
    private final Queue<TreeNode> iterationQueue;
    private TreeNode root;
    private final LinkedList<LinkedList<String>> mapNames;


    public NaryTree() {
        root = null;
        iterationQueue = new Queue<>();
        mapNames = new LinkedList<>();
    }

    private static boolean isNotPackageInstance(TreeNode node) {
        return !(node instanceof PackageTreeNode);
    }

    public String getTitle() {
        if (root == null) return null;

        return root.getValue();
    }

    public void setTitle(String title) {
        if (root == null) {
            root = new PackageTreeNode(title);
            return;
        }

        root.setValue(title);
    }

    public void insert(String parentValue, String value, NodeType type) {
        TreeNode newNode = getNodeInstance(type, value);
        newNode.parentValue = parentValue;

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

        final int[] lastIndex = new int[1];
        if (isNotPackageInstance(newNode)) {
            mapNames.reset();
            for (int i = 0; i < root.size(); i++) {
                LinkedList<String> names = new LinkedList<>();
                names.insert(root.getValue());
                mapNames.insert(names);
            }
        }

        PackageTreeNode parentNodeCache = new PackageTreeNode(parentValue);
        while (!iterationQueue.isEmpty() && !isNodeInserted) {
            TreeNode currentTreeNode = iterationQueue.poll();

            TreeNode parentInsertion = currentTreeNode.find(new ILinkedHelper<TreeNode>() {
                @Override
                public boolean compare(TreeNode a, TreeNode b) {
                    iterationQueue.insert(a);

                    if (isNotPackageInstance(newNode)) {
                        for (int i = 0; i < mapNames.size(); i++) {
                            LinkedList<String> currentMap = mapNames.getAt(i).getValue();
                            if (currentMap.tail.getValue().equals(a.parentValue)) {
                                currentMap.insert(a.getValue());
                                lastIndex[0] = i;
                                break;
                            }
                        }
                    }

                    return a.getValue().equals(b.getValue());
                }
            }, parentNodeCache);

            if (parentInsertion != null) {
                parentInsertion.insertChild(newNode);
                isNodeInserted = true;
            }
        }

        if (isNodeInserted && isNotPackageInstance(newNode)) {
//            LinkedList<String> nodePath = mapNames.getAt(lastIndex[0]).getValue();
//            StringBuilder literalPath = new StringBuilder();
//
//            for (ListNode<String> node : nodePath) {
//                literalPath.append(node.getValue().replace(' ', '_')).append("/");
//            }
//
//            literalPath.append(newNode.getValue().replace(' ', '_'));
//            ((DeliverableTreeNode)newNode).setFullPath(literalPath.toString());
//            System.out.println(literalPath);
            ((DeliverableTreeNode) newNode).setFullPath(generateFullPath(lastIndex[0], newNode.getValue()));
        }
    }

    private String generateFullPath(int mapIndex, String newNodeValue) {
        LinkedList<String> mapPath = mapNames.getAt2(mapIndex);
        StringBuilder literalPath = new StringBuilder();

        for (ListNode<String> node : mapPath) {
            literalPath.append(node.getValue()).append("/");
        }

        literalPath.append(newNodeValue);
        return literalPath.toString().replace(' ', '_');
    }

    @Override
    public String toString() {
        if (root == null) return "Empty";

        StringBuilder data = new StringBuilder();
        final int[] idx = new int[1];

        TreeNode currentTreeNode = root;

        data.append("Parent: [").append(currentTreeNode.getValue()).append("]\n");
        idx[0] = 1;
        currentTreeNode.forEachChild(new ILinkedHelper<TreeNode>() {
            @Override
            public void handle(TreeNode node) {
                data.append("\t").append(idx[0]).append(". ");
                data.append(node.toString(2)).append("\n");
                idx[0] += 1;
            }
        });

        data.append("\n");

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
