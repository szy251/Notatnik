package com.example.notatnik;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = Data.class,version = 1)
public abstract class Dane extends  OrmDatabase{
}
