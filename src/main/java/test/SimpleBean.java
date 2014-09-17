package test;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public class SimpleBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int x;
    private final int y;
    public SimpleBean() {
        this.x = 0;
        this.y = 0;
    }
    SimpleBean(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
