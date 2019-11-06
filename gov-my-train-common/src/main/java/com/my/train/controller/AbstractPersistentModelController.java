/**
 * 
 */
package com.my.train.controller;

import com.my.train.model.AbstractPersistentModel;
import com.my.train.service.AbstractPersistentModelService;
import com.my.train.util.ValidationGroups;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */
public abstract class AbstractPersistentModelController<T extends AbstractPersistentModel> {

    protected abstract AbstractPersistentModelService<T> getSvc();

    @ApiOperation("新增")
    @RequestMapping(method = RequestMethod.POST)
    public T create(@Validated({ ValidationGroups.Create.class }) @RequestBody T model) {
        return getSvc().create(model);
    }

    @ApiOperation("更新")
    @RequestMapping(method = RequestMethod.PUT)
    public T update(@Validated({ ValidationGroups.Update.class }) @RequestBody T model) {
        return getSvc().update(model);
    }

    @ApiOperation("按主键删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.ALL_VALUE)
    public void deleteById(@PathVariable(name = "id") String id) {
        getSvc().deleteByIds(id);
    }

    @ApiOperation("按主键集合删除")
    @RequestMapping(value = "/bulk-delete", method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE)
    public void deleteByIds(@RequestBody String[] ids) {
        getSvc().deleteByIds(ids);
    }

    @ApiOperation("按主键查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public T findById(@PathVariable(name = "id") String id) {
        return getSvc().findById(id);
    }
}
