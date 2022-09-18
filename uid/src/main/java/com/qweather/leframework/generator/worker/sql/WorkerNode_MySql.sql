DROP TABLE IF EXISTS le_worker_node;

CREATE TABLE le_worker_node (
  ID          BIGINT NOT NULL AUTO_INCREMENT,

  REMARK      TEXT comment '备注',
  AUTHOR_ID   BIGINT comment '创建人ID',
  AUTHOR_TIME VARCHAR(20) comment '创建时间',
  MODIFYER_ID BIGINT comment '修改人ID',
  MODIFY_TIME VARCHAR(20) comment '修改时间',
  STATUS      TINYINT     NOT NULL DEFAULT 0 comment '记录状态',
  DISPLAY     BOOLEAN     NOT NULL DEFAULT TRUE comment '是否显示',

  HOST_NAME   VARCHAR(64) NOT NULL COMMENT 'host name',
  PORT        VARCHAR(64) NOT NULL COMMENT 'port',
  TYPE        INT         NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',
  PRIMARY KEY (ID)
)
  COMMENT ='DB WorkerID Assigner for UID Generator',
  ENGINE = InnoDB,
  DEFAULT CHARSET = utf8;

