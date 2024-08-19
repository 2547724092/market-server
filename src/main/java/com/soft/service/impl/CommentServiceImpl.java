package com.soft.service.impl;

import com.soft.entity.Comment;
import com.soft.mapper.CommentMapper;
import com.soft.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author group4
 * @since 2024-06-21
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
