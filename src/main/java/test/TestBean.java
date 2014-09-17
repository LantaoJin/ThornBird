package test;

public class TestBean implements TestBeanMBean {

    private String field;

    TestBean(String field) {
        this.field = field;
    }

    public Object fun() {
        System.out.println("TestMBean-field:" + field);
        return field;
    }

    public Object fun(Object para) {
        System.out.println("TestMBean-field:" + field);
        System.out.println("TestMBean-para:" + para);
        return field;
    }

    /**
     * @return the field
     */
    public String getField() {
        System.out.println("get field!" + field);
        return field;
    }

    /**
     * @param field
     *            the field to set
     */
    public void setField(String field) {
        System.out.println("set field!" + field);
        this.field = field;
    }
}
