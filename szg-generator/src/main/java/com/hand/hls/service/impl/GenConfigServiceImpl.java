package com.hand.hls.service.impl;

import com.hand.hls.domain.GenConfig;
import com.hand.hls.repository.GenConfigRepository;
import com.hand.hls.service.GenConfigService;
import com.hand.hls.util.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
@Service
public class GenConfigServiceImpl implements GenConfigService {

    private final GenConfigRepository genConfigRepository;

    public GenConfigServiceImpl(GenConfigRepository genConfigRepository) {
        this.genConfigRepository = genConfigRepository;
    }

    @Override
    public GenConfig find(String tableName) {
        GenConfig genConfig = genConfigRepository.findByTableName(tableName);
        if (genConfig == null) {
            return new GenConfig(tableName);
        }
        return genConfig;
    }

    @Override
    public GenConfig update(String tableName, GenConfig genConfig) {
        String separator = File.separator;
        String[] paths;
        String symbol = "\\";
        if (symbol.equals(separator)) {
            paths = genConfig.getPath().split("\\\\");
        } else {
            paths = genConfig.getPath().split(File.separator);
        }
        StringBuilder api = new StringBuilder();
        for (String path : paths) {
            api.append(path);
            api.append(separator);
            if ("src".equals(path)) {
                api.append("api");
                break;
            }
        }
        if (!StringUtils.isBlank(genConfig.getApiModule())){
            api.append(File.separator);
            api.append(genConfig.getApiModule());
        }
        genConfig.setApiPath(api.toString());
        return genConfigRepository.save(genConfig);
    }
}
