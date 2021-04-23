package com.hccx.sqljava.domain.bean;

import lombok.Data;

/**
 * 项目名称：sql-java
 * <br>类描述：
 * <br>创建人：htliu
 * <br>创建时间：2021/4/23 0023 16:58
 * <br>修改人:
 * <br>修改时间：2021/4/23 0023 16:58
 * <br>修改备注：
 *
 * @author htliu
 * @date 2021/4/23 0023 16:58
 */
@Data
public class Linked<T> {
    private Node<T> head;
    private Node<T> last;

    /**
     * 添加元素
     *
     * @param t
     */
    public Linked<T> add(T t) {
        Node<T> node = new Node<>(t, null);
        if (head == null) {
            this.head = node;
            last = node;
        } else {
            last.next = node;
            last = node;
        }
        return this;
    }


    @Data
    public static class Node<T> {
        private T t;
        private Node<T> next;

        public Node(T t, Node<T> next) {
            this.t = t;
            this.next = next;
        }
    }

}
