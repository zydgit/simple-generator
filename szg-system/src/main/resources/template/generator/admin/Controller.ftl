package ${package}.controllers;

import ${package}.dto.${className};
import ${package}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class ${className}Controller extends BaseController{

    @Autowired
    private ${className}Service service;


    @RequestMapping(value = "${changeClassName}/query/dtl")
    @ResponseBody
    public ResponseData queryDtl(@RequestBody Map param) {
        int page = param.get("page")!=null?Integer.valueOf(param.get("page").toString()):0;
        int pageSize = param.get("pageSize")!=null?Integer.valueOf(param.get("pageSize").toString()):0;
        return new ResponseData(service.queryDtl(param,page,pageSize));
    }


    @RequestMapping(value = "${changeClassName}/query")
    @ResponseBody
    public ResponseData query(${className} dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "${changeClassName}/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<${className}> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "${changeClassName}/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<${className}> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }