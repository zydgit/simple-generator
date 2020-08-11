package ${package}.service.impl;


import ${package}.dto.${className};
import ${package}.service.${className}Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageHelper;

@Service
@Transactional(rollbackFor = Exception.class)
public class ${className}ServiceImpl extends BaseServiceImpl<${className}> implements ${className}Service{

    @Autowired
    private ${className}Mapper mapper;

    @Override
    public List<Map> queryDtl(Map param,int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return mapper.queryDtl(param);
    }
}