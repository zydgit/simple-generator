package ${package}.mapper;
import ${package}.dto.${className};
import java.util.List;
import java.util.Map;

public interface ${className}Mapper extends Mapper<${className}>{\

    List<Map> queryDtl(Map param);
}