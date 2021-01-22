package com.group7.store.service.impl;


import com.group7.store.entity.book.Publish;
import com.group7.store.mapper.PublishMapper;
import com.group7.store.service.PublishService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("firstPublish")
public class PublishServiceImpl implements PublishService {

    private Logger log = Logger.getLogger(PublishServiceImpl.class);

    @Autowired
    PublishMapper publishMapper;

    @Override
    public int addPublish(Publish publish) {
        return publishMapper.addPublish(publish);
    }

    @Override
    public int deletePublish(int id) {
        return publishMapper.deletePublish(id);
    }

    @Override
    public int modifyPublish(Publish publish) {
        return publishMapper.modifyPublish(publish);
    }

    @Override
    public int modifyIsShow(int id) {
        return publishMapper.modifyIsShow(id);
    }

    @Override
    public int getPublishCount() {
        return publishMapper.getPublishCount();
    }

    @Override
    public List<Publish> getPublishByPage(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        String sLog = start + ":" + pageSize;
        log.info(sLog);
        return publishMapper.getPublishByPage(start, pageSize);
    }

    @Override
    public List<String> getPublishNames() {
        return publishMapper.getPublishNames();
    }

    @Override
    public Publish getPublishById(int id) {
        return publishMapper.getPublishById(id);
    }

    @Override
    public Publish getPublishByName(String name) {
        return publishMapper.getPublishByName(name);
    }
}
