
package laboratorio;

public class TreeNode extends Node<String> {
    private LinkedList<TreeNode> children;

    protected String parentValue;
    public TreeNode(String value) {
        super(value);

        children = null;
    }

    public void insertChild(TreeNode childNode) {
        if (children == null) {
            children = new LinkedList<>();
        }

        children.insert(childNode);
    }

    public TreeNode find(ILinkedHelper<TreeNode> func, TreeNode searchValue) {
        if (children == null) return null;

        return children.find(func, searchValue);
    }

    public void forEachChild(ILinkedHelper<TreeNode> func) {
        if (children == null) return;

        children.forEach(treeNodeListNode -> func.handle(treeNodeListNode.getValue()));
    }

    public String toString(int idx) {
        if (children == null) return getValue();

        StringBuilder data = new StringBuilder();
        children.forEach(treeNodeListNode -> {
            StringBuilder repeated = new StringBuilder();
            for (int i = 0; i < idx; i++) {
                repeated.append("\t");
            }
            data.append(repeated).append(treeNodeListNode.getValue().toString(idx + 1)).append("\n");
        });

        return getValue() + "\n" + data;
    }

    public String getParentValue() {
        return parentValue;
    }

    public int size() {
        if (children == null) return 0;

         return children.size();
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "children=" + children +
                ", parentValue='" + parentValue + '\'' +
                '}';
    }
}
