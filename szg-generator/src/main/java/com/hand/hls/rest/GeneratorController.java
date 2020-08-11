package com.hand.hls.rest;

import cn.hutool.core.util.PageUtil;
import com.hand.hls.domain.ColumnInfo;
import com.hand.hls.exception.BadRequestException;
import com.hand.hls.service.GenConfigService;
import com.hand.hls.service.GeneratorService;
import com.hand.hls.util.PageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api")
public class GeneratorController {

    private final GeneratorService generatorService;

    private final GenConfigService genConfigService;

    public GeneratorController(GeneratorService generatorService, GenConfigService genConfigService) {
        this.generatorService = generatorService;
        this.genConfigService = genConfigService;
    }

    @GetMapping(value = "/generator/tables/all")
    public ResponseEntity<Object> getTables(){
        return new ResponseEntity<>(generatorService.getTables(), HttpStatus.OK);
    }

    @GetMapping(value = "/generator/tables")
    public ResponseEntity<Object> getTables(@RequestParam(defaultValue = "") String name,
                                            @RequestParam(defaultValue = "") String remark,
                                    @RequestParam(defaultValue = "0")Integer page,
                                    @RequestParam(defaultValue = "10")Integer size){
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        return new ResponseEntity<>(generatorService.getTables(name,remark,startEnd), HttpStatus.OK);
    }

    @GetMapping(value = "/generator/columns")
    public ResponseEntity<Object> getTables(@RequestParam String tableName){
        List<ColumnInfo> columnInfos = generatorService.getColumns(tableName);
        return new ResponseEntity<>(PageUtils.toPage(columnInfos,columnInfos.size()), HttpStatus.OK);
    }

    @PutMapping("/generator")
    public ResponseEntity<HttpStatus> save(@RequestBody List<ColumnInfo> columnInfos){
        generatorService.save(columnInfos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/generator/sync")
    public ResponseEntity<HttpStatus> sync(@RequestBody List<String> tables){
        for (String table : tables) {
            generatorService.sync(generatorService.getColumns(table), generatorService.query(table));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/generator/{tableName}/{type}")
    public ResponseEntity<Object> generator(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response){
        switch (type){
            // 生成代码
            case 0: generatorService.generator(genConfigService.find(tableName), generatorService.getColumns(tableName));
                    break;
            // 预览
            case 1: return generatorService.preview(genConfigService.find(tableName), generatorService.getColumns(tableName));
            // 打包下载
            case 2: generatorService.download(genConfigService.find(tableName), generatorService.getColumns(tableName), request, response);
                    break;
            default: throw new BadRequestException("没有这个选项");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
