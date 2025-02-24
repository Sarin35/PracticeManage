package com.practice.practicemanage.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.practice.practicemanage.utils.MetaConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
public class Menu {
    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 255)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "redirect")
    private String redirect;

    @Size(max = 255)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "component")
    private String component;

    @Size(max = 255)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "path")
    private String path;

    // 使用 @Lob 和 @Convert 注解处理 JSON 字符串
    @Lob // 使用 @Lob 注解处理大文本字段,表示一个字段应该被存储为一个大型对象
    @Convert(converter = MetaConverter.class)  // 使用自定义转换器进行序列化/反序列化,将实体的字段映射到数据库时使用自定义的转换器（AttributeConverter）
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "meta")
    private Meta meta;

    @Column(name = "parentid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer parentid;

    @Column(name = "hidden")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean hidden;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Menu> children;

}