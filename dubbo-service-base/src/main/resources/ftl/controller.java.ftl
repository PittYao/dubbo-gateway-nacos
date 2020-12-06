package ${package.Controller};


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
<#else>

import org.springframework.stereotype.Controller;
</#if>
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import com.cebon.model.po.${entity};
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.springframework.beans.BeanUtils;
import com.cebon.model.validator.Group;
import com.cebon.model.dto.PageDTO;
import com.cebon.model.dto.${entity}DTO;
import com.cebon.model.qo.${entity}PageQO;
import org.dozer.Mapper;
import java.util.List;



/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */

@Slf4j
@Validated
<#if restControllerStyle>
@RestController
<#else>
@RestController
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}s<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>

    @Autowired
    private Mapper mapper;

    @Autowired
    private ${table.serviceName} ${table.entityPath}Service;

    @GetMapping("/{id}")
    public ${entity}DTO get${entity}ById(@PathVariable String id){
        ${entity} ${table.entityPath} = ${table.entityPath}Service.getById(id);
        return ${table.entityPath} == null ? null : mapper.map(${table.entityPath}, ${entity}DTO.class);
    }

    @GetMapping("/page")
    public PageDTO<${entity}> page${entity}s(@Validated @RequestBody ${entity}PageQO ${table.entityPath}PageQO){
        // 分页写在service中
        Page<${entity}> page = new Page<>(${table.entityPath}PageQO.getCurrent(),${table.entityPath}PageQO.getSize());
        ${table.entityPath}Service.page(page, null);

        log.info("总记录数：{}",page.getTotal());
        log.info("数据：{}",page.getRecords().toArray());

        PageDTO<${entity}> pageDTO = new PageDTO<>();
        pageDTO.setParams(page);
        return pageDTO;
    }

    @DeleteMapping("")
    public Boolean remove${entity}ByIds(@NotEmpty(message = "id不能为空") @RequestParam("ids") List<String> ids){
        return ${table.entityPath}Service.removeByIds(ids);
    }

    @PutMapping("")
    public Boolean update${entity}(@Validated({Group.class, Default.class}) @RequestBody ${entity}DTO ${table.entityPath}DTO){
        ${entity} ${table.entityPath} = mapper.map(${table.entityPath}DTO, ${entity}.class);
        return ${table.entityPath}Service.updateById(${table.entityPath});
    }

    @PostMapping("")
    public Boolean save${entity}(@Validated @RequestBody ${entity}DTO ${table.entityPath}DTO){
        ${entity} ${table.entityPath} = mapper.map(${table.entityPath}DTO, ${entity}.class);
        return ${table.entityPath}Service.save(${table.entityPath});
    }
}
</#if>
