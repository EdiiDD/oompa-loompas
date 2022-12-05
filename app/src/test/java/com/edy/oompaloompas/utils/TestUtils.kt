package com.edy.oompaloompas.utils

import java.lang.reflect.Modifier

val Any.nonStaticFieldsCount: Int
    get() = this::class.java.declaredFields.count { Modifier.isStatic(it.modifiers).not() }
