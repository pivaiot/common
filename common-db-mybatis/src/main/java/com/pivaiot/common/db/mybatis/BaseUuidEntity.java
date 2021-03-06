package com.pivaiot.common.db.mybatis;

import com.pivaiot.common.lang.data.ToData;
import com.pivaiot.common.lang.util.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;


/**
 * 所有数据库表类的基类.
 *
 * 每个表实体有三个必备字段
 * - id
 * - created_time
 * - updated_time
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseUuidEntity<T> implements ToData<T>, Serializable {

    protected abstract String generateId();

    protected abstract String getId();

    protected abstract void setId(String id);

    private Date createdTime;

    private Date updatedTime;

    public void updateTime() {
        Date now = new Date();
        if (this.getCreatedTime() == null) {
            this.setCreatedTime(now);
        }

        this.setUpdatedTime(now);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T toData() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class<T> modelClass = (Class) params[0];
        try {
            T model = modelClass.newInstance();
            BeanUtil.copyProperties(this, model);
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
