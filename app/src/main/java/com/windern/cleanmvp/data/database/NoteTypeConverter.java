package com.windern.cleanmvp.data.database;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by wenxinlin on 2016/12/12.
 */

public class NoteTypeConverter implements PropertyConverter<NoteType,Integer>{
    @Override
    public NoteType convertToEntityProperty(Integer databaseValue) {
        return NoteType.values()[databaseValue];
    }

    @Override
    public Integer convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.getValue();
    }
}
