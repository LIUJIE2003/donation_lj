package com.donation_wf.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class Donor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "捐赠人姓名不能为空")
    private String name;

    private String phone;

    private String email;

    private String address;

    private Integer type;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    public String getTypeName() {
        if (type == null) {
            return "未知";
        }
        return type == 1 ? "个人" : "企业";
    }
}
