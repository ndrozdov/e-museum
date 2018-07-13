package repository;

import ru.mkrf.label.entity.db.Tree;

import java.util.List;

public class TestTreeObj {
    private Tree tree;
    private List<TestTreeObj> trees;

    public TestTreeObj() {
    }

    public TestTreeObj(Tree tree) {
        this.tree = tree;
    }

    public TestTreeObj(List<TestTreeObj> trees) {
        this.trees = trees;
    }

    public TestTreeObj(Tree tree, List<TestTreeObj> trees) {
        this.tree = tree;
        this.trees = trees;
    }

    public List<TestTreeObj> getTrees() {
        return trees;
    }

    public void setTrees(List<TestTreeObj> trees) {
        this.trees = trees;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }
}
