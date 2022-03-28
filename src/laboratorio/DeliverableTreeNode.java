
package laboratorio;

public class DeliverableTreeNode extends TreeNode {
    private String fileContent;

    public DeliverableTreeNode(String value, String fileContent) {
        super(value);
        this.fileContent = fileContent;
    }

    public DeliverableTreeNode(String value) {
        this(value, "");
    }

    @Override
    public void insertChild(TreeNode childNode) {
        throw new UnsupportedOperationException("you cannot insert a child in a final node");
    }

    @Override
    public String toString(int index) {
        return "DeliverableTreeNode{" +
                "title='" + getValue() + ".txt" + "', " +
                "fileContent='" + fileContent + '\'' +
                '}';
    }
    
}
