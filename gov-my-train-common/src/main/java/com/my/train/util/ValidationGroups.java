package com.my.train.util;

/**
 * @author Wtq
 * @date 2019/10/22 - 17:55
 */

/**
 * 以便进行统一的分组校验
 */
public final class ValidationGroups {
    public interface Create extends javax.validation.groups.Default {
    }

    public interface Update extends javax.validation.groups.Default {
    }

    public interface Delete extends javax.validation.groups.Default {
    }

    public interface Association {
    }
}
