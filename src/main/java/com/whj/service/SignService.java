package com.whj.service;

import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2022/1/10 13:59
 * @Description:
 */
@Service
public interface SignService {
    /**
     * 上传签字图片
     *
     * @param img
     * @return
     */
    public String uploadSign(String img);

    /**
     * 盖章
     *
     * @return
     */
    public String sign();
}
