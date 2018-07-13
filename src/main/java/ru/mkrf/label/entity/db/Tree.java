package ru.mkrf.label.entity.db;

import static ru.mkrf.label.util.PreparedStatementUtil.*;

public class Tree extends BaseEntity {
    public static final String GET_BY_ID = genSelectQueryByRowId(Tree.class);
    public static final String GET_ALL = genSelectQuery(Tree.class);
    public static final String GET_ALL_CHILD_ID =
            "WITH RECURSIVE\n" +
            "  recursive_tree( id, parent_id ) AS (\n" +
            "    SELECT :parent_id, null\n" +
            "\n" +
            "    UNION\n" +
            "\n" +
            "    SELECT tree.id, tree.parent_id FROM tree, recursive_tree\n" +
            "    WHERE tree.parent_id = recursive_tree.id\n" +
            "  )\n" +
            "SELECT * FROM recursive_tree;";

    public static final String GET_TERMINAL_ID_BY_CHILD_ID =
            "WITH RECURSIVE\n" +
            "    recursive_tree( id, parent_id ) AS (\n" +
            "    SELECT null, :childId\n" +
            "\n" +
            "    UNION\n" +
            "\n" +
            "    SELECT tree.id, tree.parent_id FROM tree, recursive_tree\n" +
            "    WHERE tree.id = recursive_tree.parent_id\n" +
            "  )\n" +
            "SELECT id FROM recursive_tree WHERE parent_id IS NULL;";
    public static final String INSERT = genInsertQuery(Tree.class);
    public static final String UPDATE = genUpdateQuery(Tree.class);
    public static final String DELETE = "DELETE FROM tree WHERE id = :id";
    public static final String CHANGE_PARENT = "UPDATE tree SET parent_id = :parentId WHERE id = :childId";

    private Integer parentId;

    public Tree() {
    }

    public Tree(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
