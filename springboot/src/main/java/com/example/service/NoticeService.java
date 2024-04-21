package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Business logic for Notice Information Table
 **/
@Service
public class NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    /**
     * Add
     */
    public void add(Notice notice) {
        notice.setTime(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        notice.setUser(currentUser.getUsername());
        noticeMapper.insert(notice);
    }

    /**
     * Delete by ID
     */
    public void deleteById(Integer nid) {
        System.out.println(nid);
        noticeMapper.deleteById(nid);
    }

    /**
     * Batch delete
     */
    public void deleteBatch(List<Integer> nids) {
        for (Integer nid : nids) {
            noticeMapper.deleteById(nid);
        }
    }

    /**
     * Update by ID
     */
    public void updateById(Notice notice) {
        noticeMapper.updateById(notice);
    }

    /**
     * Query by ID
     */
    public Notice selectById(Integer nid) { return noticeMapper.selectById(nid);}

    /**
     * Query all
     */
    public List<Notice> selectAll(Notice notice) {
        return noticeMapper.selectAll(notice);
    }

    /**
     * Page query
     */
    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectAll(notice);
        return PageInfo.of(list);
    }

}
