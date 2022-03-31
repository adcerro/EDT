
package laboratorio;

public class DeliverableTreeNode extends TreeNode {
    private String fileContent;

    private String fullPath;

    public DeliverableTreeNode(String value, String fileContent) {
        super(value);
        this.fileContent = fileContent;
    }

    public DeliverableTreeNode(String value) {
        this(value, "");
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    @Override
    public void insertChild(TreeNode childNode) {
        throw new UnsupportedOperationException("you cannot insert a child in a final node");
    }

    @Override
    public String toString(int index) {
        return "DeliverableTreeNode{" +
                "fileContent='" + fileContent + '\'' +
                ", fullPath='" + fullPath + '\'' +
                ", parentValue='" + getParentValue() + '\'' +
                '}';
    }
    
}
