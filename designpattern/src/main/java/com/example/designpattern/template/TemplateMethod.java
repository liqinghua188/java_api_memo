package com.example.designpattern.template;

/**
 * 模版模式
 *
 * 固定的流程写在模版父类中 executePrint(),方法定义为final，以防子类重写
 * 自定义的流程逻辑由子类或抽象出接口实现 wrapperPrint()
 */
public abstract class TemplateMethod {

    public final void executePrint() {
        System.out.println("############################################################");
        wrapperPrint();
        System.out.println("############################################################");
    }

    protected abstract void wrapperPrint();

    public static void main(String[] args) {
        new TemplateMethod() {
            protected void wrapperPrint() {
                System.out.println("*Hello World*");
            }
        }.executePrint();

        new TemplateMethod() {
            protected void wrapperPrint() {
                System.out.println("==Hello World==");
            }
        }.executePrint();
    }

}
