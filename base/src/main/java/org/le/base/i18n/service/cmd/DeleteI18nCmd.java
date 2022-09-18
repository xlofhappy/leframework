package org.le.base.i18n.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public class DeleteI18nCmd implements Command<Void> {

    protected String code;
    protected String lang;

    public DeleteI18nCmd(String code, String lang) {
        this.code = code;
        this.lang = lang;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( code != null && lang != null ) {
            Map<String, String> map = new HashMap<>(2);
            map.put("code", code);
            map.put("lang", lang);
            sqlSession.delete("org.le.base.i18n.delete", map);
        }
        return null;
    }

}
