DROP TABLE IF EXISTS le_user_extend;

CREATE TABLE le_user_extend (
  UID           VARCHAR(64) COMMENT '用户ID',
  K             INT(11) COMMENT '拓展信息标识',
  V             TEXT COMMENT '内容',
  PRIMARY KEY (UID, K)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

