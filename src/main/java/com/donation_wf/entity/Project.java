package com.donation_wf.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "项目名称不能为空")
    private String name;

    private String description;

    private String imageUrl;

    @NotNull(message = "目标金额不能为空")
    private BigDecimal targetAmount;

    private BigDecimal currentAmount;

    private Integer beneficiaryCount;

    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endTime;

    private Date createTime;

    private Date updateTime;

    public Integer getProgress() {
        if (targetAmount == null || targetAmount.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }
        return currentAmount.multiply(new BigDecimal(100))
                .divide(targetAmount, 0, BigDecimal.ROUND_HALF_UP)
                .intValue();
    }
}
