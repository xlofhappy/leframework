package com.qweather.leframework.base.rbac.permission.service;

import com.qweather.leframework.base.rbac.permission.service.po.PermissionEntity;
import com.qweather.leframework.model.IdQuery;
import com.qweather.leframework.model.Query;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public interface PermissionQuery extends IdQuery<PermissionQuery, PermissionEntity> {

    PermissionQuery pid(Long pid);

    PermissionQuery code(String code);

    PermissionQuery codeLike(String codeLike);

    PermissionQuery operationLike(String operationLike);

    PermissionQuery url(String url);

    PermissionQuery route(String route);

    PermissionQuery isMenu(Boolean isMenu);

    PermissionQuery orderByCode(Query.Direction direction);

    PermissionQuery orderByPid(Query.Direction direction);

    PermissionQuery orderByOperation(Query.Direction direction);

    PermissionQuery orderByUrl(Query.Direction direction);

    PermissionQuery orderBySort(Query.Direction direction);

}
