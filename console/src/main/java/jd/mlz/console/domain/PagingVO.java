package jd.mlz.console.domain;

import lombok.Data;

import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-08
 */

@Data
public class PagingVO {
    List list;
    Integer total;
    Integer page;
}
