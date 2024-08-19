package com.soft.service.impl;

import com.soft.entity.Favorite;
import com.soft.mapper.FavoriteMapper;
import com.soft.service.FavoriteService;
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
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

}
