package org.le.base.file.service.impl;

import org.le.base.file.service.cmd.DeleteFileCmd;
import org.le.base.file.service.cmd.DeleteFileForeverCmd;
import org.le.base.file.service.cmd.InsertFileCmd;
import org.le.base.file.service.FileQuery;
import org.le.base.file.service.FileService;
import org.le.base.file.service.po.FileEntity;
import org.le.model.command.CommandExecutor;
import org.le.core.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
@Service
public class FileServiceImpl implements FileService {

    private final CommandExecutor executor;

    @Autowired
    public FileServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public FileQuery createFileQuery() {
        return new FileQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileEntity createFile() {
        return new FileEntity(Config.getDaoConfig().getIdPool().getNextId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFile(FileEntity entity) {
        executor.execute(new InsertFileCmd(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(Long id) {
        executor.execute(new DeleteFileCmd(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFileForever(Long id) {
        executor.execute(new DeleteFileForeverCmd(id));
    }

}
