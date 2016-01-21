package scripts.SPXAIOCooker.api.Framework;

import scripts.SPXAIOCooker.data.Variables;

public abstract class Node {

    protected Variables vars;

    public Node(Variables v) {
        vars = v;
    }

    public abstract void execute();

    public abstract boolean validate();

}

