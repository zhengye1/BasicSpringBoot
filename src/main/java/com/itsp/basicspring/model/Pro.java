package com.itsp.basicspring.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Vincent Zheng
 * @since 2024-10-23
 */
@Getter
@Setter
@TableName("pro")
public class Pro implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("pro_name")
    private String proName;

    @TableField("team_id")
    private Long teamId;

    @TableField("birth")
    private String birth;

    @TableField("birth_place")
    private String birthPlace;

    @TableField("org")
    private String org;

    @TableField("pro_year")
    private Integer proYear;
}
