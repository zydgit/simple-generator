package ${package}.service;

import ${package}.dto.${className};
import java.util.List;
import java.util.Map;

public interface ${className}Service extends IBaseService<${className}>, ProxySelf<${className}Service>{

    List<Map> queryDtl(Map param,int page, int pageSize);
}