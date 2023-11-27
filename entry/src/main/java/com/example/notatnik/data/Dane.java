package com.example.notatnik.data;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {Data.class, ListNot.class, NormalNot.class, OpcjeData.class},version = 1)
public abstract class Dane extends  OrmDatabase{
}
