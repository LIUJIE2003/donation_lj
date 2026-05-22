package com.donation_wf.entity;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Donation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "请选择捐赠人")
    private Long donorId;

    private String donorName;

    @NotNull(message = "请选择项目")
    private Long projectId;

    private String projectName;

    @NotNull(message = "捐赠金额不能为空")
    @DecimalMin(value = "0.01", message = "捐赠金额必须大于0")
    private BigDecimal amount;

    private Integer payType;

    private String message;

    private Date donateTime;

    private Date createTime;

    public String getPayTypeName() {
        if (payType == null) {
            return "未知";
        }
        switch (payType) {
            case 1: return "现金";
            case 2: return "银行转账";
            case 3: return "支付宝";
            case 4: return "微信";
            default: return "其他";
        }
    }
}
