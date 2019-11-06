/**
 * 
 */
package com.my.train.util;

import com.my.train.model.AbstractPersistentModel;
import com.my.train.model.Teacher;
import com.my.train.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */
@Component
public class PersistentModelEventListener {

    @Autowired
    private TeacherService teacherService;

    @EventListener
    public void onEvent(PersistentModelEvent event) {
        //获取事件类型
        PersistentModelEvent.Type type = event.getType();
        //获取触发事件的model类型
        Class<?> modelClass = event.getModelClass();
        //获取出发事件的具体Entity
        AbstractPersistentModel[] models = event.getModels();

        if (Teacher.class.isAssignableFrom(modelClass)) {
            onEvent(type, (Teacher[]) models);
        }
        //其他service（后续补充）
//        else if (Domain.class.isAssignableFrom(modelClass)) {
//            onEvent(type, (Domain[]) models);
//        }
    }



    // ------
    // on event
    // ------
    private void onEvent(PersistentModelEvent.Type type, Teacher... settingss) {
        Teacher teacher = settingss[0];
        switch (type) {
            case PRE_DELETE:
                if (teacherService.countById(teacher.getId()) > 0) {
                    //待删除Entity-->id不存在，，抛出异常  ；；做这个的意义是：我们可以在具体的实现类中正常编写代码即可，
                    // 由我们的事件监听机制来进行一些前置校验，从而达到我们自己接手异常信息的目的
                    //throw NOT_DELETE_MODEL_HAS_CHILDREN.runtimeException(RuntimeSettings.class.getName(), settings.getId(), System.class.getName());
                    throw new RuntimeException("id不存在！！");
                }
                break;
            case PRE_CREATE: //创建时，使用uuid为Entity赋值id属性
                    if (StringUtils.isBlank(teacher.getId())){
                        System.out.println("监听的方式分配主键...........");
                        teacher.setId(IdUtil.generateId());
                    }
                break;
//            case PersistentModelEvent.Type.POST_UPDATE:
//                if (settings.getCategory() == Category.REGISTRY_CENTER) {
//                    discoverySvc.onRegistryCenterSettingsChanged(settingss[1]);
//                }
//                break;
//            case PersistentModelEvent.Type.POST_DELETE:
//                if (settings.getCategory() == Category.REGISTRY_CENTER) {
//                    discoverySvc.onRegistryCenterSettingsDeleted(settings);
//                }
//                break;
            default:
                break;
        }
    }



//    private void setModelFields(PersistentModelEvent.Type type, AbstractPersistentModel... models) {
//        // 需要在记录操作日志前, 设置主键
//        if (type == PersistentModelEvent.Type.PRE_CREATE) {
//            AbstractPersistentModel model = models[0];
//            if (StringUtils.isBlank(model.getId())) {
//                model.setId(IdUtil.generateId());
//            }
//        }
//    }
//
//    private void logOperation(PersistentModelEvent.Type type, AbstractPersistentModel... models) {
//        if (type == PersistentModelEvent.Type.POST_CREATE) {
//            String ids = Arrays.stream(models).map(model -> digest(model)).collect(Collectors.joining(", "));
//            String message = "Create " + models[0].getClass().getSimpleName() + ": " + ids;
//            operLogSvc.add(GovernorOperationLog.Type.CREATE, message, models);
//        } else if (type == Type.POST_DELETE) {
//            String ids = Arrays.stream(models).map(model -> digest(model)).collect(Collectors.joining(", "));
//            String message = "Delete " + models[0].getClass().getSimpleName() + ": " + ids;
//            operLogSvc.add(GovernorOperationLog.Type.DELETE, message, models);
//        } else if (type == Type.POST_UPDATE) {
//            // 更新的话是2个实体, 一个是要更新的model, 一个是数据库中存在的model, 这里只需要存一条操作日志
//            String message = "Update " + models[0].getClass().getSimpleName() + ": " + digest(models[0]);
//            operLogSvc.add(GovernorOperationLog.Type.UPDATE, message, models);
//        }
//    }
//
//    private String digest(AbstractPersistentModel model) {
//        String str = "{";
//        str += model.getId();
//        if (model instanceof AbstractMainModel) {
//            str += ", " + ((AbstractMainModel) model).getName();
//            str += ", " + ((AbstractMainModel) model).getCode();
//        }
//        return str + "}";
//    }


}
