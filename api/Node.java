package scripts.SPXAIOCooker.api;

import scripts.SPXAIOCooker.Variables;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public abstract class Node {

    protected Variables vars;

    public Node(Variables v) {
        vars = v;
    }

    public abstract void execute();

    public abstract boolean validate();

}

